<template>
    <div>
        <p style="text-align: center">{{replacersTable.localizedName}}</p>
        <table id="replacers-table" style="text-align: center">
            <tbody>
            <tr v-if="notStub && statusIsActive(replacer.status)"
                style="text-align: left"
                v-for="replacer in sortedReplacers">
                <td class="bordered">
                    <table>
                        <tbody>
                        <tr>
                            <td class="not-symmetrical-left">
                                <button type="button"
                                        @click="navigateToItem(replacer.itemId)">
                                    {{replacer.buttonText}}
                                </button>
                            </td>
                            <td class="not-symmetrical-right">
                                {{replacer.rating}}
                            </td>
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
                        <tr>
                            <td colspan="4">
                                <p style="text-align: left" v-if="!editMode">{{replacer.comment}}</p>
                                <textarea v-if="editMode" v-model="replacer.comment"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";

    export default {
        name: "ReplacersTable",

        props: {
            replacersTable: Object,
            editMode: Boolean,
            notStub: Boolean,
            ratedItems: Array
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName
            }),

            sortedReplacers() {
                let replacers = this.replacersTable.replacers;
                return replacers.sort((a, b) => (a.rating < b.rating) ? 1 : -1);
            }
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
                    itemId: itemId,
                    ratedItems: this.ratedItems,
                    replacers: this.replacersTable.replacers
                };
                axios
                    .put(this.basicUrl
                        + "/" + "item/rate-replacer"
                        + "/" + this.userName,
                        rate, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                    .then(response => {
                        this.$emit("update-replacers", response.data);
                        console.log("Replacer rate action performed: "
                            + "user name: " + this.userName
                            + ", action: " + rate.action
                            + ", item id: " + rate.itemId);
                    });
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
    td {
        padding: initial;
    }
</style>