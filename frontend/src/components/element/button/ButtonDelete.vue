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
            }
        }
    }
</script>

<style scoped>

</style>