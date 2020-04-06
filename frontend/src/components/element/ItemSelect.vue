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
    import arrayUtil from "../../util/arrayUtil";
    import itemDtoUtil from "../../util/itemDtoUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: "ItemSelect",

        props: {
            replacer: Boolean
        },

        mixins: [basicComponent, view],

        data() {
            return {
                selectedItemMessage: "",
                selectedItem: ""
            }
        },

        methods: {
            getActualPossibleItemsList() {
                let possibleItems;
                let deletedItems;
                let items;

                if (this.replacer) {
                    possibleItems =  this.itemView.possibleReplacers;
                    deletedItems = this.itemView.deletedReplacers;
                    items = this.itemView.replacersTable.replacers.slice();
                } else {
                    possibleItems = this.itemView.possibleParts;
                    deletedItems = this.itemView.deletedChildren;
                    items = this.itemView.children.slice();
                }

                if (!this.isEmpty(deletedItems) && deletedItems.length > 0) {
                    items = items.concat(deletedItems);
                }

                let actualPossibleItems = [];
                for (let i=0; i < possibleItems.length; i++) {
                    let item = possibleItems[i];
                    if (!this.isInList(item, items)) {
                        item.selectText = itemDtoUtil.createSelectText(item);
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
            }
        }
    }
</script>

<style scoped>

</style>