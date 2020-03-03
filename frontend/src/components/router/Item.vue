<template>
    <div>
        <LoadingScreen v-if="isLoading()"/>
        <div v-else>
            <form id="item-form" @submit="submit">
                <ItemName/>
                <ItemMenu/>
                <Header :item="true"/>

                <details v-if="arrayIsRendered(itemView.children)" open>
                    <summary>{{translate("Units / parts")}}</summary>
                    <PartsSection/>
                </details>

                <details v-if="arrayIsRendered(itemView.replacersTable.replacers)" open>
                    <summary>{{translate("Replacers")}}</summary>
                    <ReplacersSection/>
                </details>
            </form>

            <details v-if="arrayIsRendered(itemView.allChildren)">
                <summary>{{translate("All units / parts")}}</summary>
                <ItemSummary/>
            </details>

            <details v-if="arrayIsRendered(itemView.parents.children)">
                <summary>{{translate("Usage")}}</summary>
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
    import dictionaryUtil from "../../util/dictionaryUtil";
    import ItemName from "../item/ItemName";
    import userUtil from "../../util/userUtil";

    export default {

        components: {
            ItemName,
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
                loadingState: state => state.dictionary.loadingState,
                itemView: state => state.dictionary.itemView,
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
                axiosUtil.updateItem(itemId, this.itemView, this.$route.params.lang);
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
                this.getItemView(id, true);
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

            getItemView(itemId, refreshIfError) {
                let lang = this.$route.params.lang;
                axios
                    .get(axiosUtil.getBasicUrl()
                        + "/" + "item"
                        + "/" + "view"
                        + "/" + "item"
                        + "/" + itemId
                        + "/" + this.getUserName()
                        + "/" + lang, {
                        headers: {
                            Authorization: axiosUtil.getAuthorization()
                        }
                    })
                    .then(response => {
                        let itemView = response.data;
                        itemViewUtil.dispatchView(itemView, lang);
                    })
                    .catch(error => {
                        this.getItemView(itemId, false);
                        itemViewUtil.dispatchResponseError(error);
                    });
            },

            isAuthorized() {
                return itemViewUtil.isAuthorized(axiosUtil.getAuthorization());
            },

            isGuest() {
                return userUtil.isGuest();
            },

            isLoading() {
                return shared.isLoading(this.loadingState);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            getUserName() {
                return userUtil.getUserName();
            }
        }
    }
</script>

<style scoped>
    details {
        margin: 10px;
    }
</style>