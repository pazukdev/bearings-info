<template>
    <div>
        <MotorcycleCatalogue/>

        <details open>
            <summary id="menu-summary">{{$t("menu")}}</summary>
            <Menu :admin="true"
                  :basic-url="basicUrl"
                  @open-items-management="openItemsManagement"
                  @open-users-list="openUsersList"/>
        </details>

        <div id="place-of-creation">
            {{"Minsk 2019"}}
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import Menu from "./Menu";
    import MotorcycleCatalogue from "./MotorcycleCatalogue";
    import axios from "axios";

    export default {
        name: "Home",

        components: {
            MotorcycleCatalogue,
            Menu
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                appLanguage: state => state.dictionary.appLanguage,
                itemView: state => state.dictionary.itemView
            })
        },

        data() {
            return {
                admin: false
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
                this.getMotorcycleCatalogueItemView();
            },

            setLocale() {
                let language = this.appLanguage.toString();
                if (this.$route.params.lang !== language) {
                    this.changeLanguageInUrl(language);
                }
                this.$i18n.locale = language;
            },

            changeLanguageInUrl(language) {
                this.$router.replace({
                    path: this.$router.currentRoute.path.replace(/\/[^/]*$/, "/" + language)
                });
            },

            getMotorcycleCatalogueItemView() {
                this.setLocale();

                // this.$store.dispatch("setLoadingState", true);
                axios
                    .get(this.basicUrl
                        + "/" + "item"
                        + "/" + "get-home-view"
                        + "/" + this.userName
                        + "/" + this.$i18n.locale, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let itemView = response.data;
                        this.dispatchView(itemView);
                        console.log("home displayed");
                    });
            },

            dispatchView(itemView) {
                this.$store.dispatch("setItemView", itemView);
                this.$store.dispatch("setLoadingState", false);
            },

            logEvent(event, itemId, name) {
                console.log(event + ": " + "id=" + itemId + "; name=" + name);
            },

            isAdmin() {
                return this.itemView.userData.comment === "Admin";
            },

            openItemsManagement() {
                this.$router.push({ name: "items_management" });

            },

            openUsersList() {
                let usersListId = -4;
                this.pushTo(usersListId);
            },

            pushTo(itemId) {
                this.$router.push({ path: `/item/id/${itemId}/:lang` });
            }
        }
    }
</script>

<style scoped>

</style>