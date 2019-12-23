<template>
    <div>
        <LoadingScreen v-if="this.loadingState"/>
        <div v-else>
            <ItemMenu/>
            <PartsSection/>
            <ReplacersSection/>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import ItemMenu from "../menu/ItemMenu";
    import ItemDescription from "../list/section/ItemDescription";
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

        created() {
            this.onUrlChange();
        },

        watch: {
            '$route': 'onUrlChange'
        },

        methods: {
            onUrlChange() {
                itemViewUtil.setLocale(this.$router, this.$route, this.$i18n, this.appLanguage.toString());
                this.getView();
            },

            getView() {
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

            getItemId() {
                return routerUtil.getId(this.$route);
            },

            getUserRole() {
                return this.itemView.userData.comment;
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
                routerUtil.toItem(this.$router, id, this.appLanguage.toString());
            },

            pushToHome() {
                this.pushTo(this.motorcycleCatalogueId.toString());
            },

            navigateToItem(itemId) {
                this.pushTo(itemId);
            },

            getItemView(itemId) {
                axios
                    .get(this.basicUrl
                        + "/" + "item"
                        + "/" + "view"
                        + "/" + "item"
                        + "/" + itemId
                        + "/" + this.userName
                        + "/" + this.appLanguage, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let itemView = response.data;
                        itemViewUtil.dispatchView(this.$store, itemView);
                    });
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
            // getItemName() {
            //     return this.itemView.header.rows[0].parameter;
            // },
            //
            // selectOnChange() {
            //     this.categoryMessage = "";
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