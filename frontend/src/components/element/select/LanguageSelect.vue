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
    import dictionaryUtil from "../../../util/dictionaryUtil";
    import axiosUtil from "../../../util/axiosUtil";
    import shared from "../../../util/shared";

    export default {
        name: "LanguageSelect",

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                langs: state => state.dictionary.langs,
                itemView: state => state.dictionary.itemView,
                dictionary: state => state.dictionary.dictionary
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
            console.log("LanguageSelect: created()");
            this.onUrlChange();
        },

        methods: {
            onUrlChange() {
                console.log("LanguageSelect: onUrlChange()");
                let urlLang = this.$route.params.lang;
                if (!routerUtil.validLang(urlLang)) {
                    throw "Invalid lang code: " + urlLang;
                } else if (urlLang !== this.newLanguage) {
                    this.newLanguage = urlLang;
                    console.log("onUrlChange(): axiosUtil.setLangsAndDictionary(urlLang)");
                    axiosUtil.setLangsAndDictionary(urlLang);
                }
            },

            selectLanguage() {
                routerUtil.selectLanguage(this.newLanguage, this.$route);
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