<template>
    <div>
        <button v-if="isRendered(item)"
                type="button"
                class="round-button red"
                :title="translate('Delete')"
                @click="$emit('remove-item', item)">
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
            itemsManagementView: Boolean,
            wishlistView: Boolean,
            forceRender: Boolean
        },

        methods: {
            isRendered(item) {
                if (this.forceRender) {
                    return true;
                }
                if (this.isEditable(item) && !this.itemsManagementView) {
                    return true;
                }
                if (!this.editMode || item.deletable === false) {
                    return false;
                }
                if (this.isAdmin(this.itemView) || this.wishlistView) {
                    return true;
                }
                if (this.itemsManagementView) {
                    return userUtil.isCurrentUserItemCreator(item.creatorId);
                }
                return this.isEditor();

            }
        }
    }
</script>

<style scoped>

</style>