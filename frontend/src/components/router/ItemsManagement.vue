<template>
    <div>
        <LoadingScreen v-if="isLoading()"/>
        <transition name="slide-fade">
            <div v-if="!isLoading()">
                <CreateItemForm/>
                <Header :editable="true" :items-management="true"/>
                <div v-if="isAdmin()" class="default-margin">
                    <label>
                        {{"Items status"}}
                        <select v-model="status" @change="changeStatus()">
                            <option v-for="status in ['active', 'deleted']" :value="status">
                                {{status}}
                            </option>
                        </select>
                    </label>
                </div>
                <ItemList :items-management-view="true"
                          :sorted="true"
                          :url-filter="$route.params.filter"/>
                <div style="height: 100px"/>
            </div>
        </transition>
    </div>
</template>

<script>
import axios from "axios";
import ItemList from "../list/ItemList";
import LoadingScreen from "../special/LoadingScreen";
import itemViewUtil from "../../util/itemViewUtil";
import CreateItemForm from "../form/CreateItemForm";
import NestedItemsTableTitle from "../list/section/NestedItemsTableTitle";
import Header from "../list/section/Header";
import shared from "../../util/shared";
import userUtil from "../../util/userUtil";
import routerUtil from "../../util/routerUtil";
import basicComponent from "../../mixin/basicComponent";
import view from "../../mixin/view";

export default {
        name: "ItemsManagement",

        components: {
            Header,
            NestedItemsTableTitle,
            CreateItemForm,
            LoadingScreen,
            ItemList
        },

        mixins: [basicComponent, view],

        data() {
            return {
                status: "active"
            }
        },

        created() {
            this.onUrlChange();
        },

        watch: {
            '$route': 'onUrlChange'
        },

        methods: {
            onUrlChange() {
                this.getView();
            },

            getView() {
                let itemId = "ITEMS_MANAGEMENT_VIEW";
                console.log("get " + itemId);
                let lang = this.getLang();
                if (this.cache) {
                    let cachedView = this.findViewInCache(itemId, lang, "-");
                    if (!shared.isEmpty(cachedView)) {
                        this.dispatchCachedView(cachedView, lang);
                        return cachedView;
                    }
                }

                axios
                    .get(this.basicUrl
                        + "/" + "item"
                        + "/" + "view"
                        + "/" + "items"
                        + "/" + this.status
                        + "/" + userUtil.getUserName()
                        + "/" + routerUtil.getLang(this.$route), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(response.data, this.$route.params.lang);
                        if (this.cache) {
                            response.data.reportType = "-";
                            this.cachedViews.push(response.data);
                        }
                        console.log("items management displayed");
                    })
                    .catch(error => {
                        itemViewUtil.dispatchResponseError(error);
                    });
            },

            isLoading() {
                return shared.isLoading(this.loadingState);
            },

            isAdmin() {
                return userUtil.isAdmin(this.itemView);
            },

            changeStatus() {
                this.onUrlChange();
            }
        }
    }
</script>

<style scoped>

</style>