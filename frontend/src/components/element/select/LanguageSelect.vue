<template>
    <div class="bordered" style="white-space: nowrap">
<!--        {{newLang}}-->
        <!--        <flag :iso="newLanguage"/>-->
        {{translate("Language") + ": "}}
        <select v-model="newLang"
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
    import dictionaryUtil from "../../../util/dictionaryUtil";
    import axiosUtil from "../../../util/axiosUtil";
    import shared from "../../../util/shared";

    export default {
        name: "LanguageSelect",

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                lang: state => state.dictionary.lang,
                langs: state => state.dictionary.langs,
                itemView: state => state.dictionary.itemView,
                dictionary: state => state.dictionary.dictionary
            })
        },

        data() {
            return {
                newLang: ""
            }
        },

        watch: {
            '$route': 'onUrlChange'
        },

        created() {
            console.log("LanguageSelect: created()");
            this.onUrlChange();
        },

        methods: {
            onUrlChange() {
                console.log("LanguageSelect: onUrlChange()");
                let lang = this.$route.params.lang;
                if (!routerUtil.validLang(lang)) {
                    throw "Invalid lang code: " + lang;
                }

                if (this.newLang !== lang) {
                    this.newLang = lang;
                    axiosUtil.setLangsAndDictionary(lang);
                    console.log("onUrlChange(): axiosUtil.setLangsAndDictionary(lang)");
                }
            },

            selectLanguage() {
                let lang = this.newLang;
                axiosUtil.setLangsAndDictionary(lang);
                console.log("selectLanguage(): axiosUtil.setLangsAndDictionary(lang)");
                routerUtil.selectLanguage(lang, this.$route);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            isEmpty(value) {
                return shared.isEmpty(value);
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