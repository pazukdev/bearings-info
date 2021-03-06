<template>
    <div>
        <LoadingScreen v-if="isLoading()"/>
        <transition name="slide-fade">
            <div v-if="!isLoading()" style="text-align: center">
                <p><b>{{user.name}}</b></p>
                <EditableImg/>
                <EditPanel v-if="isEditable()" :user-form="true"/>
                <AlertMessagesSection :messages="validationMessages"/>
                <form id="user-form" @submit="submit">
                    <v-details v-if="editMode" v-model="changePasswordOpened">
                        <summary class="default-margin">{{translate("Change password")}}</summary>
                        <table class="equal-columns-table">
                            <tbody>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    {{translate("To change password input your old password")}}
                                </td>
                            </tr>
                            <tr>
                                <td>{{translate("Old password")}}</td>
                                <td>
                                    <input type="password" v-model="user.oldPassword"
                                           pattern="[a-zA-Z0-9_ \\-]{2,26}"
                                           :title="translate('Length: 2 - 26 characters: english letters, numbers, - , _ , space')"/>
                                </td>
                            </tr>
                            <tr v-if="!isEmpty(user.oldPassword)">
                                <td>{{translate("New password")}}</td>
                                <td>
                                    <input type="password" v-model="user.newPassword" required
                                           pattern="[a-zA-Z0-9_ \\-]{2,26}"
                                           :title="translate('Length: 2 - 26 characters: english letters, numbers, - , _ , space')"/>
                                </td>
                            </tr>
                            <tr v-if="!isEmpty(user.newPassword)">
                                <td>{{translate("Repeat new password")}}</td>
                                <td>
                                    <input type="password" v-model="user.repeatedNewPassword" required
                                           pattern="[a-zA-Z0-9_ \\-]{2,26}"
                                           :title="translate('Length: 2 - 26 characters: english letters, numbers, - , _ , space')"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </v-details>
                    <table class="equal-columns-table">
                        <tbody>
                        <tr>
                            <td>{{translate("Nickname")}}</td>
                            <td>
                                <p v-if="!editMode">{{user.name}}</p>
                                <input v-if="editMode" v-model="user.name" type="text" required
                                       pattern="[a-zA-Z0-9_ \\-]{2,26}"
                                       :title="translate('Length: 2 - 26 characters: english letters, numbers, - , _ , space')"/>
                            </td>
                        </tr>
                        <tr>
                            <td>{{translate("Email")}}</td>
                            <td>
                                <p v-if="!editMode">{{user.email}}</p>
                                <input v-if="editMode" id="email" type="email" v-model="user.email" required/>
                            </td>
                        </tr>
                        <tr>
                            <td>{{translate("Role")}}</td>
                            <td>
                                <p v-if="!isRoleSelectRendered()">{{user.role}}</p>
                                <select v-if="isRoleSelectRendered()" v-model="user.role">
                                    <option v-for="role in ['admin', 'editor', 'seller', 'user']" :key="role">
                                        {{role}}
                                    </option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>{{translate("Country")}}</td>
                            <td>
                                <p v-if="!editMode">{{countryName}}</p>
                                <select v-if="editMode" v-model="user.country">
                                    <option v-for="country in countries" :value="country.alpha2Code">
                                        {{translate(country.name)}}
                                    </option>
                                </select>
                            </td>
                        </tr>
                        <tr><td>{{translate("Rating")}}</td><td>{{user.rating}}</td></tr>
                        <tr v-if="editMode">
                            <td/>
                            <td>
                                <DefaultButton id="user-delete"
                                               :text="translate('Delete profile')"
                                               :my-class="'background-red'"
                                               @on-click="openUserDeleteDialog('delete')"/>
                            </td>
                        </tr>
                        <tr v-if="isAdmin()" style="text-align: center">
                            <td colspan="2"><b>{{"Admin options"}}</b></td>
                        </tr>
                        <tr v-if="isAdmin()">
                            <td>{{translate("Status")}}</td>
                            <td>
                                <p v-if="!editMode">{{user.status}}</p>
                                <select v-if="editMode" v-model="user.status">
                                    <option v-for="status in statuses">
                                        {{status}}
                                    </option>
                                </select>
                            </td>
                        </tr>
                        <tr v-if="isAdmin() && editMode">
                            <td/>
                            <td>
                                <DefaultButton id="user-hard-delete"
                                               :text="translate('Hard delete')"
                                               :my-class="'background-red'"
                                               @on-click="openUserDeleteDialog('hard-delete')"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                    <table v-if="!isEmpty(deleteOption)" style="text-align: center">
                        <tbody class="bordered">
                        <tr><td colspan="2">{{translate("Confirm deletion")}}</td></tr>
                        <tr>
                            <td>
                                <DefaultButton id="cancel-user-delete"
                                               :text="translate('Cancel')"
                                               @on-click="cancelUserDelete()"/>
                            </td>
                            <td>
                                <DefaultButton id="confirm-user-delete"
                                               :text="translate('Confirm')"
                                               @on-click="confirmUserDelete()"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        </transition>
    </div>
