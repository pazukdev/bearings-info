<template>
    <div class="default-margin">
        <table>
            <tbody>
            <tr>
                <td><button @click="copyURL()">{{translate("Share")}}</button></td>
                <td><input type="text" id="current-location-input"></td>
            </tr>
            </tbody>
        </table>
        <br>
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
                inputDisplayed: false
            }
        },

        watch: {
            '$route': 'onUrlChange'
        },

        methods: {
            onUrlChange() {
                let currentLocationInput = document.getElementById("current-location-input");
                currentLocationInput.value = "";
            },

            copyURL() {
                this.inputDisplayed = true;
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