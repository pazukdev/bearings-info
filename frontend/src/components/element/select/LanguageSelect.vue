<template>
    <div>
        <select v-model="newLanguage"
                @change="selectLanguage()">
            <option v-for="lang in langs" :key="lang">
                {{lang}}
            </option>
        </select>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import axiosUtil from "../../../util/axiosUtil";
    import routerUtil from "../../../util/routerUtil";
    import storeUtil from "../../../util/storeUtil";

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
            this.setLangsList();
            this.newLanguage = this.appLanguage;
            this.onUrlChange();
        },

        methods: {
            setLangsList() {
                if (this.newLanguage !== "en") {
                    axiosUtil.setLangsAndDictionary();
                }
            },

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