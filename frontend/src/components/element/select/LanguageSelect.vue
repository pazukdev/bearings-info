<template>
    <div style="white-space: nowrap">
        <span v-if="newLang === 'en'">
            <flag :iso="'GB'"/>
        {{" "}}
        </span>
        <flag :iso="getCountryCode(newLang)"/>
        {{" "}}
        <select v-model="newLang"
                @change="selectLanguage()">
            <option v-for="lang in getSortedLangs(langs)" :key="lang" :value="lang">
                {{getLanguageISONativeName(getCountryCode(lang))}}
            </option>
        </select>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import routerUtil from "../../../util/routerUtil";
    import axiosUtil from "../../../util/axiosUtil";
    import basicComponent from "../../../mixin/basicComponent";
    import view from "../../../mixin/view";

    export default {
        name: "LanguageSelect",

        mixins: [basicComponent, view],

        computed: {
            ...mapState({
                dictionary: state => state.dictionary.dictionary,
                countries: state => state.dictionary.countries
            })
        },

        data() {
            return {
                newLang: "",
                newCountryCode: ""
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

            getCountryCode(lang) {
                lang = lang.toLowerCase();
                if (lang === "en") {
                    return "us";
                }
                if (lang === "de") {
                    return "de";
                }
                for (let i = 0; i < this.countries.length; i++) {
                    if (this.countries[i].languages[0].iso639_1 === lang) {
                        return this.countries[i].alpha2Code;
                    }
                }
                return "country not found";
            },

            getCountry(alpha2Code) {
                for (let i = 0; i < this.countries.length; i++) {
                    if (this.countries[i].alpha2Code.toLowerCase() === alpha2Code.toLowerCase()) {
                        return this.countries[i];
                    }
                }
                return "country not found";
            },

            getLanguageISONativeName(alpha2Code) {
                if (alpha2Code === "BY") {
                    return "Беларуская"
                }
                return this.getCountry(alpha2Code).languages[0].nativeName;
            },

            getSortedLangs(langs) {
                return langs.slice().sort();
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