<template>
    <div style="text-align: center">
        <DeletedItemsList :array="getDeletedItems()" @restore="restore"/>

        <p class="green">{{translate(message)}}</p>

        <table id="replacers-table" style="text-align: center">
            <tbody>
            <tr style="text-align: left" v-for="item in sortedReplacers()">
                <td class="bordered">
                    <table>
                        <tbody>
                        <tr v-if="editMode">
                            <td colspan="2" style="text-align: right">
                                <ButtonDelete style="display: inline-block"
                                              :item="item"
                                              :item-view-prop="true"
                                              @remove-item="removeItem"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="not-symmetrical-left">
                                <ButtonNavigateToItem :part="item"/>
                            </td>
                            <td style="text-align: right">
                                <button type="button"
                                        class="round-button"
                                        :title="translate(isLiked(item) ? 'Cancel' : 'Like')"
                                        @click="rate(item, 'like')">
                                    <i :class="{'fa fa-thumbs-up': true, 'green': isLiked(item)}"/>
                                </button>
                                <span :class="{'green': isColored(item.likedUserIds.length)}">
                                    {{item.likedUserIds.length}}
                                </span>
                                <button type="button"
                                        class="round-button"
                                        :title="translate(isDisliked(item) ? 'Cancel' : 'Dislike')"
                                        @click="rate(item, 'dislike')">
                                    <i :class="{'fa fa-thumbs-down': true, 'red': isDisliked(item)}"/>
                                </button>
                                <span :class="{'red': isColored(item.dislikedUserIds.length)}">
                                    {{0 - item.dislikedUserIds.length}}
                                </span>
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
    import ButtonDelete from "../element/button/ButtonDelete";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";
    import DeletedItemsList from "../element/DeletedItemsList";

    export default {
        name: "ReplacerList",

        components: {DeletedItemsList, ButtonDelete, ButtonNavigateToItem},

        mixins: [basicComponent, view],

        computed: {
            ...mapState({
                userData: state => state.dictionary.userData
            })
        },

        data() {
            return {
                message: ""
            }
        },

        methods: {
            isColored(likesCount) {
                return likesCount > 0;
            },

            sortedReplacers() {
                return this.getItems().slice().sort((a,b) => {
                    let aRating = a.likedUserIds.length - a.dislikedUserIds.length;
                    let bRating = b.likedUserIds.length - b.dislikedUserIds.length;
                    return aRating < bRating ? 1 : -1
                });
            },

            getItems() {
                return this.itemView.replacersTable.replacers;
            },

            getDeletedItems() {
                return this.itemView.deletedReplacers;
            },

            getLikedItemsIds() {
                return this.itemView.likeList.likedItemsIds;
            },

            getDislikedItemsIds() {
                return this.itemView.likeList.dislikedItemsIds;
            },

            isRated(item) {
                return this.isLiked(item) || this.isDisliked(item);
            },

            isLiked(item) {
                return shared.isInArray(this.userData.id, item.likedUserIds);
            },

            isDisliked(item) {
                return shared.isInArray(this.userData.id, item.dislikedUserIds);
            },

            rate(item, actionType) {
                if (this.isGuest()) {
                    this.message = "To rate the replacer, please log in";
                    return;
                }

                let cancel = "cancel";
                let action = this.isLiked(item) ? cancel : "up";
                if (!shared.isEmpty(actionType) && actionType.toLowerCase() === "dislike") {
                    action = this.isDisliked(item) ? cancel : "down";
                }

                let rate = {
                    action: action,
                    itemId: item.itemId,
                    replacers: this.getItems()
                };
                axios
                    .put(this.basicUrl
                        + "/" + "replacer/rate"
                        + "/" + this.getUserName(),
                        rate, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                    .then(response => {
                        let rateReplacer = response.data;
                        this.itemView.replacersTable.replacers = rateReplacer.replacers;
                        this.itemView.userData.rating = rateReplacer.newUserRating;
                        console.log("Replacer rate action performed: "
                            + "user name: " + this.getUserName()
                            + ", action: " + rate.action
                            + ", item id: " + rate.itemId);
                    });
            },

            removeItem(item) {
                this.getDeletedItems().push(item);
                shared.removeFromArray(item, this.getItems());
                this.$emit("show-add-form");
            },

            restore(item) {
                this.getItems().push(item);
                shared.removeFromArray(item, this.getDeletedItems());
            }
        }
    }
</script>

<style scoped>
    td {
        padding: initial;
    }

    .round-button {
        background: none;
        margin-left: 2px;
        margin-right: 2px;
        font-size: x-large;
        color: grey;
    }

</style>