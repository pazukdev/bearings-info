<template>
    <div class="default-margin">
        <button id="copy-url-button" @click="copyURL()">
            {{translate("Get shareable link to the page")}}
        </button>
        <input id="current-location-input" type="text"
               :class="{'background-dark': !urlCopied}">
        <br>
        <p id="url-copied-text" v-if="urlCopied" style="text-align: center" class="green">
            {{translate("Url copied to clipboard")}}
        </p>
    </div>
</template>

<script>
    import shared from "../../../util/shared";
    import dictionaryUtil from "../../../util/dictionaryUtil";
    import {mapState} from "vuex";
    import routerUtil from "../../../util/routerUtil";

    export default {
        name: "CopyUrlButton",

        data() {
            return {
                urlCopied: false
            }
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            })
        },

        watch: {
            '$route': 'onUrlChange'
        },

        methods: {
            onUrlChange() {
                this.urlCopied = false;
                let currentLocationInput = document.getElementById("current-location-input");
                currentLocationInput.value = "";
            },

            copyURL() {
                this.urlCopied = true;
                let currentLocationInput = document.getElementById("current-location-input");
                currentLocationInput.value = this.getCurrentLocation();
                currentLocationInput.select();
                currentLocationInput.setSelectionRange(0, 99999);
                document.execCommand("copy");
                console.log("Url copied: " + currentLocationInput.value);
            },

            getCurrentLocation() {
                let url = shared.getCurrentLocation();
                if (routerUtil.isItem(this.$route)) {
                    let id = this.itemView.category + "&" + this.itemView.name;
                    url = url.toString().replace(this.$route.params.id, id.replace(/\s+/g, '_'));
                }
                return url;
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>
    table {
        border-spacing: initial;
        border-collapse: initial;
    }

    input, button {
        border-radius: initial;
        height: initial;
        padding: initial;
        max-height: initial;
        min-height: initial;
    }
</style>