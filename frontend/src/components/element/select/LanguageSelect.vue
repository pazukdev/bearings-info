<template>
    <div class="bordered" style="white-space: nowrap">
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
    import axiosUtil from "../../../util/axiosUtil";
    import basicComponent from "../../../mixin/basicComponent";
    import view from "../../../mixin/view";

    export default {
        name: "LanguageSelect",

        mixins: [basicComponent, view],

        computed: {
            ...mapState({
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