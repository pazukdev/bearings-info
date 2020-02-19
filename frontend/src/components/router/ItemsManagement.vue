<template>
    <div>
        <LoadingScreen v-if="isLoading()"/>
        <div v-else>
            <CreateItemForm/>
            <Header/>
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
            <ItemList :items-management-view="true" :sorted="true"/>
            <div style="height: 100px"/>
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
    import shared from "../../util/shared";

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
                axios
                    .get(this.basicUrl
                        + "/" + "item"
                        + "/" + "view"
                        + "/" + "items"
                        + "/" + this.status
                        + "/" + this.userName
                        + "/" + this.appLanguage, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(response.data);
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
                return itemViewUtil.isAdmin(this.itemView);
            },

            changeStatus() {
                this.onUrlChange();
            }
        }
    }
</script>

<style scoped>

</style>