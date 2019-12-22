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
    import itemViewUtil from "../../../util/itemViewUtil";

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
                return itemViewUtil.getTitle(this.table);
            },
        }
    }
</script>

<style scoped>

</style>