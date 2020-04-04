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
    import {mapState} from "vuex";
    import userUtil from "../../../util/userUtil";
    import dictionaryUtil from "../../../util/dictionaryUtil";

    export default {
        name: "ButtonDelete",

        props: {
            item: Object,
            wishlistView: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode
            })
        },

        methods: {
            isRendered(item) {
                if (!this.editMode || item.deletable === false) {
                    return false;
                }
                return userUtil.isAdmin(this.itemView)
                    || userUtil.isCurrentUserItemCreator(item.creatorId)
                    || this.wishlistView;
            },

            removeItem(item) {
                this.$emit("remove-item", item);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>
    .round-button {
        height: initial;
        width: initial;
        min-height: initial;
        max-height: initial;
        border-radius: initial;
        /*border: 2px solid grey;*/
        font-size: x-large;
        font-weight: bold;
        background: none;
    }
</style>