<template>
    <div>
        <transition name="slide-fade">
            <div v-if="!isLoading() && !isEmpty(itemView)">
                <div v-if="isAllItemsReport()">
                    <EditableImg :small="true"/>
                    <ItemName/>
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
                        <details v-if="arrayIsRendered(itemView.parents.children)">
                            <summary class="bold">
                                {{getTextPlusCount(getUsageTitle(), itemView.parents.children.length)}}
                            </summary>
                            <Usage/>
                        </details>
                        <WhereToBuy/>
                        <details v-if="arrayIsRendered(itemView.replacersTable.replacers)">
                            <summary class="bold">
                                {{getTextPlusCount("Replacers", itemView.replacersTable.replacers.length)}}
                            </summary>
                            <ReplacersSection/>
                        </details>
                        <details v-if="arrayIsRendered(itemView.children)" open>
                            <summary class="bold">
                                {{getTextPlusCount("Units / parts", itemView.children.length)}}
                            </summary>
                            <PartsSection/>
                            <div v-if="!editMode"
                                 class="default-margin" style="text-align: right">
                                <router-link class="simple-link"
                                             :to="{name: 'item', params: {category: itemView.category, name: itemView.name, lang: lang, report_type: getAllItemsReportUrl()}}">
                                    {{getAllItemsReportText()}}
                                </router-link>
                            </div>
                        </details>
                    </form>
                </div>
                <br>
            </div>
        </transition>
        <LoadingScreen v-if="isLoading()"/>
    </div>
</template>

<script>
import axios from 'axios';
import storeUtil from "../../util/storeUtil";
import axiosUtil from "../../util/axiosUtil";
import basicComponent from "../../mixin/basicComponent";
import view from "../../mixin/view";
import itemViewUtil from "../../util/itemViewUtil";
import routerUtil from "../../util/routerUtil";
import shared from "../../util/shared";
// import ItemMenu from "../menu/ItemMenu";
    // import ItemDescription from "../list/section/ItemDescription";
    // import EditPanel from "../menu/EditPanel";
    // import PartsSection from "../item/PartsSection";
    // import ReplacersSection from "../item/ReplacersSection";
    // import LoadingScreen from "../special/LoadingScreen";
    // import Usage from "../item/Usage";
    // import CountedItemList from "../list/CountedItemList";
    // import ItemSummary from "../item/ItemSummary";
    // import Header from "../list/section/Header";
    // import ItemName from "../item/ItemName";
    // import WhereToBuy from "../item/WhereToBuy";
    // import EditableImg from "../EditableImg";

    const ItemDescription = () => import('../list/section/ItemDescription');
    const PartsSection = () => import('../item/PartsSection');
    const ReplacersSection = () => import('../item/ReplacersSection');
    const CountedItemList = () => import('../list/CountedItemList');
    const ItemSummary = () => import('../item/ItemSummary');
    const Header = () => import('../list/section/Header');
    const WhereToBuy = () => import('../item/WhereToBuy');
    const EditableImg = () => import('../EditableImg');
    const Usage = () => import('../item/Usage');
    const LoadingScreen = () => import('../special/LoadingScreen');
    const EditPanel = () => import('../menu/EditPanel');
    const ItemMenu = () => import("../menu/ItemMenu");
    const ItemName = () => import('../item/ItemName');

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
                title: this.getTitle(this.itemView),
                meta: [
                    {name: this.itemView.localizedCategory + " " + this.itemView.localizedName,},
                    {property: 'og:title', content: this.itemView.localizedCategory + " " + this.itemView.localizedName,},
                    {property: 'og:image', content: this.itemView.img},
                    {property: 'vk:image', content: this.itemView.img}
                ]
            }
        },

        methods: {
            getTitle(itemView) {
                let firstPart = itemView.localizedCategory;
                if (itemView.category === "Vehicle" && !this.isEmpty(itemView.header)) {
                    for (let i = 0; i <= itemView.header.rows.length; i++) {
                        let row = itemView.header.rows[i];
                        if (row.name === "Class") {
                            if (!this.isEmpty(row.value)) {
                                firstPart = row.value;
                            }
                            break;
                        }
                    }
                }
                return firstPart + " " + itemView.localizedName
            },

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
                let itemId = this.itemView.itemId;
                let lang = this.getLang();
                axiosUtil.updateItem(itemId, this.itemView, lang, this.cache, this.cachedViews);
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
                let reportType = this.getReportType();
                let lang = this.getLang();
                if (this.cache) {
                    let cachedView = this.findViewInCache(itemId, lang, reportType);
                    if (!shared.isEmpty(cachedView)) {
                        this.dispatchCachedView(cachedView, lang);
                        return cachedView;
                    }
                }

                axios
                    .get(axiosUtil.getBasicUrl()
                        + "/" + "item"
                        + "/" + "view"
                        + "/" + "item"
                        + "/" + itemId
                        + "/" + this.getUserName()
                        + "/" + lang
                        + "/" + reportType, {
                        headers: {
                            Authorization: axiosUtil.getAuthorization()
                        }
                    })
                    .then(response => {
                        let itemView = response.data;
                        if (itemView.nameToRedirect === "home") {
                            let idData;
                            if (itemId.includes("&")) {
                                idData = this.translate(itemId.split("&")[0]) + " "
                                    + this.translate(itemId.split("&")[1]);
                            } else {
                                idData = this.translate("Item") + " id=" + itemId
                            }
                            let message = idData + " " + this.translate("not found");
                            routerUtil.toHome(lang, message);
                        } else {
                            itemViewUtil.dispatchView(itemView, lang);
                            if (this.cache) {
                                itemView.reportType = reportType;
                                this.cachedViews.push(itemView);
                            }
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

    /*.slide-fade-enter-active {*/
    /*    transition: all .3s ease;*/
    /*}*/
    /*.slide-fade-leave-active {*/
    /*    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);*/
    /*}*/
    /*.slide-fade-enter, .slide-fade-leave-to {*/
    /*    transform: translateX(10px);*/
    /*    opacity: 0;*/
    /*}*/
</style>