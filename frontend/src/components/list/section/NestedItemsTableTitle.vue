<template>
    <div>
        <div v-if="isTitleVisible()">
            <hr>
            <p class="title">
                {{getTitle()}}
            </p>
        </div>
    </div>
</template>

<script>
    import shared from "../../../util/shared";

    export default {
        name: "NestedItemsTableTitle",

        props: {
            editMode: Boolean,
            replacers: Boolean,
            table: Object
        },

        methods: {
            isTitleVisible() {
                let list = this.replacers ? this.table.replacers : this.table.parts;
                return shared.isNestedItemsTitleVisible(list, this.editMode);
            },

            getTitle() {
                return this.replacers ? this.$t("replacers") : this.$t("parts");
            },
        }
    }
</script>

<style scoped>

</style>