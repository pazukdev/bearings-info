<template>
    <div>
        <LoadingScreen v-if="this.loadingState"/>
        <div v-else>
            <HeaderMenu :user-data="itemView.userData"
                        :guest="isGuest()"
                        :admin="isAdmin()"
                        :wish-list-view="isWishListView()"
                        :items-count-in-wishlist="itemView.wishListIds.length"
                        :add-to-wishlist-button-visible="isAddToWishlistButtonVisible()"
                        :item-in-wishlist-text-visible="isItemInWishListTextVisible()"
                        :search-enabled="isSearchEnabled()"
                        :show-bottom-hr="isOrdinaryItemView()"
                        :item-name-for-search-in-google="getItemNameForSearchInGoogle()"
                        @open-wish-list="openWishList"
                        @add-item-to-wishlist="addThisItemToWishList"/>

            <ItemDescription/>
            <EditPanel/>
            <PartsSection/>
            <ReplacersSection/>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import HeaderMenu from "./HeaderMenu";
    import ItemDescription from "./ItemDescription";
    import EditPanel from "./EditPanel";
    import PartsSection from "./PartsSection";
    import ReplacersSection from "./ReplacersSection";
    import shared from "../shared";
    import itemViewUtil from "../itemViewUtil";
    import LoadingScreen from "./LoadingScreen";

    export default {

        components: {
            LoadingScreen,
            HeaderMenu,
            ItemDescription,
            EditPanel,
            PartsSection,
            ReplacersSection
        },

        data() {
            return {
                imgData: "",
                newItemCategory: "",
                newItemName: "",
                categoryMessage: "",
                fileUploadMessage: ""
            }
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                loadingState: state => state.dictionary.loadingState,
                itemView: state => state.dictionary.itemView,
                itemsManagementId: state => state.dictionary.itemsManagementId,
                motorcycleCatalogueId: state => state.dictionary.motorcycleCatalogueId,
                wishlistId: state => state.dictionary.wishlistId,
                userlistId: state => state.dictionary.userlistId,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        watch: {
            '$route': 'getItemViewByUrl'
        },

        created() {
            this.getItemViewByUrl();
        },

        methods: {
            getItemId() {
                return this.$route.params.item_id;
            },

            getUserRole() {
                return this.itemView.userData.comment;
            },

            getItemViewByUrl() {
                if (this.$route.params.lang !== this.appLanguage.toString()) {
                    this.$router.replace({
                        path: this.$router.currentRoute.path.replace(/\/[^\/]*$/, "/" + this.appLanguage)
                    });
                }
                this.$i18n.locale = this.appLanguage.toString();

                let item_id = this.processItemId(this.$route.params.item_id);

                if (item_id === "redirect to login") {
                    console.log("/" + this.$route.params.item_id
                        + " url is forbidden for user with role " + this.getUserRole());
                    console.log("redirected to login");
                    this.pushToLoginForm();
                    return;
                }

                console.log("getItemViewByUrl(): " + item_id);
                this.getItemView(item_id);
            },

            processItemId(itemId) {
                if (itemId === "items_management") {
                    return  this.itemsManagementId.toString();
                }
                if (itemId === "home") {
                    return this.motorcycleCatalogueId.toString();
                }
                if (itemId === "wishlist") {
                    if (!this.isAuthorized() || this.isGuest()) {
                        return "redirect to login";
                    }
                    return this.wishlistId.toString();
                }
                if (itemId === "users") {
                    if (!this.isAuthorized() || this.isGuest()) {
                        return "redirect to login";
                    }
                    return  this.userlistId.toString();
                }
                return itemId;
            },

            pushToLoginForm() {
                this.$router.push({ name: `login` });
            },

            loginAsGuest() {
                let username = "guest";
                let password = "user";
                let credentialsUrl ="username=" + username + "&" + "password=" + password;
                axios
                    .post(this.basicUrl + "/login", credentialsUrl)
                    .then(response => {
                        if (response.status === 200) {
                            let authorization = response.data.Authorization;
                            this.$store.dispatch("setAuthorization", authorization);
                            this.$store.dispatch("setUserName", username);
                            console.log("logged in as " + username);
                        }
                    })
                    .catch(error => {
                        console.log("login as " + username + " failed");
                    });
            },

            pushTo(itemId) {
                this.$router.push({ path: `/item/id/${itemId}/${this.appLanguage}` });
            },

            pushToHome() {
                this.pushTo(this.motorcycleCatalogueId.toString());
            },

            navigateToItem(itemId) {
                this.pushTo(itemId);
            },

            getLanguage() {
                return this.appLanguage;
            },

            getItemView(itemId) {
                this.switchEditModeOff();
                axios
                    .get(this.basicUrl
                        + "/" + "item/get-view"
                        + "/" + itemId
                        + "/" + this.userName
                        + "/" + this.getLanguage(), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let itemView = response.data;
                        this.dispatchView(itemView);
                        this.logEvent("item view displayed: item", itemView);
                        this.$emit("set-admin", this.isAdmin());
                    });
            },

            update(itemId) {
                this.switchEditModeOff();
                axios
                    .put(this.basicUrl
                        + "/" + "item/update-view"
                        + "/" + itemId
                        + "/" + this.userName
                        + "/" + this.getLanguage(),
                        this.itemView, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let updatedItemView = response.data;
                        this.dispatchView(updatedItemView);
                        this.logEvent("item updated", updatedItemView);
                    });
            },

            dispatchView(itemView) {
                itemViewUtil.dispatchView(this.$store, itemView);
            },

            logEvent(event, itemView) {
                console.log(event + ": "
                    + "id=" + itemView.itemId
                    + "; name=" + itemView.header.name);
            },

            isAuthorized() {
                return this.authorization !== "";
            },

            isAdditionalMenuDisplayed() {
                return this.isHomePage() && !this.isGuest();
            },

            isHomePage() {
                return this.isMotorcycleCatalogueView();
            },

            isAdmin() {
                return this.itemView.userData.comment === "Admin";
            },

            isGuest() {
                return itemViewUtil.isGuest(this.itemView, this.userName);
            },

            openWishList() {
                let wishListId = -3;
                this.navigateToItem(wishListId);
            },

            getItemNameForSearchInGoogle() {
                return this.itemView.header.name;
            },

            previewImage(event) {
                let input = event.target;
                let file = input.files[0];
                if (file !== null) {
                    if (file.size > 2097152) {
                        this.fileUploadMessage = "Image is too big! Size limit is 2MB";
                        return;
                    }
                    this.fileUploadMessage = "";
                    this.removeFromArray("img removed", this.itemView.messages);
                    this.itemView.messages.push("img uploaded");
                    let reader = new FileReader();
                    reader.onload = (e) => {
                        this.itemView.imgData = e.target.result;
                    };
                    reader.readAsDataURL(file);
                }
            },

            removeImg() {
                this.itemView.messages.push("img removed");
            },

            isAddToWishlistButtonVisible() {
                return !this.isInWishList(this.itemView.itemId)
                    && this.isOrdinaryItemView()
                    && !this.editMode
                    && !this.isGuest();
            },

            isItemInWishListTextVisible() {
                return this.isInWishList(this.itemView.itemId) && this.isOrdinaryItemView() && !this.isGuest();
            },

            addThisItemToWishList() {
                this.itemView.addToWishList = true;
                this.save();
            },

            rateAction(action, itemId) {
                this.itemView.rate = {
                    action: action,
                    itemId: itemId
                };
                this.save();
            },

            getItemName() {
                return this.itemView.header.rows[0].parameter;
            },

            isInWishList(itemId) {
                for (let i=0; i < this.itemView.wishListIds.length; i++) {
                    if (this.itemView.wishListIds[i] === itemId) {
                        return true;
                    }
                }
                return false;
            },

            isInArray(element, array) {
                for (let i=0; i < array.length; i++) {
                    if (array[i] === element) {
                        return true;
                    }
                }
                return false;
            },

            removePartFromList(part, array) {
                this.removeFromArray(part, array);
                if (this.isItemsManagementView() || this.isWishListView() || this.isUserListView()) {
                    this.itemView.idsToRemove.push(part.itemId);
                }
            },

            removeReplacerFromList(replacer) {
                this.removeFromArray(replacer, this.itemView.replacersTable.replacers);
            },

            removeFromArray(element, array) {
                shared.removeFromArray(element, array);
            },

            selectOnChange() {
                this.categoryMessage = "";
            },

            edit() {
                this.editMode = true;
            },

            cancel() {
                this.getItemViewByUrl();
            },

            switchEditModeOff() {
                this.editMode = false;
                this.clearAllEditData();
            },

            clearAllEditData() {
                this.clearAllMessages();
                this.clearNewItemData();
                this.imgData = "";
            },

            clearAllMessages() {
                this.fileUploadMessage = "";
                this.clearItemCreationMessages();
            },

            clearItemCreationMessages() {
                this.categoryMessage = "";
            },

            clearNewItemData() {
                this.newItemName = "";
            },

            save() {
                this.update(this.itemView.itemId);
            },

            isPartsTitleVisible() {
                return !this.isMotorcycleCatalogueView()
                    && (this.notStub(this.itemView.partsTable.name) && this.itemHaveActiveParts())
                    || (this.notStub(this.itemView.partsTable.name) && this.editMode);
            },

            isReplacersTableVisible() {
                return (this.notStub(this.itemView.replacersTable.name)
                    && this.arrayHaveActiveItems(this.itemView.replacersTable.replacers))
                || (this.notStub(this.itemView.replacersTable.name) && this.editMode);
            },

            getFirstColumnValue(item) {
                if (this.isUserListView()) {
                    return item.comment;
                } else {
                    return item.location;
                }

            },

            arrayHaveActiveItems(array) {
                for (let i=0; i < array.length; i++) {
                    if (this.statusIsActive(array[i].status)) {
                        return true;
                    }
                }
                return false;
            },

            isOrdinaryItemView() {
                return this.itemView.itemId > 0;
            },

            isItemsManagementView() {
                return this.itemView.itemId === -1;
            },

            isEditButtonVisible() {
                return !this.isMotorcycleCatalogueView() && !this.isGuest();
            },

            isMotorcycleCatalogueView() {
                return this.itemView.itemId === this.motorcycleCatalogueId;
            },

            isWishListView() {
                return this.itemView.itemId === -3;
            },

            isUserListView() {
                return this.itemView.itemId === -4;
            },

            isShowQuantityValue() {
                return (!this.editMode && (this.isOrdinaryItemView() || this.isUserListView()))
                    || (this.editMode && this.isUserListView());
            },

            isSearchEnabled() {
                return this.itemView.searchEnabled;
            },

            // selectOptionVisible(option) {
            //     return this.statusActive(option) && this.isNotThisItem(option);
            // },

            isItemDeleteButtonVisibleToCurrentUser(item) {
                return this.itemView.userData.comment === "Admin"
                    || this.currentUserIsCreator(item)
                    || this.isOrdinaryItemView()
                    || this.isWishListView();
            },

            isViewWithImage() {
                return this.itemView.imgData !== '-';
            },

            messagesContain(message) {
                return this.isInArray(message, this.itemView.messages);
            }
        }
    }
</script>

<style>
    .not-symmetrical-left {
        width: 80%;
    }

    .not-symmetrical-right {
        width: 20%;
    }

    .two-columns-table-left-column {
        width: 50%;
    }

    .two-column-table-right-column, .three-column-table-right-column, #get-all-table {
        width: 100%;
    }

    .three-column-table-left-column, .three-column-table-middle-column {
        width: 33.33%;
    }

    .three-column-table-left-column, .three-column-table-left-column-text {
        text-align: left;
    }

    .three-column-table-right-column {
        text-align: center;
    }

    .three-column-table-button-column {
    }

    .round-delete-button {
        background: red;
    }

    #item-creation-menu, #item-image {
        border-spacing: 0;
    }

    #menu-summary {
        text-align: center;
        font-weight: bold;
        font-size: large;
    }

    #place-of-creation {
        text-align: center;
        margin-top: 60px;
        margin-bottom: 20px;
    }

    #remove-img-button {
        width: initial;
        background: red
    }
</style>