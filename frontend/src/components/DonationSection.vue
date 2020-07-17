<template>
    <div class="default-margin">
        <p style="font-weight: bold; font-size: large">
            {{translate("You can help develop the project")}}
        </p>
        <p style="margin-top: 10px; text-align: left">
            {{translate("The project takes a lot of time. Really a lot.")}}
            {{translate("To find out how to help the project, visit our website") + ":"}}
            <a :href="'https://www.sovietboxers.com/#/' + getSovietBoxersLang($route.params.lang)"
               class="simple-link">
                {{"www.sovietboxers.com"}}
            </a>
        </p>
        <br>
        <p>{{"_________"}}</p>
    </div>
</template>

<script>
    import dictionaryUtil from "../util/dictionaryUtil";

    export default {
        name: "DonationSection",

        methods: {
            translate(text) {
                return dictionaryUtil.translate(text);
            },

            b() {
                return "U+1F37A";
            },

            getSovietBoxersLang(appLang) {
                if (appLang == "ru" || appLang == "be" || appLang == "uk") {
                    return "ru";
                }
                return "en";
            }
        }
    }
</script>

<style scoped>
    summary {

    }

    input, img {
        background: initial;
        height: initial;
        width: initial;
        max-height: initial;
        max-width: initial;
        border-radius: initial;
        margin: initial;
    }
</style>