<template>
    <div>
        <p style="text-align: center">{{itemView.replacersTable.localizedName}}</p>
        <table id="replacers-table" style="text-align: center">
            <tbody>
            <tr style="text-align: left"
                v-for="item in sortedReplacers">
                <td class="bordered">
                    <table>
                        <tbody>
                        <tr>
                            <td class="not-symmetrical-left">
                                <ButtonNavigateToItem :part="item"/>
                            </td>
                            <td class="not-symmetrical-right">
                                {{item.rating}}
                            </td>
                            <td style="min-width: 32px">
                                <button v-if="isRateButtonVisible(item)"
                                        type="button"
                                        class="round-button"
                                        @click="rate('up', item.itemId)">
                                    {{"&#x2191;"}}
                                </button>
                            </td>
                            <td>
                                <button v-if="isRateButtonVisible(item)"
                                        type="button"
                                        class="round-button"
                                        @click="rate('down', item.itemId)">
                                    {{" &#x2193;"}}
                                </button>
                                <button v-if="isUnrateButtonVisible(item)"
                                        type="button"
                                        class="round-button"
                                        @click="rate('cancel', item.itemId)">
                                    {{"x"}}
                                </button>
                                <button v-if="editMode"
                                        type="button"
                                        class="round-button"
                                        style="background: red"
                                        @click="removeItem(item)">
                                    {{"-"}}
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <p style="text-align: left" v-if="!editMode">{{item.comment}}</p>
                                <textarea v-if="editMode" v-model="item.comment"/>
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
    import shared from "../shared";
    import ButtonNavigateToItem from "./ButtonNavigateToItem";
    import itemViewUtil from "../itemViewUtil";

    export default {
        name: "ReplacersTable",

        components: {
            ButtonNavigateToItem
        },

        props: {
            editMode: Boolean
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                itemView: state => state.dictionary.itemView
            }),

            sortedReplacers() {
                return this.getReplacers().sort((a,b) => (a.rating < b.rating) ? 1 : -1);
            }
        },

        methods: {
            getReplacers() {
                return this.itemView.replacersTable.replacers;
            },

            getRatedItems() {
                return this.itemView.ratedItems;
            },

            isRateButtonVisible(item) {
                return !this.editMode && !this.isRated(item) && !this.isGuest();
            },

            isUnrateButtonVisible(item) {
                return !this.editMode && this.isRated(item) && !this.isGuest();
            },

            isRated(item) {
                return shared.isInArray(item, this.getRatedItems());
            },

            isGuest() {
                return itemViewUtil.isGuest(this.itemView, this.userName);
            },

            rate(action, itemId) {
                let rate = {
                    action: action,
                    itemId: itemId,
                    ratedItems: this.getRatedItems(),
                    replacers: this.getReplacers()
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
                        let rateReplacer = response.data;
                        this.getRatedItems = rateReplacer.ratedItems;
                        this.getReplacers = rateReplacer.replacers;
                        console.log("Replacer rate action performed: "
                            + "user name: " + this.userName
                            + ", action: " + rate.action
                            + ", item id: " + rate.itemId);
                    });
            },

            removeItem(item) {
                shared.removeFromArray(item, this.getReplacers());
                this.$emit("show-add-form");
            }
        }
    }
</script>

<style scoped>
    td {
        padding: initial;
    }
</style>