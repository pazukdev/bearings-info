<template>
    <div>
        <LoadingScreen v-if="isLoading()"/>
        <div v-else-if="!isEmpty(itemView)">
            <form id="item-form" @submit="submit">
                <ItemName/>
                <ItemMenu/>
                <Header :item="true" :editable="true"/>
                <WhereToBuy/>
                <details v-if="arrayIsRendered(itemView.replacersTable.replacers)">
                    <summary class="bold">{{getTextPlusCount("Replacers", itemView.replacersTable.replacers.length)}}</summary>
                    <ReplacersSection/>
                </details>
                <details v-if="arrayIsRendered(itemView.children)">
                    <summary class="bold">{{getTextPlusCount("Units / parts", itemView.children.length)}}</summary>
                    <PartsSection/>
                </details>
            </form>
            <details v-if="itemView.allChildren.length > itemView.children.length && arrayIsRendered(itemView.allChildren)">
                <summary class="bold">{{getTextPlusCount("All units / parts", itemView.allChildren.length)}}</summary>
                <ItemSummary/>
            </details>
            <details v-if="arrayIsRendered(itemView.parents.children)">
                <summary class="bold">{{getTextPlusCount(getUsageTitle(), itemView.parents.children.length)}}</summary>
                <Usage/>
            </details>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
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
    import storeUtil from "../../util/storeUtil";
    import axiosUtil from "../../util/axiosUtil";
    import ItemName from "../item/ItemName";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";
    import WhereToBuy from "../item/WhereToBuy";

    export default {

        components: {
            WhereToBuy,
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

        mixins: [basicComponent, view],

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
                        console.log("Error on getItemView(): " + error);
                        if (refreshIfError) {
                            console.log("this.getItemView(itemId, false)");
                            this.getItemView(itemId, false);
                        } else {
                            console.log("itemViewUtil.dispatchResponseError(error)");
                            itemViewUtil.dispatchResponseError(error);
                        }
                    });
            },

            getUsageTitle() {
                if (itemViewUtil.isManufacturer(this.itemView)) {
                    return "Products";
                }
                return "Usage";
            }
        }
    }
</script>

<style scoped>
    details {
        margin: 10px;
    }
</style>