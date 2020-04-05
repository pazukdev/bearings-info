<template>
    <div>
        <button v-if="isRendered(item)"
                type="button"
                class="round-button red"
                :title="translate('Delete')"
                @click="removeItem(item)">
            {{"&times;"}}
        </button>
    </div>
</template>

<script>
    import userUtil from "../../../util/userUtil";
    import basicComponent from "../../../mixin/basicComponent";
    import view from "../../../mixin/view";

    export default {
        name: "ButtonDelete",

        mixins: [basicComponent, view],

        props: {
            item: Object,
            wishlistView: Boolean
        },

        methods: {
            isRendered(item) {
                if (!this.editMode || item.deletable === false) {
                    return false;
                }
                return this.isAdmin(this.itemView)
                    || this.isEditor()
                    || userUtil.isCurrentUserItemCreator(item.creatorId)
                    || this.wishlistView;
            },

            removeItem(item) {
                this.$emit("remove-item", item);
            }
        }
    }
</script>

<style scoped>

</style>