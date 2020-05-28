<template>
    <div>
        <LoadingScreen v-if="isLoading()"/>
        <div v-else-if="!isEmpty(itemView)">

            <ItemName/>

            <div v-if="isAllItemsReport()">
                <EditableImg :small="true"/>
                <div style="text-align: center">
                    {{getAllItemsReportText()}}
                </div>
                <details v-if="itemView.allChildren.length > 0" open>
                    <summary class="bold">
                        {{getTextPlusCount("All units / parts", itemView.allChildren.length)}}
                    </summary>
                    <ItemSummary/>
                </details>
                <div v-else style="text-align: center">
                    <br>
                    {{translate("Nothing found")}}
                </div>
            </div>

            <div v-else>
                <form id="item-form" @submit="submit">
                    <ItemMenu/>
                    <Header :item="true" :editable="true"/>
                    <WhereToBuy/>
                    <details v-if="arrayIsRendered(itemView.replacersTable.replacers)">
                        <summary class="bold">
                            {{getTextPlusCount("Replacers", itemView.replacersTable.replacers.length)}}
                        </summary>
                        <ReplacersSection/>
                    </details>
                    <details v-if="arrayIsRendered(itemView.children)" open>
                        <div v-if="!editMode"
                             class="default-margin" style="text-align: right">
                            <router-link class="simple-link"
                                         :to="{name: 'item', params: {category: itemView.category, name: itemView.name, lang: lang, report_type: getAllItemsReportUrl()}}">
                                {{getAllItemsReportText()}}
                            </router-link>
                        </div>
                        <summary class="bold">
                            {{getTextPlusCount("Units / parts", itemView.children.length)}}
                        </summary>
                        <PartsSection/>
                    </details>
                </form>
                <details v-if="arrayIsRendered(itemView.parents.children)">
                    <summary class="bold">
                        {{getTextPlusCount(getUsageTitle(), itemView.parents.children.length)}}
                    </summary>
                    <Usage/>
                </details>
            </div>
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
    import EditableImg from "../EditableImg";
    import routerUtil from "../../util/routerUtil";

    export default {

        components: {
            EditableImg,
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

        metaInfo() {
            return {
                title: this.itemView.localizedCategory + " " + this.itemView.localizedName,
                meta: [
                    {name: this.itemView.localizedCategory + " " + this.itemView.localizedName,},
                    {property: 'og:title', content: this.itemView.localizedCategory + " " + this.itemView.localizedName,},
                    {property: 'og:image', content: this.itemView.img},
                    {property: 'vk:image', content: this.itemView.img}
                ]
            }
        },

        methods: {
            getAllItemsReportText() {
                return this.translate("All units and parts list");
            },

            getAllItemsReportUrl() {
                return "all_parts_report";
            },

            getReportType() {
                return this.$route.params.report_type;
            },

            isAllItemsReport() {
                return this.getReportType() === this.getAllItemsReportUrl();
            },

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

            getUserRole() {
                return this.itemView.userData.comment;
            },

            pushToLoginForm() {
                this.$router.push({ name: `login` });
            },

            getView() {
                this.getItemView(routerUtil.getId(this.$route), true);
            },

            getItemView(itemId, refreshIfError) {
                console.log("getItemView(): " + itemId);
                let lang = this.$route.params.lang;
                axios
                    .get(axiosUtil.getBasicUrl()
                        + "/" + "item"
                        + "/" + "view"
                        + "/" + "item"
                        + "/" + itemId
                        + "/" + this.getUserName()
                        + "/" + lang
                        + "/" + this.getReportType(), {
                        headers: {
                            Authorization: axiosUtil.getAuthorization()
                        }
                    })
                    .then(response => {
                        let itemView = response.data;
                        if (itemView.nameToRedirect === "home") {
                            let message = this.translate("Item") + " "
                                + "id=" + itemId + " "
                                + this.translate("not found");
                            routerUtil.toHome(lang, message);
                        } else {
                            itemViewUtil.dispatchView(itemView, lang);
                        }
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