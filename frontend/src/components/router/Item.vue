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
                return itemViewUtil.isAuthorized(this.authorization);
            },

            isGuest() {
                return itemViewUtil.isGuest(this.itemView, this.userName);
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