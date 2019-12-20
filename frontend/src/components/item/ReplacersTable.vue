<template>
    <div>
        <NestedItemsTableTitle :edit-mode="editMode" :replacers="true" :table="itemView.replacersTable"/>
        <table id="replacers-table" style="text-align: center">
            <tbody>
            <tr style="text-align: left"
                v-for="item in sortedReplacers()">
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
                            <td>
                                <button v-if="isLikedIndicatorVisible(item)"
                                        type="button"
                                        class="round-button"
                                        @click="rate('cancel', item.itemId)">
                                    {{"&#128077;"}}
                                </button>
                                <button v-if="isRateButtonVisible(item)"
                                        type="button"
                                        class="round-button"
                                        style="background: none"
                                        @click="rate('up', item.itemId)">
                                    {{"&#128077;"}}
                                </button>
                            </td>
                            <td>
                                <button v-if="isDislikedIndicatorVisible(item)"
                                        type="button"
                                        class="round-button"
                                        @click="rate('cancel', item.itemId)">
                                    {{"&#128078;"}}
                                </button>
                                <button v-if="isRateButtonVisible(item)"
                                        type="button"
                                        class="round-button"
                                        style="background: none"
                                        @click="rate('down', item.itemId)">
                                    {{"&#128078;"}}
                                </button>
                            </td>
                            <td>
                                <div style="width: 32px" v-if="isUnrateButtonVisible(item)"/>
                                <ButtonDelete :item="item" @remove-item="removeItem"/>
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
    import shared from "../../util/shared";
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import itemViewUtil from "../../util/itemViewUtil";
    import NestedItemsTableTitle from "../NestedItemsTableTitle";
    import ButtonDelete from "../element/button/ButtonDelete";

    export default {
        name: "ReplacersTable",

        components: {
            ButtonDelete,
            ButtonNavigateToItem,
            NestedItemsTableTitle
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                editMode: state => state.dictionary.editMode,
                itemView: state => state.dictionary.itemView
            })
        },

        methods: {
            sortedReplacers() {
                return this.getReplacers().slice().sort((a,b) => (a.rating < b.rating) ? 1 : -1);
            },

            getReplacers() {
                return this.itemView.replacersTable.replacers;
            },

            getLikedItemsIds() {
                return this.itemView.likeList.likedItemsIds;
            },

            getDislikedItemsIds() {
                return this.itemView.likeList.dislikedItemsIds;
            },

            isRateButtonVisible(item) {
                if (this.editMode || this.isGuest()) {
                    return false;
                } else {
                    return !this.isRated(item);
                }
            },

            isUnrateButtonVisible(item) {
                if (this.editMode || this.isGuest()) {
                    return false;
                } else {
                    return this.isRated(item);
                }
            },

            isRated(item) {
                return this.isLiked(item) || this.isDisliked(item);
            },

            isLikedIndicatorVisible(item) {
                return !this.editMode && this.isLiked(item);
            },

            isDislikedIndicatorVisible(item) {
                return !this.editMode && this.isDisliked(item);
            },

            isLiked(item) {
                return shared.isInArray(item.itemId, this.getLikedItemsIds());
            },

            isDisliked(item) {
                return !this.editMode && shared.isInArray(item.itemId, this.getDislikedItemsIds());
            },

            isGuest() {
                return itemViewUtil.isGuest(this.itemView, this.userName);
            },

            rate(action, itemId) {
                let rate = {
                    action: action,
                    itemId: itemId,
                    likeList: this.itemView.likeList,
                    replacers: this.getReplacers()
                };
                axios
                    .put(this.basicUrl
                        + "/" + "replacer/rate"
                        + "/" + this.userName,
                        rate, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                    .then(response => {
                        let rateReplacer = response.data;
                        this.itemView.likeList = rateReplacer.likeList;
                        this.itemView.replacersTable.replacers = rateReplacer.replacers;
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