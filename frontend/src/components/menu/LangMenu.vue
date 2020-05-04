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
                                     v-if="!isEmpty(userData) &&!isEmpty(userData.id) && !isGuest()"
                                     :to="{name: 'user', params: {id: userData.id, lang: lang}}">
                            {{userData.name}}
                        </router-link>
                        <p v-else>{{translate("You are guest")}}</p>
                    </td>
                    <td v-if="!isEmpty(userData) && !isGuest()">
                        {{translate("Rating") + ": " + userData.rating}}
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