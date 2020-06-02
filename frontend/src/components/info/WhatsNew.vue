<template>
    <div>
        <OrderedList :name="translate('What\'s new in') + ' v 2.0'" :list="getAppV20Description()"/>
    </div>
</template>

<script>
    import OrderedList from "../element/list/OrderedList";
    import basicComponent from "../../mixin/basicComponent";

    export default {
        name: "WhatsNew",
        components: {OrderedList},
        mixins: [basicComponent],
        methods: {
            getAppV20Description() {
                let inEnglish = " (" + this.translate("currently in English only") + ")";
                return [
                    this.translate("The app is completely redesigned"),
                    this.translate("Wiki Principle: app data is editable. You can add or correct any info") + inEnglish,
                    this.translate("You can add new items: replacers, motorcycle models, units and parts") + inEnglish,
                    this.translate("Replacers got a rating param to collect statistics on their quality and suitability"),
                    this.translate("Manufacturers/sellers and some other links that may be helpful"),
                    this.translate("Oils: filling capacities for units"),
                    this.translate("More detailed specifications for all items"),
                    this.translate("Specifications, manuals, part catalogs, drawings, schemes"),
                    this.translate("Wishlist: you can create a list of parts that you need"),
                    this.translate("All known incorrect info has been corrected")
                ]
            },
        }
    }
</script>

<style scoped>

</style>