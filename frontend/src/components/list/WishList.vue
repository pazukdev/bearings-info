<template>
    <div>
        <LoadingScreen v-if="isLoading()"/>
        <div v-else style="text-align: center">
            <div class="default-margin" style="text-align: center">
                <b>{{translate("Wishlist")}}</b>
                <br>
                <br>
                <ul>
                    <li>{{translate("Add comment in the left column")}}</li>
                    <li>{{translate("Edit quantity in the right column")}}</li>
                </ul>
                <br>
            </div>
            <Header :editable="true"/>
            <CountedItemList :editable-comments="true" :wish-list-view="true"/>
        </div>
    </div>
</template>

<script>
    import CountedItemList from "./CountedItemList";
    import Header from "./section/Header";
    import LoadingScreen from "../special/LoadingScreen";
    import axios from "axios";
    import itemViewUtil from "../../util/itemViewUtil";
    import userUtil from "../../util/userUtil";
    import routerUtil from "../../util/routerUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: "WishList",

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
                        + "/" + "wishlist"
                        + "/" + userUtil.getUserName()
                        + "/" + routerUtil.getLang(this.$route), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(response.data, this.$route.params.lang);
                        console.log("user wishlist rendered");
                    })
                    .catch(error => {
                        itemViewUtil.dispatchResponseError(error);
                    });
            },

            save() {
                itemViewUtil.update(this.itemView);
            }

        }
    }
</script>

<style scoped>
    ul {
        list-style-position: inside;
        text-align: left;
    }
</style>