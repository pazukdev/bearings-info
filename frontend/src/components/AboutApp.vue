<template>
    <div class="default-margin">
        <details>
            <summary>{{translate("About the app")}}</summary>
            <div class="default-margin">
                {{translate(aboutTheApp())}}
                <br>
                <br>
                <div style="text-align: right">
                    <a href="https://info.flagcounter.com/OWa2">
                        <img id="flag-counter" alt="Flag Counter" border="0"
                             src="https://s11.flagcounter.com/count2/Vx7O/bg_212121/txt_808080/border_212121/columns_2/maxflags_10/viewers_0/labels_1/pageviews_0/flags_0/percent_0/">
                    </a>
                </div>
                <hr>
            </div>
        </details>
    </div>
</template>

<script>
    import dictionaryUtil from "../util/dictionaryUtil";

    export default {
        name: "AboutApp",

        methods: {
            translate(text) {
                return dictionaryUtil.translate(text);
            },

            aboutTheApp() {
                return "Information about seals, bearings and other parts of soviet boxers and some other old or "
                    + "rare vehicles";
            }
        }
    }
</script>

<style scoped>
    #flag-counter {
        max-height: initial;
        max-width: initial;
    }
</style>