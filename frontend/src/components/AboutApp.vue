<template>
    <div class="default-margin">
        <details>
            <summary>{{translate("About the app")}}</summary>
            <br>
            <div style="text-align: right">
                <a href="https://info.flagcounter.com/OWa2">
                    <img id="flag-counter" alt="Flag Counter" border="0"
                         src="https://s11.flagcounter.com/count2/Vx7O/bg_212121/txt_808080/border_212121/columns_2/maxflags_20/viewers_0/labels_1/pageviews_0/flags_0/percent_0/">
                </a>
            </div>
            <div class="default-margin">
                {{translate(aboutTheApp())}}
                <GooglePlayLink/>
                <WhatsNew/>
                <AppGroupsSection :beer-glass-rendered="true"/>
            </div>
        </details>
    </div>
</template>

<script>
    import dictionaryUtil from "../util/dictionaryUtil";
    import AppGroupsSection from "./AppGroupsSection";
    import OrderedList from "./element/list/OrderedList";
    import WhatsNew from "./info/WhatsNew";
    import GooglePlayLink from "./element/GooglePlayLink";

    export default {
        name: "AboutApp",

        components: {GooglePlayLink, WhatsNew, OrderedList, AppGroupsSection},

        methods: {
            translate(text) {
                return dictionaryUtil.translate(text);
            },

            aboutTheApp() {
                return "E-catalog focused on standard parts and their modern high quality analogs"
                    + " for soviet sidecar motorcycles manufactured by IMZ and KMZ:"
                    + " Ural (Cossack), Dnepr (Neval), MV-750, K-750, K-650, M-72";
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