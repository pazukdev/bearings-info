<template>
    <div v-if="!isLoginPage()">
        <table v-if="buttonStyle" style="margin-bottom: 20px" class="equal-columns-table">
            <tbody>
                <tr>
                    <td>
                        <DefaultButton @on-click="goHome" :text="$t('home')"/>
                    </td>
                    <td>
                        <DefaultButton @on-click="openItemsManagement" :text="$t('appData')"/>
                    </td>
                    <td>
                        <DefaultButton v-if="!isGuest()" @on-click="showCurrentUserProfile()" :text="$t('myProfile')"/>
                    </td>
                </tr>
            </tbody>
        </table>

        <table v-if="!buttonStyle" style="margin-bottom: 20px" class="equal-columns-table">
            <tbody>
                <tr>
                    <td>
                        <router-link :to="{name: 'home'}" active-class="active">
                            {{$t('home')}}
                        </router-link>
                    </td>
                    <td>
                        <router-link :to="{name: 'items_management'}" active-class="active">
                            {{$t('appData')}}
                        </router-link>
                    </td>
                    <td>
                        <router-link :to="{name: 'user', params: {id: itemView.userData.id, lang: appLanguage}}"
                                     active-class="active">
                            {{$t('myProfile')}}
                        </router-link>
                    </td>
                    <td>
                        <router-link :to="{name: 'menu'}" active-class="active">
                            {{$t("menu")}}
                        </router-link>
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

        data() {
            return  {
                buttonStyle: false
            }
        },

        methods: {
            goHome() {
                routerUtil.toHome();
            },

            showCurrentUserProfile() {
                routerUtil.toUser(this.itemView.userData.id, this.appLanguage)
            },

            openItemsManagement() {
                routerUtil.toItemsManagement(this.$router);
            },

            isLoginPage() {
                return routerUtil.isLogin(this.$route);
            },

            isGuest() {
                return itemViewUtil.isGuest(this.userName);
            }
        }
    }
</script>

<style scoped>
    .active {
        border-bottom: grey solid 2px;
    }

    a {
        display: inline-block;
        width: 100%;
        color: grey;
        text-decoration: none;
        padding-bottom: 14px;
        border-bottom: transparent solid 2px;
    }

    a:hover {
        border-bottom: grey solid 2px;
    }
</style>