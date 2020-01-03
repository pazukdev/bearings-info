<template>
    <div>
        <button v-if="isRendered(item)"
                type="button"
                class="round-button"
                style="background: red"
                @click="removeItem(item)">
            {{"-"}}
        </button>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import itemViewUtil from "../../../util/itemViewUtil";
    import userUtil from "../../../util/userUtil";

    export default {
        name: "ButtonDelete",

        props: {
            item: Object,
            wishlistView: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView,
                userName: state => state.dictionary.userName,
                editMode: state => state.dictionary.editMode
            })
        },

        methods: {
            isRendered(item) {
                if (!this.editMode || item.deletable === false) {
                    return false;
                }
                return itemViewUtil.isAdmin(this.itemView)
                    || userUtil.isCurrentUserItemCreator(this.userName, item.creatorName)
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