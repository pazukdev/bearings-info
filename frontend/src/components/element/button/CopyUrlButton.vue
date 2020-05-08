<template>
    <div class="default-margin">
        <button @click="copyURL()">{{translate("Get shareable link to the page")}}</button>
        <input type="text" id="current-location-input"
               :class="{'background-dark': !urlCopied}">
        <br><br>
        <p v-if="urlCopied" style="text-align: center" class="green">
            {{translate("Url copied to clipboard")}}
        </p>
        <br>
    </div>
</template>

<script>
    import shared from "../../../util/shared";
    import dictionaryUtil from "../../../util/dictionaryUtil";

    export default {
        name: "CopyUrlButton",

        data() {
            return {
                urlCopied: false
            }
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
                return shared.getCurrentLocation();
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
        /*margin: initial;*/
        padding: initial;
        max-height: initial;
        min-height: initial;
        /*background: initial;*/
    }
</style>