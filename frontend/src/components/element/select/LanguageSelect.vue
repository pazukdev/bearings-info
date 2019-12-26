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
    import itemViewUtil from "../../../util/itemViewUtil";
    import axiosUtil from "../../../util/axiosUtil";

    export default {
        name: "LanguageSelect",

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                appLanguage: state => state.dictionary.appLanguage,
                langs: state => state.dictionary.langs
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
                axiosUtil.setSupportedLangs(this.basicUrl, this.authorization);
            },

            onUrlChange() {
                itemViewUtil.setLocale(this.$router, this.$route, this.$i18n, this.newLanguage);
            },

            selectLanguage() {
                let lang = this.newLanguage;
                this.$store.dispatch("setAppLanguage", lang);
                itemViewUtil.changeLanguageInUrl(this.$router, lang);
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