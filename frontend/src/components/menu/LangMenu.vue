<template>
    <div>
        <table>
            <tbody>
                <tr>
                    <td style="text-align: left; white-space: nowrap">
                        {{"v 2.0"}}
                    </td>
                    <td v-if="!isGuest()">
                        <router-link class="simple-link"
                                     :to="{name: 'user', params: {id: itemView.userData.id, lang: appLanguage}}">
                            {{itemView.userData.name}}
                        </router-link>
                    </td>
                    <td v-if="!isGuest()">
                        {{translate("Rating") + ": " + itemView.userData.rating}}
                    </td>
                    <td v-if="isGuest()">
                        {{translate("You are guest")}}
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
    import itemViewUtil from "../../util/itemViewUtil";
    import {mapState} from "vuex";
    import dictionaryUtil from "../../util/dictionaryUtil";

    export default {
        name: "LangMenu",

        components: {LanguageSelect},

        computed: {
            ...mapState({
                userName: state => state.dictionary.userName,
                itemView: state => state.dictionary.itemView,
                loadingState: state => state.dictionary.loadingState,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            isGuest() {
                return itemViewUtil.isGuest(this.userName);
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>

</style>