<template>
    <div>
        <select v-model="selectedItem"
                @change="onChange">
            <option v-for="item in items"
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
            items: Array
        },

        data() {
            return {
                selectedItemMessage: "",
                selectedItem: ""
            }
        },

        methods: {
            selectOptionVisible(option) {
                return true;
            },

            onChange() {
                this.$emit("on-change", this.selectedItem)
            },

            // selectOptionVisible(option) {
            //     return this.statusActive(option) && this.isNotThisItem(option);
            // },
        }
    }
</script>

<style scoped>

</style>