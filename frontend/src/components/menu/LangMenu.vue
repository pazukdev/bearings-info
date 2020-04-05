<template>
    <div>
        <table>
            <tbody>
                <tr>
                    <td style="text-align: left; white-space: nowrap">
                        {{"v 2.0"}}
                    </td>
                    <td>
                        <router-link class="simple-link"
                                     v-if="!isEmpty(itemView) && !isEmpty(itemView.userData.id) && !isGuest()"
                                     :to="{name: 'user', params: {id: itemView.userData.id, lang: $route.params.lang}}">
                            {{itemView.userData.name}}
                        </router-link>
                        <p v-else>{{translate("You are guest")}}</p>
                    </td>
                    <td v-if="!isEmpty(itemView) && !isEmpty(itemView.userData) && !isGuest()">
                        {{translate("Rating") + ": " + itemView.userData.rating}}
                    </td>
                    <td>
                        <LanguageSelect/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" style="text-align: right">
                        <div v-if="isAdmin()">{{translate("You are admin")}}</div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import LanguageSelect from "../element/select/LanguageSelect";
    import routerUtil from "../../util/routerUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: "LangMenu",

        components: {LanguageSelect},

        mixins: [basicComponent, view],

        methods: {
            getLang() {
                return routerUtil.getLang(this.$route);
            }
        }
    }
</script>

<style scoped>

</style>