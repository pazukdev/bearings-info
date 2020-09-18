<template>
    <div style="text-align: center">
        <DeletedItemsList :array="getDeletedItems()" @restore="restore"/>

        <p class="green">{{translate(message)}}</p>

        <LoadingScreen v-if="loading"/>
        <div v-else>
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
                                            :title="getLikeTitle(item, true)"
                                            @click="rate(item, 'like')">
                                        <i :class="{'fa fa-thumbs-up': true, 'green': isLiked(item)}"/>
                                    </button>
                                    <span :class="{'green': isColored(getLikedUsers(item).length)}">
                                    {{getLikedUsers(item).length}}
                                </span>
                                    <button type="button"
                                            class="round-button"
                                            :title="getLikeTitle(item, false)"
                                            @click="rate(item, 'dislike')">
                                        <i :class="{'fa fa-thumbs-down': true, 'red': isDisliked(item)}"/>
                                    </button>
                                    <span :class="{'red': isColored(getDislikedUsers(item).length)}">
                                    {{0 - getDislikedUsers(item).length}}
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
import itemDtoUtil from "../../util/itemDtoUtil";
import LoadingScreen from "../special/LoadingScreen";

export default {
        name: "ReplacerList",

        components: {LoadingScreen, DeletedItemsList, ButtonDelete, ButtonNavigateToItem},

        mixins: [basicComponent, view],

        computed: {
            ...mapState({
                countries: state => state.dictionary.countries
            })
        },

        data() {
            return {
                message: "",
                loading: false
            }
        },

        watch: {
            '$route': 'onUrlChange'
        },

        methods: {
            onUrlChange() {
                this.loading = false;
            },

            getLikeTitle(item, like) {
                let header = this.isDisliked(item) ? 'Cancel' : 'Dislike';
                if (like) {
                    header = this.isLiked(item) ? 'Cancel' : 'Like';
                }
                header = this.translate(header);
                let users = "";
                let array = like ? this.getLikedUsers(item) : this.getDislikedUsers(item);
                if (array.length === 0) {
                    return header;
                }
                for (let i = 0; i < array.length; i++) {
                    let countryAlpha2Code = array[i].comment;
                    let countryName = this.getCountryName(countryAlpha2Code);
                    let userCountryInfo = this.isEmpty(countryName)
                        ? ""
                        : " " + this.translate("from") + " " + this.translate(countryName);
                    users += array[i].itemName + userCountryInfo + "\n";
                }
                return header + "\n- - - - -\n" + users;
            },

            getCountryName(alpha2Code) {
                return shared.getCountryName(alpha2Code);
            },

            getLikedUsers(item) {
                return item.likedUsers;
            },

            getDislikedUsers(item) {
                return item.dislikedUsers;
            },

            isColored(likesCount) {
                return likesCount > 0;
            },

            sortedReplacers() {
                return this.getItems().slice().sort((a,b) => {
                    let aRating = this.getLikedUsers(a).length - this.getDislikedUsers(a).length;
                    let bRating = this.getLikedUsers(b).length - this.getDislikedUsers(b).length;
                    return aRating < bRating ? 1 : -1
                });
            },

            getItems() {
                return this.itemView.replacersTable.replacers;
            },

            getDeletedItems() {
                return this.itemView.deletedReplacers;
            },

            isRated(item) {
                return this.isLiked(item) || this.isDisliked(item);
            },

            isLiked(item) {
                return itemDtoUtil.isInArrayById(this.userData.id, this.getLikedUsers(item));
            },

            isDisliked(item) {
                return itemDtoUtil.isInArrayById(this.userData.id, this.getDislikedUsers(item));
            },

            rate(item, actionType) {
                if (this.isGuest()) {
                    this.message = "To rate the replacer, please log in";
                    return;
                }

                this.loading = true;
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
                        this.itemView.cachedViews = rateReplacer.cachedViews;
                        this.userData.rating = rateReplacer.newUserRating;
                        console.log("Replacer rate action performed: "
                            + "user name: " + this.getUserName()
                            + ", action: " + rate.action
                            + ", item id: " + rate.itemId);
                        this.loading = false;
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