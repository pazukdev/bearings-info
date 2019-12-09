<template>
    <div>
        <select v-model="selectedItem"
                @change="onChange">
            <option v-for="item in sortPossibleItems()"
                    :value="item">
                {{item.selectText}}
            </option>
        </select>
    </div>
</template>

<script>
    export default {
        name: "ItemSelect",

        props: {
            parentItemId: Number,
            items: Array,
            possibleItems: Array
        },

        computed: {
            sortedPossibleItems() {
                let possibleItems = [];
                for (let i=0; i < this.possibleItems.length; i++) {
                    let item = this.possibleItems[i];
                    if (this.isNotThisItem(item.itemId)
                        && !this.itemIsAlreadyInList(item.itemId)
                        && this.statusIsActive(item.status)) {
                        possibleItems.push(item);
                    }
                }
                return possibleItems.sort((a, b) => (a.selectText > b.selectText) ? 1 : -1);
            }
        },

        data() {
            return {
                selectedItemMessage: "",
                selectedItem: ""
            }
        },

        methods: {
            sortPossibleItems() {
                let possibleItems = [];
                for (let i=0; i < this.possibleItems.length; i++) {
                    let item = this.possibleItems[i];
                    if (this.isNotThisItem(item.itemId)
                        && !this.itemIsAlreadyInList(item.itemId)
                        && this.statusIsActive(item.status)) {
                        possibleItems.push(item);
                    }
                }
                this.$emit("show-add-form", possibleItems.length > 0);
                return possibleItems.sort((a, b) => (a.selectText > b.selectText) ? 1 : -1);
            },

            onChange() {
                this.$emit("on-change", this.selectedItem);
            },

            isNotThisItem(itemId) {
                return itemId !== this.parentItemId;
            },

            itemIsAlreadyInList(itemId) {
                for (let i=0; i < this.items.length; i++) {
                    if (this.items[i].itemId === itemId) {
                        return true;
                    }
                }
                return false;
            },

            statusIsActive(status) {
                return this.$parent.statusIsActive(status);
            }
        }
    }
</script>

<style scoped>

</style>