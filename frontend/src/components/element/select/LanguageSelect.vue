<template>
    <div class="bordered" style="white-space: nowrap">
<!--        <flag :iso="newLanguage"/>-->
        {{translate("Language") + ": "}}
        <select v-model="newLanguage"
                @change="selectLanguage()">
            <option v-for="lang in langs" :key="lang" :value="lang">
                {{lang}}
            </option>
        </select>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import routerUtil from "../../../util/routerUtil";
    import storeUtil from "../../../util/storeUtil";
    import axios from "axios";
    import store from "../../../plugins/store";
    import dictionaryUtil from "../../../util/dictionaryUtil";

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
                    this.setLangsAndDictionary();
                }
            },

            setLangsAndDictionary() {
                axios
                    .get(store.getters.basicUrl
                        + "/" + "file"
                        + "/" + "dictionary-data"
                        + "/" + store.getters.appLanguage, {
                        headers: {
                            Authorization: store.getters.authorization
                        }
                    })
                    .then(response => {
                        let dictionaryData = response.data;
                        let langs = dictionaryData.langs;

                        console.log("got langs: " + langs);
                        storeUtil.setLangs(langs);
                        storeUtil.setDictionary(dictionaryData.dictionary);
                    });
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