<template>
    <div>
        <p style="text-align: center">{{replacersTable.localizedName}}</p>
        <table id="replacers-table" style="text-align: center">
            <tbody>
            <tr v-if="notStub && statusIsActive(replacer.status)"
                style="text-align: left"
                v-for="replacer in replacersTable.replacers">
                <td class="three-column-table-left-column">
                    <p v-if="!editMode">{{replacer.comment}}</p>
                    <input v-if="editMode"
                           v-model="replacer.comment"
                           type="text"/>
                </td>
                <td class="three-column-table-middle-column">
                    <button type="button"
                            @click="navigateToItem(replacer.itemId)">
                        {{replacer.buttonText}}
                    </button>
                </td>
                <td class="three-column-table-right-column">{{replacer.rating}}</td>
                <td>
                    <button v-if="isRateButtonVisible(replacer)"
                            type="button"
                            class="round-button"
                            @click="rate('up', replacer.itemId)">
                        {{"&#x2191;"}}
                    </button>
                </td>
                <td>
                    <button v-if="isRateButtonVisible(replacer)"
                            type="button"
                            class="round-button"
                            @click="rate('down', replacer.itemId)">
                        {{" &#x2193;"}}
                    </button>
                    <button v-if="isUnrateButtonVisible(replacer)"
                            type="button"
                            class="round-button"
                            @click="rate('cancel', replacer.itemId)">
                        {{"x"}}
                    </button>
                    <button v-if="editMode"
                            type="button"
                            class="round-button"
                            style="background: red"
                            @click="removeReplacerFromList(replacer)">
                        {{"-"}}
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "ReplacersTable",

        props: {
            replacersTable: Object,
            editMode: Boolean,
            notStub: Boolean,
            ratedItems: Array
        },

        methods: {
            navigateToItem(itemId) {
                this.$emit("navigate-to-item", itemId);
            },

            isRateButtonVisible(replacer) {
                return !this.editMode && !this.isRated(replacer) && !this.$parent.isGuest();
            },

            isUnrateButtonVisible(replacer) {
                return !this.editMode && this.isRated(replacer) && !this.$parent.isGuest();
            },

            isRated(replacer) {
                return this.$parent.isInArray(replacer.itemId, this.ratedItems);
            },

            rate(action, itemId) {
                let rate = {
                    action: action,
                    itemId: itemId
                };
                this.$emit("rate", rate);
            },

            removeReplacerFromList(replacer) {
                this.$parent.removeReplacerFromList(replacer);
            },

            statusIsActive(status) {
                return this.$parent.statusIsActive(status);
            }
        }
    }
</script>

<style scoped>

</style>