<template>
    <div>
<!--        {{newLanguage}}-->
        <select v-model="newLanguage"
                @change="selectLanguage()">
            <option v-for="lang in ['en', 'ru']" :key="lang">
                {{lang}}
            </option>
        </select>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import itemViewUtil from "../../../util/itemViewUtil";

    export default {
        name: "LanguageSelect",

        computed: {
            ...mapState({
                appLanguage: state => state.dictionary.appLanguage
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
            this.newLanguage = this.appLanguage;
            this.onUrlChange();
        },

        methods: {
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