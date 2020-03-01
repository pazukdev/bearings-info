<template>
    <div class="bordered" style="white-space: nowrap">
<!--        <flag :iso="newLanguage"/>-->
        {{translate("Language") + ": "}}
        <select v-model="newLanguage"
                @change="selectLanguage()">
            <option v-for="lang in langs" :key="lang" :value="lang">
                {{lang.toUpperCase()}}
            </option>
        </select>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import routerUtil from "../../../util/routerUtil";
    import storeUtil from "../../../util/storeUtil";
    import dictionaryUtil from "../../../util/dictionaryUtil";
    import axiosUtil from "../../../util/axiosUtil";

    export default {
        name: "LanguageSelect",

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                appLanguage: state => state.dictionary.appLanguage,
                langs: state => state.dictionary.langs,
                itemView: state => state.dictionary.itemView
            })
        },

        data() {
            return {
                newLanguage: ""
            }
        },

        watch: {
            '$route': 'onUrlChange'
        },

        created() {
            this.onUrlChange();
        },

        methods: {
            onUrlChange() {
                let urlLang = this.$route.params.lang;
                if (urlLang !== this.newLanguage) {
                    this.newLanguage = urlLang;
                    storeUtil.setAppLang(this.newLanguage);
                }
            },

            selectLanguage() {
                storeUtil.setAppLang(this.newLanguage);
                routerUtil.setLang(this.newLanguage, this.$route);
                if (this.newLanguage !== "en") {
                    axiosUtil.setLangsAndDictionary();
                }
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>
    select {
        width: initial;
        height: initial;
        background: initial;
        color: grey;
    }

    option {
        background: #212121;
    }
</style>