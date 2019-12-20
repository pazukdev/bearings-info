<template>
    <div>
        <LoadingScreen v-if="this.loadingState"/>
        <div v-else>
            <ItemMenu @save="save"/>
            <ItemDescription/>
            <EditPanel @save="save"/>
            <PartsSection/>
            <ReplacersSection/>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import ItemMenu from "../menu/ItemMenu";
    import ItemDescription from "../item/ItemDescription";
    import EditPanel from "../menu/EditPanel";
    import PartsSection from "../item/PartsSection";
    import ReplacersSection from "../item/ReplacersSection";
    import itemViewUtil from "../../util/itemViewUtil";
    import LoadingScreen from "../special/LoadingScreen";
    import routerUtil from "../../util/routerUtil";

    export default {

        components: {
            LoadingScreen,
            ItemMenu,
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
                return routerUtil.getId(this.$route);
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

                let id = this.processItemId(this.getItemId());

                if (id === "redirect to login") {
                    console.log("/" + this.getItemId()
                        + " url is forbidden for user with role " + this.getUserRole());
                    console.log("redirected to login");
                    this.pushToLoginForm();
                    return;
                }

                console.log("getItemViewByUrl(): " + id);
                this.getItemView(id);
            },

            processItemId(itemId) {
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

            pushTo(id) {
                let lang = this.getLanguage();
                this.$router.push({ name: "item", params: {id, lang} });
            },

            pushToHome() {
                this.pushTo(this.motorcycleCatalogueId.toString());
            },

            navigateToItem(itemId) {
                this.pushTo(itemId);
            },

            getLanguage() {
                return this.appLanguage.toString();
            },

            getItemView(itemId) {
                // this.switchEditModeOff();
                axios
                    .get(this.basicUrl
                        + "/" + "item"
                        + "/" + "view"
                        + "/" + "item"
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
                // this.switchEditModeOff();
                axios
                    .put(this.basicUrl
                        + "/" + "item"
                        + "/" + "update"
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
            //
            // rateAction(action, itemId) {
            //     this.itemView.rate = {
            //         action: action,
            //         itemId: itemId
            //     };
            //     this.save();
            // },
            //
            // getItemName() {
            //     return this.itemView.header.rows[0].parameter;
            // },
            //
            // isInArray(element, array) {
            //     for (let i=0; i < array.length; i++) {
            //         if (array[i] === element) {
            //             return true;
            //         }
            //     }
            //     return false;
            // },
            // removeReplacerFromList(replacer) {
            //     this.removeFromArray(replacer, this.itemView.replacersTable.replacers);
            // },
            //
            // removeFromArray(element, array) {
            //     shared.removeFromArray(element, array);
            // },
            //
            // selectOnChange() {
            //     this.categoryMessage = "";
            // },
            //
            // edit() {
            //     this.editMode = true;
            // },

            // cancel() {
            //     this.getItemViewByUrl();
            // },

            // switchEditModeOff() {
            //     this.editMode = false;
            //     this.clearAllEditData();
            // },

            // clearAllEditData() {
            //     this.clearAllMessages();
            //     this.clearNewItemData();
            //     this.imgData = "";
            // },

            // clearAllMessages() {
            //     this.fileUploadMessage = "";
            //     this.clearItemCreationMessages();
            // },
            //
            // clearItemCreationMessages() {
            //     this.categoryMessage = "";
            // },
            //
            // clearNewItemData() {
            //     this.newItemName = "";
            // },

            save() {
                this.update(this.itemView.itemId);
            },

            // isPartsTitleVisible() {
            //     return !this.isMotorcycleCatalogueView()
            //         && (this.notStub(this.itemView.partsTable.name) && this.itemHaveActiveParts())
            //         || (this.notStub(this.itemView.partsTable.name) && this.editMode);
            // },
            //
            // isReplacersTableVisible() {
            //     return (this.notStub(this.itemView.replacersTable.name)
            //         && this.arrayHaveActiveItems(this.itemView.replacersTable.replacers))
            //     || (this.notStub(this.itemView.replacersTable.name) && this.editMode);
            // },
            //
            // getFirstColumnValue(item) {
            //     if (this.isUserListView()) {
            //         return item.comment;
            //     } else {
            //         return item.location;
            //     }
            //
            // },

            // arrayHaveActiveItems(array) {
            //     for (let i=0; i < array.length; i++) {
            //         if (this.statusIsActive(array[i].status)) {
            //             return true;
            //         }
            //     }
            //     return false;
            // },
            //
            // isOrdinaryItemView() {
            //     return this.itemView.itemId > 0;
            // },
            //
            // isItemsManagementView() {
            //     return this.itemView.itemId === -1;
            // },
            //
            // isEditButtonVisible() {
            //     return !this.isMotorcycleCatalogueView() && !this.isGuest();
            // },

            // isMotorcycleCatalogueView() {
            //     return this.itemView.itemId === this.motorcycleCatalogueId;
            // },
            //
            // isWishListView() {
            //     return this.itemView.itemId === -3;
            // },
            //
            // isUserListView() {
            //     return this.itemView.itemId === -4;
            // },
            //
            // isShowQuantityValue() {
            //     return (!this.editMode && (this.isOrdinaryItemView() || this.isUserListView()))
            //         || (this.editMode && this.isUserListView());
            // },

            // isItemDeleteButtonVisibleToCurrentUser(item) {
            //     return this.itemView.userData.comment === "Admin"
            //         || this.currentUserIsCreator(item)
            //         || this.isOrdinaryItemView()
            //         || this.isWishListView();
            // },

            isViewWithImage() {
                return this.itemView.imgData !== '-';
            },

            // messagesContain(message) {
            //     return this.isInArray(message, this.itemView.messages);
            // }
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