<template>
    <div>
        <details class="default-margin" open>
            <summary>{{translate("App settings")}}</summary>
            <table class="default-margin equal-columns-table">
                <tbody>
                    <tr>
                        <td>
                            {{translate("Cache app pages")}}
                        </td>
                        <td style="text-align: right">
                            <SwitchElement :start-value="cache" :purpose="'enable_cache'"
                                           @toggle-switch="toggleSwitch"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            {{translate("Cashed pages")}}
                        </td>
                        <td style="text-align: right">
                            {{cachedViews.length}}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            {{translate("Pages cache size")}}
                        </td>
                        <td style="text-align: right">
                            {{roughSizeOfObject(cachedViews).toFixed(2) + " " + "MB"}}
                        </td>
                    </tr>
                    <tr v-if="isClearCacheVisible()">
                        <td>
                            {{translate("Remove all cashed pages")}}
                        </td>
                        <td>
                            <button @click="clearCache()">
                                {{translate("Clear app cache")}}
                            </button>
                        </td>
                    </tr>
                    <tr v-if="isAdmin()">
                        <td>
<!--                            {{translate("Upload all app pages")}}-->
                            <input id="number-of-pages-to-upload"
                                   placeholder="Number of pages"
                                   type="text"
                                   v-model="pages"/>
                        </td>
                        <td>
                            <button @click="uploadAllPages()">
                                {{translate("Upload all")}}
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </details>
        <br>
        <br>
        <table class="equal-columns-table">
            <tbody>
                <tr v-if="isUpdatePageVisible()">
                    <td/>
                    <td style="text-align: center">
                        <button id="update-page-button"
                                class="background-green"
                                @click="updatePage()">
                            <span style="font-size: xx-large">
                                {{"&#8635;"}}
                            </span>
                            <span>
                                {{translate("Update page")}}
                            </span>
                        </button>
                        <p style="margin-top: 10px">
                            {{translate("Get actual data from server")}}
                        </p>
                    </td>
                </tr>
                <tr v-else class="green">
                    <td/>
                    <td style="text-align: center">
                        {{translate("Page is updated")}}
                    </td>
                </tr>
                <tr v-if="seconds > 0" style="text-align: center">
                    <td colspan="2">
                        <br><br>
                        {{seconds + " seconds passed"}}
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import storeUtil from "../../util/storeUtil";
    import routerUtil from "../../util/routerUtil";
    import view from "../../mixin/view";
    import basicComponent from "../../mixin/basicComponent";
    import SwitchElement from "../element/SwitchElement";
    import shared from "../../util/shared";
    import cacheUtil from "../../util/cacheUtil";
    import axios from "axios";
    import axiosUtil from "../../util/axiosUtil";

    export default {
        name: "AppSettings",

        components: {SwitchElement},

        mixins: [basicComponent, view],

        data() {
            return {
                seconds: 0,
                pages: 0
            }
        },

        methods: {
            clearCache() {
                storeUtil.setCachedViews([]);
            },

            updatePage() {
                cacheUtil.removeCachedViews(this.itemView.itemId, this.cachedViews);
                routerUtil.refresh();
            },

            isClearCacheVisible() {
                return true;
            },

            isUpdatePageVisible() {
                if (routerUtil.isItem(this.$route)
                    || routerUtil.isItems(this.$route)
                    || routerUtil.isHome(this.$route)) {
                    return this.itemView.enableUpdate === true;
                }
                return false;
            },

            toggleSwitch(purpose) {
                if (purpose === "enable_cache") {
                    storeUtil.setCache(!this.cache);
                }
            },

            roughSizeOfObject(object) {
                return shared.roughSizeOfObject(object)
            },

            uploadAllPages() {
                storeUtil.setLoadingStateLoading();
                storeUtil.setCachedViews([]);
                this.itemView.businessLogicTime = 0;
                this.seconds = 0;
                let uploadTimer = setInterval(this.timer, 1000);
                let lang = this.getLang();
                axios
                    .get(axiosUtil.getBasicUrl()
                        + "/" + "item"
                        + "/" + "view"
                        + "/" + "all-item-views"
                        + "/" + this.pages
                        + "/" + this.getUserName()
                        + "/" + lang, {
                        headers: {
                            Authorization: axiosUtil.getAuthorization()
                        }
                    })
                    .then(response => {
                        storeUtil.setCachedViews(response.data);
                        clearTimeout(uploadTimer);
                        this.itemView.businessLogicTime = this.seconds;
                        this.itemView.responseTotalTime = this.itemView.businessLogicTime;
                        setTimeout(this.resetTimer, 5000);
                        storeUtil.setLoadingStateOff();
                    })
                    .catch(error => {
                        clearTimeout(uploadTimer);
                        this.itemView.businessLogicTime = this.seconds;
                        this.itemView.responseTotalTime = this.itemView.businessLogicTime;
                        setTimeout(this.resetTimer, 5000);
                        storeUtil.setLoadingStateOff();
                        console.log(error);
                    });

            },

            timer() {
                this.seconds++;
            },

            resetTimer() {
                this.seconds = 0;
            }
        }
    }
</script>

<style scoped>
    table {
        text-align: left;
    }

    span {
        display: inline-block;
        vertical-align: middle;
    }

    #update-page-button {
        width: initial;
        padding: 0 14px;
        border-radius: 26px;
    }
</style>