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
    import {mapState} from "vuex";
    import shared from "../../util/shared";
    import dictionaryUtil from "../../util/dictionaryUtil";

    export default {
        name: "WishList",
        components: {CountedItemList, Header, LoadingScreen},

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                appLanguage: state => state.dictionary.appLanguage,
                itemView: state => state.dictionary.itemView,
                loadingState: state => state.dictionary.loadingState,
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
            onUrlChange() {
                this.getView();
            },

            getView() {
                axios
                    .get(this.basicUrl
                        + "/" + "view"
                        + "/" + "wishlist"
                        + "/" + this.userName
                        + "/" + this.appLanguage, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(response.data);
                        console.log("user wishlist rendered");
                    })
                    .catch(error => {
                        itemViewUtil.dispatchResponseError(error);
                    });
            },

            save() {
                itemViewUtil.update(this.itemView);
            },

            isLoading() {
                return shared.isLoading(this.loadingState);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
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