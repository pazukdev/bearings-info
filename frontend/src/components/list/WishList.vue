<template>
    <div>
        <LoadingScreen v-if="this.loadingState"/>
        <div v-else>
            <Header :item="false"/>
            <NestedItemsTableTitle v-if="itemView.partsEnabled"
                                   :edit-mode="editMode" :replacers="false" :table="itemView.partsTable"/>
            <CountedItemList :editable-comments="true"/>
        </div>
    </div>
</template>

<script>
    import CountedItemList from "./CountedItemList";
    import Header from "./section/Header";
    import LoadingScreen from "../special/LoadingScreen";
    import NestedItemsTableTitle from "./section/NestedItemsTableTitle";
    import axios from "axios";
    import itemViewUtil from "../../util/itemViewUtil";
    import {mapState} from "vuex";

    export default {
        name: "WishList",
        components: {CountedItemList, Header, NestedItemsTableTitle, LoadingScreen},

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
                        + "/" + this.appLanguage.toString(), {
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
            }
        }
    }
</script>

<style scoped>

</style>