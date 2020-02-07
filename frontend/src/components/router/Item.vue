<template>
    <div>
<!--        {{itemView.children.length}}-->
        <LoadingScreen v-if="isLoading()"/>
        <div v-else>
            <ItemMenu/>

            <form id="item-form" @submit="submit">
                <Header :item="true"/>

                <details v-if="arrayIsRendered(itemView.children)" open>
                    <summary>{{"Parts"}}</summary>
                    <PartsSection/>
                </details>

                <details v-if="arrayIsRendered(itemView.replacersTable.replacers)" open>
                    <summary>{{"Replacers"}}</summary>
                    <ReplacersSection/>
                </details>
            </form>

            <details v-if="arrayIsRendered(itemView.allChildren)">
                <summary>{{"All parts"}}</summary>
                <ItemSummary/>
            </details>

            <details v-if="arrayIsRendered(itemView.parents.children)">
                <summary>{{"Usage"}}</summary>
                <Usage/>
            </details>
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
    import Usage from "../item/Usage";
    import CountedItemList from "../list/CountedItemList";
    import ItemSummary from "../item/ItemSummary";
    import Header from "../list/section/Header";
    import shared from "../../util/shared";
    import storeUtil from "../../util/storeUtil";
    import axiosUtil from "../../util/axiosUtil";

    export default {

        components: {
            Header,
            ItemSummary,
            CountedItemList,
            Usage,
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
                appLanguage: state => state.dictionary.appLanguage,
                editMode: state => state.dictionary.editMode
            })
        },

        created() {
            this.onUrlChange();
        },

        watch: {
            '$route': 'onUrlChange'
        },

        methods: {
            submit: function (e) {
                e.preventDefault();
                storeUtil.setLoadingStateDefault();
                storeUtil.setEditMode(false);
                this.update(this.itemView.itemId);
            },

            update(itemId) {
                let itemView = this.itemView;
                let basicUrl = this.basicUrl.toString();
                let userName = this.userName.toString();
                let appLanguage = this.appLanguage.toString();
                let authorization = this.authorization;
                axiosUtil.updateItem(itemId, itemView, basicUrl, userName, appLanguage, authorization);
            },

            arrayIsRendered(array) {
                return this.editMode || array.length > 0;
            },

            onUrlChange() {
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
                        itemViewUtil.dispatchView(itemView);
                    })
                    .catch(error => {
                        itemViewUtil.dispatchResponseError(error);
                    });
            },

            isAuthorized() {
                return itemViewUtil.isAuthorized(this.authorization);
            },

            isGuest() {
                return itemViewUtil.isGuest(this.userName);
            },

            isLoading() {
                return shared.isLoading(this.loadingState);
            }
        }
    }
</script>

<style scoped>
    details {
        margin: 10px;
    }
</style>