<template>
    <div>
        <LoadingScreen v-if="isLoading()"/>
        <transition name="slide-fade">
            <div v-if="!isLoading()">
                <Header :editable="isAdmin()"/>
                <CountedItemList :user-list-view="true" :sorted="true" :translate-comments="true"/>
            </div>
        </transition>
    </div>
</template>

<script>
import CountedItemList from "./CountedItemList";
import Header from "./section/Header";
import LoadingScreen from "../special/LoadingScreen";
import axios from "axios";
import itemViewUtil from "../../util/itemViewUtil";
import routerUtil from "../../util/routerUtil";
import userUtil from "../../util/userUtil";
import basicComponent from "../../mixin/basicComponent";
import view from "../../mixin/view";

export default {
        name: "UserList",

        components: {CountedItemList, Header, LoadingScreen},

        mixins: [basicComponent, view],

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
                axios
                    .get(this.basicUrl
                        + "/" + "view"
                        + "/" + "user/list"
                        + "/" + userUtil.getUserName()
                        + "/" + routerUtil.getLang(this.$route), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(response.data, this.$route.params.lang);
                        console.log("user list rendered");
                    })
                    .catch(error => {
                        itemViewUtil.dispatchResponseError(error);
                    });
            }

        }

    }
</script>

<style scoped>

</style>