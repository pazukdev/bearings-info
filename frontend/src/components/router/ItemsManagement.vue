<template>
    <div>
        <LoadingScreen v-if="this.loadingState"/>
        <div v-else>
            <CreateItemForm/>
            <Header :item="false"/>
            <NestedItemsTableTitle v-if="itemView.partsEnabled"
                                   :edit-mode="editMode" :replacers="false" :table="itemView.partsTable"/>
            <ItemList :items-management-view="true" :sorted="true"/>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import ItemList from "../list/ItemList";
    import LoadingScreen from "../special/LoadingScreen";
    import itemViewUtil from "../../util/itemViewUtil";
    import CreateItemForm from "../form/CreateItemForm";
    import NestedItemsTableTitle from "../list/section/NestedItemsTableTitle";
    import Header from "../list/section/Header";

    export default {
        name: "ItemsManagement",

        components: {
            Header,
            NestedItemsTableTitle,
            CreateItemForm,
            LoadingScreen,
            ItemList
        },

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
                        + "/" + "item"
                        + "/" + "view"
                        + "/" + "items-management"
                        + "/" + this.userName
                        + "/" + this.$i18n.locale, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(this.$store, response.data);
                        console.log("items management displayed");
                    });
            }
        }
    }
</script>

<style scoped>

</style>