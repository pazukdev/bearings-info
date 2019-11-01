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
            this.isAdmin();
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                loading: state => state.dictionary.loading,
                itemView: state => state.dictionary.itemView
            })
        },

        components: {
            Item
        },

        methods: {

            isLoading() {
                return this.loading === true || this.itemView === undefined;
            },

            selectItem(id) {
                this.getItemView(id, false);
            },

            getItemView(itemId, removeLastItemView) {
                axios
                    .get("/backend/item/get-view/" + itemId
                        + "/" + this.userName, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        if (removeLastItemView === true) {
                            this.$store.dispatch("removeLastItemView");
                        }
                        this.$store.dispatch("setItemView", response.data);
                    });
            },

            showMotorcycleMenu() {
                let specialMotorcycleCatalogueItemId = -2;
                this.$store.dispatch("addItemId", specialMotorcycleCatalogueItemId);
                this.getItemView(specialMotorcycleCatalogueItemId, false);
            },

            cancel(id) {
                this.getItemView(this.itemId, true);
            },

            reload() {
                window.location.reload();
            },

            refresh() {
                this.redirectToLogin();
                this.showMotorcycleMenu();
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
            },

            isAdmin() {
                let jwtData = this.authorization.split('.')[1];
                let decodedJwtJsonData = window.atob(jwtData);
                let decodedJwtData = JSON.parse(decodedJwtJsonData);
                let isAdmin = decodedJwtData.roles[0] === "ROLE_ADMIN";
                this.$store.dispatch("setAdmin", isAdmin);
            }

        }
    }

</script>

<style scoped>
</style>