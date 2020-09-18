<template>
    <div>
<!--        {{userData.name}}-->
        <table>
            <tbody>
                <tr>
                    <td style="text-align: left; white-space: nowrap">
                        {{"v 2.03"}}
                    </td>
                    <td>
                        <router-link class="simple-link"
                                     v-if="!isEmpty(itemView) && !isEmpty(userData) && !isEmpty(userData.id) && !isGuest()"
                                     :to="{name: 'user', params: {id: userData.id, lang: lang}}">
                            {{replaceEmpty(userData.name)}}
                        </router-link>
                        <p v-else>{{translate("You are guest")}}</p>
                    </td>
                    <td v-if="!isEmpty(itemView) && !isEmpty(userData) && !isGuest()">
                        {{translate("Rating") + ": " + replaceEmpty(userData.rating)}}
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
import basicComponent from "../../mixin/basicComponent";
import view from "../../mixin/view";

export default {
        name: "LangMenu",
        components: {LanguageSelect},
        mixins: [basicComponent, view]
    }
</script>

<style scoped>

</style>