</template>

<script>
import axios from "axios";
import storeUtil from "../../util/storeUtil";
import routerUtil from "../../util/routerUtil";
import EditableImg from "../EditableImg";
import itemViewUtil from "../../util/itemViewUtil";
import EditPanel from "../menu/EditPanel";
import AlertMessagesSection from "../info/AlertMessagesSection";
import DefaultButton from "../element/button/DefaultButton";
import axiosUtil from "../../util/axiosUtil";
import userUtil from "../../util/userUtil";
import shared from "../../util/shared";
import LoadingScreen from "../special/LoadingScreen";
import basicComponent from "../../mixin/basicComponent";
import view from "../../mixin/view";
import {mapState} from "vuex";

export default {
        name: "User",
        components: {LoadingScreen, DefaultButton, AlertMessagesSection, EditPanel, EditableImg},

        mixins: [basicComponent, view],

        computed: {
            ...mapState({
                countries: state => state.dictionary.countries
            })
        },

        data() {
            return {
                user: "",
                validationMessages: [],
                deleteOption: "",
                countryName: "",
                changePasswordOpened: false,
                statuses: ["active", "blocked", "deleted", "pending"],
                editedUserIsCurrentUser: false
            }
        },

        created() {
            this.onUrlChange();
        },

        watch: {
            '$route': 'onUrlChange'
        },

        methods: {
            onUrlChange() {
                this.getView();
            },

            getView() {
                axios
                    .get(this.basicUrl
                        + "/" + "view"
                        + "/" + "user"
                        + "/" + routerUtil.getId(this.$route), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.user = response.data;
                        this.editedUserIsCurrentUser = this.user.name === this.getUserName();
                        this.getCountryName(this.user.country);
                        let itemView = {
                            img: this.user.img,
                            defaultImg: this.user.defaultImg,
                            messages: [],
                            userData: this.itemView.userData,
                            wishListIds: this.itemView.wishListIds,
                            errorMessage: this.itemView.errorMessage
                        };
                        itemViewUtil.dispatchView(itemView, this.$route.params.lang);
                        console.log("user rendered: name: " + this.user.name);
                        storeUtil.setLoadingStateOff();
                    })
                    .catch(error => {
                        itemViewUtil.dispatchResponseError(error);
                    });
            },

            submit: function (e) {
                e.preventDefault();

                storeUtil.setLoadingStateDefault();
                storeUtil.setEditMode(false);

                let userView = this.user;
                userView.img = this.itemView.img;
                userView.defaultImg = this.itemView.defaultImg;
                userView.currentUserName = this.getUserName();

                // /user/id/update
                axios
                    .put(this.basicUrl
                        + "/" + "user"
                        + "/" + routerUtil.getId(this.$route)
                        + "/" + "update",
                        userView, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                    .then(response => {
                        this.validationMessages = response.data;
                        this.changePasswordOpened = this.validationMessages.length > 0
                            && shared.arrayContainsSubstring("asswo", this.validationMessages);
                        if (this.validationMessages.length === 0) {
                            if (this.editedUserIsCurrentUser) {
                                this.itemView.userData.name = userView.name;
                            }
                            storeUtil.setLoadingStateOff();
                            this.getCountryName(this.user.country);
                            console.log("user data successfully updated");
                        } else {
                            console.log("user data update failed");
                            storeUtil.setEditMode(true);
                        }
                    });
            },

            isSeller() {
                userUtil.isSeller(this.user.role);
            },

            isEditable() {
                if (this.editMode) {
                    return true;
                }
                return this.isAdmin() || this.isCurrentUserProfile();
            },

            isCurrentUserProfile() {
                return this.user.name === this.getUserName();
            },

            isRoleSelectRendered() {
                return this.editMode && this.isAdmin();
            },

            openUserDeleteDialog(deleteOption) {
                this.deleteOption = deleteOption;
            },

            cancelUserDelete() {
                this.deleteOption = "";
            },

            confirmUserDelete() {
                // /user/{id}/delete
                let userId = routerUtil.getId(this.$route);
                axios
                    .delete(this.basicUrl
                        + "/" + "user"
                        + "/" + userId
                        + "/" + this.deleteOption, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                    .then(response => {
                        if (response.status === 200) {
                            this.userDeleteDialogOpened = false;
                            if (this.isCurrentUserProfile()) {
                                axiosUtil.logout(this.$route.params.lang);
                            } else {
                                routerUtil.back();
                            }
                            console.log("user id=" + userId + " successfully deleted");
                        } else {
                            console.log("user id=" + userId + " deletion failed");
                        }
                        storeUtil.setLoadingStateOff();
                    });
            },

            getCountryName(alpha2Code) {
                this.countryName = shared.getCountryName(alpha2Code);
            },

            isLoading() {
                return shared.isLoading(this.loadingState);
            }
        }
    }
</script>

<style scoped>
    table {
        text-align: left;
    }
</style>