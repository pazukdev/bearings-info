<template>
    <div>
        <table>
            <tbody>
                <tr>
                    <td style="text-align: left">
                        {{"v 2.0"}}
                    </td>
                    <td v-if="!isGuest()">
                        <router-link class="simple-link"
                                     :to="{name: 'user', params: {id: itemView.userData.id, lang: appLanguage}}">
                            {{itemView.userData.name}}
                        </router-link>
                    </td>
                    <td v-if="!isGuest()">
                        {{$t("rating") + ": " + itemView.userData.rating}}
                    </td>
                    <td v-if="isGuest()">
                        {{$t('youAreGuest')}}
                    </td>
                    <td style="text-align: right">
                        <LanguageSelect/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" style="text-align: right">
                        <div v-if="isAdmin()">{{$t("youAreAdmin")}}</div>
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
        }
    }
</script>

<style scoped>

</style>