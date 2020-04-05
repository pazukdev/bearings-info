<template>
    <div v-if="!isLoginPage()">
        <table v-if="!buttonStyle"
               style="margin-bottom: 20px" class="equal-columns-table">
            <tbody>
                <tr>
                    <td>
                        <router-link :to="{name: 'home', params: {lang: lang}}"
                                     active-class="active">
                            {{translate("Vehicles")}}
                        </router-link>
                    </td>
                    <td>
                        <router-link :to="{name: 'items_management', params: {lang: lang}}"
                                     active-class="active">
                            {{translate("All")}}
                        </router-link>
                    </td>
                    <td>
                        <router-link :to="{name: 'menu', params: {lang: lang}}" active-class="active">
                            {{translate("Menu")}}
                        </router-link>
                    </td>
                    <td>
                        <router-link v-if="!isGuest()"
                                     :style="getWishlistStyle()"
                                     :to="{name: 'wish_list', params: {lang: lang}}"
                                     active-class="active">
                            {{translate("Wishlist") + ": " + itemView.wishListIds.length}}
                        </router-link>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import DefaultButton from "../element/button/DefaultButton";
    import routerUtil from "../../util/routerUtil";
    import colorUtil from "../../util/colorUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: "NavigationBar",

        components: {DefaultButton},

        mixins: [basicComponent, view],

        data() {
            return  {
                buttonStyle: false
            }
        },

        methods: {
            getWishlistStyle() {
                if (this.itemView.wishListIds.length > 0) {
                    return {
                        color: colorUtil.getGreenCode()
                    }
                }
            },

            goHome() {
                routerUtil.toHome(this.lang);
            },

            showCurrentUserProfile() {
                routerUtil.toUser(this.itemView.userData.id)
            },

            openItemsManagement() {
                routerUtil.toItemsManagement(null, this.$route);
            },

            isLoginPage() {
                return routerUtil.isLogin(this.$route);
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