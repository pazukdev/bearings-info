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
    import itemViewUtil from "../../itemViewUtil";

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
                    || this.currentUserIsCreator(item)
                    || this.wishlistView;
            },

            currentUserIsCreator(item) {
                return item.creatorName === this.userName.toString();
            },

            removeItem(item) {
                this.$emit("remove-item", item);
            }
        }
    }
</script>

<style scoped>

</style>