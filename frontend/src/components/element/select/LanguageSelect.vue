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
            if (this.langs.length === 0 && this.newLanguage === "en") {
                console.log("On create: set dictionary");
                let lang = this.$route.params.lang;
                axiosUtil.setLangsAndDictionary(lang);
            }
        },

        methods: {
            onUrlChange() {
                let urlLang = this.$route.params.lang;
                if (urlLang !== this.newLanguage) {
                    this.newLanguage = urlLang;
                    if (routerUtil.isLogin(this.$route) || routerUtil.isMenu(this.$route)) {
                        // this.selectLanguage();
                    }
                    // storeUtil.setAppLang(this.newLanguage);
                    // if (this.newLanguage !== "en") {
                    //     console.log("On url change: set dictionary");
                    //     axiosUtil.setLangsAndDictionary();
                    // }
                    // this.selectLanguage();
                }
            },

            selectLanguage() {
                // storeUtil.setAppLang(this.newLanguage);
                // routerUtil.setLang(this.newLanguage, this.$route);
                // if (this.newLanguage !== "en") {
                //     console.log("On lang select: set dictionary");
                //     axiosUtil.setLangsAndDictionary();
                // }
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