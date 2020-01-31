<template>
    <div>
        <select v-model="selectedItem" @change="onChange">
            <option v-for="item in actualPossibleItemsList" :value="item">
                {{item.selectText}}
            </option>
        </select>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "ItemSelect",

        props: {
            replacer: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            }),

            actualPossibleItemsList() {
                return this.getActualPossibleItemsList();
            }
        },

        data() {
            return {
                selectedItemMessage: "",
                selectedItem: ""
            }
        },

        methods: {
            getActualPossibleItemsList() {
                let possibleItems;
                let items;
                if (this.replacer) {
                    possibleItems =  this.itemView.possibleReplacers;
                    items = this.itemView.replacersTable.replacers;
                } else {
                    possibleItems = this.itemView.possibleParts;
                    items = this.itemView.children;
                }

                let actualPossibleItems = [];
                for (let i=0; i < possibleItems.length; i++) {
                    if (!this.isInList(possibleItems[i], items)) {
                        actualPossibleItems.push(possibleItems[i]);
                    }
                }

                if (actualPossibleItems.length < 1){
                    this.$emit("hide-add-form");
                }

                return actualPossibleItems;
            },

            isInList(item, list) {
                for (let i=0; i < list.length; i++) {
                    if (list[i].itemId === item.itemId) {
                        return true;
                    }
                }
                return false;
            },

            onChange() {
                this.$emit("on-change", this.selectedItem);
            }
        }
    }
</script>

<style scoped>

</style>