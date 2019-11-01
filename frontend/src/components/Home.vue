<template>
    <div>
        <div v-if="isLoading()" style="text-align: center; padding-top: 50%">
            {{"Loading..."}}
        </div>
        <Item v-if="!isLoading()" @cancel="cancel" @select-item="selectItem"/>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import Item from "./Item";

    export default {

        data() {
            return {
                motorcycle: "",
                engine: "",
                manufacturers: [],
                engines: [],
                decodedJwtData: ""
            }
        },

        created() {
            this.refresh();
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                loadingState: state => state.dictionary.loadingState,
                itemView: state => state.dictionary.itemView,
                lastItemId: state => state.dictionary.itemIds[state.dictionary.itemIds.length - 1]
            })
        },

        components: {
            Item
        },

        methods: {
            isLoading() {
                return this.loadingState === true || this.itemView === undefined;
            },

            selectItem(id) {
                this.getItemView(id, false);
            },

            setView(itemView) {

            },

            getItemView(itemId, removeLastItemView) {
                console.log("22222");
                axios
                    .get("/backend/item/get-view/" + itemId
                        + "/" + this.userName, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        console.log("33333333333");
                        this.$store.dispatch("setItemView", response.data);
                        this.$store.dispatch("setLoadingState", false);

                    });
            },

            cancel(id) {
                this.getItemView(this.lastItemId, true);
            },

            refresh() {
                this.redirectToLogin();
                this.selectItem(this.lastItemId);
            },

            redirectToLogin() {
                if (!this.isAuthorized()) {
                    this.goToLogin();
                }
            },

            goToLogin() {
                this.$router.push({ path: '/login'});
            },

            isAuthorized() {
                return this.authorization !== "";
            }
        }
    }

</script>

<style scoped>
</style>