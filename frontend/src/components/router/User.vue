<template>
    <div>
        <LoadingScreen v-if="isLoading()"/>
        <div v-else style="text-align: center">
            <p>{{user.name}}</p>
            <EditableImg v-if="isImgRendered()"/>
            <EditPanel v-if="isEditable()" :user-form="true"/>
            <AlertMessagesSection :messages="validationMessages"/>
            <form id="user-form" @submit="submit">
                <v-details v-if="editMode" v-model="changePasswordOpened">
                    <summary class="default-margin">{{"Change password"}}</summary>
                    <table class="equal-columns-table">
                        <tbody>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                {{"To change password input your old password"}}
                            </td>
                        </tr>
                        <tr>
                            <td>{{"Old password"}}</td>
                            <td>
                                <input type="password" v-model="user.oldPassword"
                                       pattern="[a-zA-Z0-9_ \\-]{4,14}"
                                       :title="$t('nameAndPasswordInputLabel')"/>
                            </td>
                        </tr>
                        <tr v-if="!isEmpty(user.oldPassword)">
                            <td>{{"New password"}}</td>
                            <td>
                                <input type="password" v-model="user.newPassword" required
                                       pattern="[a-zA-Z0-9_ \\-]{4,14}"
                                       :title="$t('nameAndPasswordInputLabel')"/>
                            </td>
                        </tr>
                        <tr v-if="!isEmpty(user.newPassword)">
                            <td>{{"Repeat new password"}}</td>
                            <td>
                                <input type="password" v-model="user.repeatedNewPassword" required
                                       pattern="[a-zA-Z0-9_ \\-]{4,14}"
                                       :title="$t('nameAndPasswordInputLabel')"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </v-details>
                <table class="equal-columns-table">
                    <tbody>
                    <tr>
                        <td>{{"Nickname"}}</td>
                        <td>
                            <p v-if="!editMode">{{user.name}}</p>
                            <input v-if="editMode" v-model="user.name" type="text" required
                                   pattern="[a-zA-Z][a-zA-Z0-9 _-]{1,28}[a-zA-Z0-9_-]"
                                   :title="$t('nameAndPasswordInputLabel')"/>
                        </td>
                    </tr>
                    <tr>
                        <td>{{"Email"}}</td>
                        <td>
                            <p v-if="!editMode">{{user.email}}</p>
                            <input v-if="editMode" id="email" type="email" v-model="user.email" required/>
                        </td>
                    </tr>
                    <tr>
                        <td>{{"Role"}}</td>
                        <td>
                            <p v-if="!isRoleSelectRendered()">{{user.role}}</p>
                            <select v-if="isRoleSelectRendered()" v-model="user.role">
                                <option v-for="role in ['admin', 'seller', 'user']" :key="role">
                                    {{role}}
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>{{"Country"}}</td>
                        <td>
                            <p v-if="!editMode">{{countryName}}</p>
                            <select v-if="editMode" v-model="user.country">
                                <option v-for="country in countries" :value="country.alpha2Code">
                                    {{country.name}}
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr><td>{{"Rating"}}</td><td>{{user.rating}}</td></tr>
                    <tr v-if="editMode">
                        <td/>
                        <td>
                            <DefaultButton id="user-delete" :text="'Delete profile'" :color="'red'"
                                           @on-click="openUserDeleteDialog('delete')"/>
                        </td>
                    </tr>
                    <tr v-if="isAdmin()" style="text-align: center">
                        <td colspan="2">{{"Admin options"}}</td>
                    </tr>
                    <tr v-if="isAdmin()">
                        <td>{{"Status"}}</td>
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
                            <DefaultButton id="user-hard-delete" :text="'Hard delete'" :color="'red'"
                                           @on-click="openUserDeleteDialog('hard-delete')"/>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <table v-if="!isEmpty(deleteOption)" style="text-align: center">
                    <tbody class="bordered">
                    <tr><td colspan="2">{{"Confirm deletion"}}</td></tr>
                    <tr>
                        <td>
                            <DefaultButton id="cancel-user-delete" :text="'Cancel'" @on-click="cancelUserDelete()"/>
                        </td>
                        <td>
                            <DefaultButton id="confirm-user-delete" :text="'Confirm'" @on-click="confirmUserDelete()"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import storeUtil from "../../util/storeUtil";
    import routerUtil from "../../util/routerUtil";
    import EditableImg from "../EditableImg";
    import itemViewUtil from "../../util/itemViewUtil";
    import EditPanel from "../menu/EditPanel";
    import AlertMessagesSection from "../AlertMessagesSection";
    import DefaultButton from "../element/button/DefaultButton";
    import axiosUtil from "../../util/axiosUtil";
    import userUtil from "../../util/userUtil";
    import shared from "../../util/shared";
    import LoadingScreen from "../special/LoadingScreen";

    export default {
        name: "User",
        components: {LoadingScreen, DefaultButton, AlertMessagesSection, EditPanel, EditableImg},

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode,
                userName: state => state.dictionary.userName
            })
        },

        data() {
            return {
                user: "",
                validationMessages: [],
                deleteOption: "",
                countries: [],
                countryName: "",
                changePasswordOpened: false,
                statuses: ["active", "blocked", "deleted"],
                editedUserIsCurrentUser: false
            }
        },

        // mounted() {
        //     let script = document.createElement('script');
        //     script.setAttribute('src', 'https://maps.googleapis.com/maps/api/js?key=&libraries=places');
        //     document.head.appendChild(script);
        // },

        created() {
            this.onUrlChange();
            this.getCountries();
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
                        this.editedUserIsCurrentUser = this.user.name === this.userName;
                        this.getCountryName(this.user.country);
                        let itemView = {
                            img: this.user.img,
                            defaultImg: this.user.defaultImg,
                            messages: [],
                            userData: this.itemView.userData,
                            wishListIds: this.itemView.wishListIds,
                            errorMessage: this.itemView.errorMessage
                        };
                        itemViewUtil.dispatchView(itemView);
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
                userView.currentUserName = this.userName;

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
                            && shared.arrayContainsSubstring("assword", this.validationMessages);
                        if (this.validationMessages.length === 0) {
                            if (this.editedUserIsCurrentUser) {
                                storeUtil.setUserName(userView.name, this.itemView);
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

            isImgRendered() {
                return true;
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
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
                return this.user.name === this.userName;
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
                                axiosUtil.logout(this.basicUrl);
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

            getCountries() {
                console.log("get countries list");
                axios
                    .get("https://restcountries.eu/rest/v2/all")
                    .then(response => {
                        this.countries = response.data;
                    })
            },

            getCountryName(alpha2Code) {
                console.log("get country name for: " + alpha2Code);
                if (alpha2Code === null) {
                    this.countryName = "-";
                    return;
                }
                axios
                    .get("https://restcountries.eu/rest/v2/alpha/" + alpha2Code)
                    .then(response => {
                        let country = response.data;
                        console.log("country name: " + country.name);
                        this.countryName = country.name;
                    })
            },

            isLoading() {
                return shared.isLoading(this.loadingState);
            },

            isEmpty(value) {
                return shared.isEmpty(value);
            }
        }
    }
</script>

<style scoped>
    table {
        text-align: left;
    }
</style>