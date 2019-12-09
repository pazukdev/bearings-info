<template>
    <div>
        <select v-model="selectedItem"
                @change="onChange">
            <option v-for="item in collection"
                    v-if="selectOptionVisible(item)"
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
            collection() {
                return this.possibleItems.sort((a, b) => (a.selectText > b.selectText) ? 1 : -1);
            }
        },

        data() {
            return {
                selectedItemMessage: "",
                selectedItem: ""
            }
        },

        methods: {
            sort() {
                let list = this.possibleItems;
                list.sort((a, b) => (a.color > b.color) ? 1 : -1);
                // array.sort((a, b) => (a.manufacturer > b.manufacturer)
                //     ? 1
                //     : (a.manufacturer === b.manufacturer) ? ((a.size > b.size) ? 1 : -1) : -1 )
                return list;
            },

            onChange() {
                this.$emit("on-change", this.selectedItem)
            },

            selectOptionVisible(option) {
                return this.isNotThisItem(option.itemId)
                    && !this.itemIsAlreadyInList(option.itemId)
                    && this.statusIsActive(option.status);
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
                return status === "active";
            }
        }
    }
</script>

<style scoped>

</style>