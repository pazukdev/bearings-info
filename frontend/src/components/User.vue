<template>
    <div style="text-align: center">
<!--        {{itemView.userData}}<br>-->
        <p>{{"User"}}</p>
        <EditableImg/>
        <EditPanel v-if="isEditable()" :save-is-submit="true"/>
        <AlertMessagesSection :messages="validationMessages"/>
        <form id="form" @submit="submit">
            <table class="equal-columns-table">
                <tbody>

                <tr v-if="!editMode"><td>{{"Name"}}</td><td>{{user.name}}</td></tr>
                <tr v-if="editMode">
                    <td colspan="2">
                        <label>{{"Nickname"}}
                            <input v-model="user.name" type="text"
                                   pattern=".{3,20}" required title="3-20 characters length"/>
                        </label>
                    </td>
                </tr>

                <tr v-if="!editMode"><td>{{"Email"}}</td><td>{{user.email}}</td></tr>
                <tr v-if="editMode">
                    <td colspan="2">
                        <label>{{"Email"}}
                            <input id="email" type="email" v-model="user.email" required/>
                        </label>
                    </td>
                </tr>

                <tr><td>{{"Role"}}</td><td>{{user.role}}</td></tr>
                <tr><td>{{"Rating"}}</td><td>{{user.rating}}</td></tr>
                </tbody>
            </table>
        </form>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import storeUtil from "../util/storeUtil";
    import routerUtil from "../util/routerUtil";
    import EditableImg from "./EditableImg";
    import itemViewUtil from "../util/itemViewUtil";
    import EditPanel from "./menu/EditPanel";
    import AlertMessagesSection from "./AlertMessagesSection";

    export default {
        name: "User",
        components: {AlertMessagesSection, EditPanel, EditableImg},
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
                validationMessages: []
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
                        let itemView = {
                            imgData: this.user.imgData,
                            messages: [],
                            userData: this.itemView.userData,
                            wishListIds: this.itemView.wishListIds
                        };
                        itemViewUtil.dispatchView(this.$store, itemView);
                        console.log("user rendered: name: " + this.user.name);
                        storeUtil.setLoadingState(false);
                    });
            },

            submit: function (e) {
                e.preventDefault();

                storeUtil.setLoadingState(true);
                storeUtil.setEditMode(false);

                let userView = this.user;
                userView.imgData = this.itemView.imgData;
                userView.messages = this.itemView.messages;

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
                        if (this.validationMessages.length === 0) {
                            storeUtil.setUserName(userView.name, this.itemView);
                            storeUtil.setLoadingState(false);
                            console.log("user data successfully updated");
                        } else {
                            // this.getView();
                            console.log("user data update failed");
                            storeUtil.setEditMode(true);
                        }
                    });
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            },

            isEditable() {
                if (this.editMode) {
                    return true;
                }
                return this.isAdmin() || this.user.name === this.userName;
            }
        }
    }
</script>

<style scoped>
    table {
        text-align: left;
    }
</style>