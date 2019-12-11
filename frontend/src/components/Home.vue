<template>
    <div>
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

    export default {
        name: "Home",

        components: {
            Menu
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                appLanguage: state => state.dictionary.appLanguage
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
                this.$i18n.locale = this.appLanguage.toString();
            },

            setAdmin(admin) {
                return this.admin = admin;
            },

            openItemsManagement() {
                let itemsManagementId = -1;
                this.pushTo(itemsManagementId);

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