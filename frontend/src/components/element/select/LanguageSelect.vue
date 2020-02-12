<template>
    <div>
        <select v-model="newLanguage"
                @change="selectLanguage()">
            <option v-for="lang in langs" :key="lang" v-if="lang !== 'all'">
                {{lang}}
            </option>
        </select>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import axiosUtil from "../../../util/axiosUtil";
    import routerUtil from "../../../util/routerUtil";

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
                    this.$store.dispatch("setAppLanguage", this.newLanguage);
                }
                this.$i18n.locale = this.newLanguage;
            },

            selectLanguage() {
                routerUtil.setLang(this.newLanguage);
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