<template>
    <div>
<!--        {{getActualPossibleItemsList()[0].selectText}}-->
        <select v-model="selectedItem" @change="onChange">
            <option v-for="item in getActualPossibleItemsList()" :value="item">
                {{item.selectText}}
            </option>
        </select>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import arrayUtil from "../../util/arrayUtil";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import itemDtoUtil from "../../util/itemDtoUtil";

    export default {
        name: "ItemSelect",

        props: {
            replacer: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            })
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
                    let item = possibleItems[i];
                    if (!this.isInList(item, items)) {
                        item.selectText = itemDtoUtil.createSelectText(item, this.replacer);
                        actualPossibleItems.push(item);
                    }
                }

                if (actualPossibleItems.length < 1){
                    this.$emit("hide-add-form");
                }

                return arrayUtil.sortBySelectText(actualPossibleItems);
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
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>

</style>