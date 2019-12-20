<template>
    <div style="margin-bottom: 20px" v-if="!isLoginPage()">
        <table class="equal-columns-table">
            <tbody>
                <tr>
                    <td>
                        <DefaultButton @on-click="goHome" :text="'Home'"/>
                    </td>
                    <td>
                        <DefaultButton v-if="!isGuest()"
                                       @on-click="openItemsManagement" :text="$t('itemsManagement')"/>
                    </td>
                    <td>
                        <DefaultButton v-if="false" @on-click="goHome" :text="'not-displayed'"/>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import itemViewUtil from "../../util/itemViewUtil";
    import {mapState} from "vuex";
    import DefaultButton from "../element/button/DefaultButton";
    import routerUtil from "../../util/routerUtil";

    export default {
        name: "NavigationBar",
        components: {DefaultButton},

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                editMode: state => state.dictionary.editMode,
                itemView: state => state.dictionary.itemView,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            goHome() {
                this.$router.push({name: "home"});
            },

            openItemsManagement() {
                this.$router.push({name: "items_management"});
            },

            isLoginPage() {
                return routerUtil.isLoginPage(this.$route);
            },

            isGuest() {
                return itemViewUtil.isGuest(this.itemView, this.userName.toString());
            }
        }
    }
</script>

<style scoped>

</style>