<template>
    <div>
        <details class="default-margin">
            <summary>{{translate("App settings")}}</summary>
            <table class="default-margin equal-columns-table">
                <tbody>
                    <tr>
                        <td>
                            {{"Cached pages: " + itemView.cachedViews}}
                        </td>
                        <td>
                            <button @click="clearCache()">
                                {{translate("Clear app cache")}}
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p v-if="seconds > 0">
                                {{seconds + " seconds passed"}}
                            </p>
                        </td>
                        <td>
                            <button @click="createDefaultCache()">
                                {{"Create default cache"}}
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </details>
    </div>
</template>

<script>
import storeUtil from "../../util/storeUtil";
import view from "../../mixin/view";
import basicComponent from "../../mixin/basicComponent";
import SwitchElement from "../element/SwitchElement";
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
                axios
                    .put(axiosUtil.getBasicUrl()
                        + "/" + "item"
                        + "/" + "reset-cache", {
                            headers: {
                                Authorization: axiosUtil.getAuthorization()
                            }
                        })
                    .then(response => {
                        this.itemView.cachedViews = response.data;
                        console.log("server cache cleared");
                    });
            },

            createDefaultCache() {
                storeUtil.setLoadingStateLoading();
                this.itemView.businessLogicTime = 0;
                this.seconds = 0;
                let uploadTimer = setInterval(this.timer, 1000);
                axios
                    .put(axiosUtil.getBasicUrl()
                        + "/" + "item"
                        + "/" + "create-default-cache", {
                        headers: {
                            Authorization: axiosUtil.getAuthorization()
                        }
                    })
                    .then(response => {
                        this.itemView.cachedViews = response.data;
                        clearTimeout(uploadTimer);
                        this.itemView.businessLogicTime = this.seconds;
                        this.itemView.responseTotalTime = this.itemView.businessLogicTime;
                        setTimeout(this.resetTimer, 5000);
                        storeUtil.setLoadingStateOff();
                        console.log("server cache cleared");
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