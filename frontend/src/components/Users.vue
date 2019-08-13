<template>
    <div>
        <p id="title" style="text-align: center;"><b>Users</b></p>
        <table id="productsTable" class="get-all-table">
            <thead>
            <tr>
                <th style="width: 50px"></th>
                <th scope="col">Name</th>
                <th scope="col">Role</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(user, index) in users" :key="index">
                <td>
                    <label class="form-checkbox" style="text-align: center">
                        <input type="checkbox"
                               v-if="user.name !== userName"
                               :value="user.id"
                               v-model="selected"
                               @click="closeForm()">
                    </label>
                    <p v-if="user.name === userName">You</p>
                </td>
                <td>{{replaceEmptyWithDash(user.name)}}</td>
                <td>{{replaceEmptyWithDash(user.role)}}</td>
            </tr>
            </tbody>
        </table>
        <hr>
        <div style="text-align: right">
            <table>
                <tbody>
                <tr>
                    <td>
                        <button type="button"
                                class="default-width-2"
                                v-on:click="openCreateForm()"
                                :disabled="!isAddButtonVisible()">
                            Add
                        </button>
                    </td>
                    <td>
                        <button type="button"
                                class="default-width-2"
                                v-on:click="openEditForm()"
                                :disabled="!isEditButtonVisible()">
                            Edit
                        </button>
                    </td>
                    <td>
                        <button type="button"
                                class="default-width-2"
                                v-on:click="deleteSelected()"
                                :disabled="!isDeleteButtonVisible()">
                            Delete
                        </button>
                    </td>
                    <td>
                        <button type="button"
                                class="default-width-2"
                                v-on:click="closeForm()"
                                :disabled="!isFormVisible()">
                            Cancel
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <table class="creation-form" v-if="isFormVisible()">
            <tbody>
            <tr style="text-align: center">
                <td colspan="2">
                    <b v-if="isEditFormOpened()">Edit</b>
                    <b v-if="isCreateFormOpened()">Create</b>
                </td>
            </tr>
            <tr>
                <td>
                    Name
                </td>
                <td class="right">
                    <input class="content" v-model="name" type="text">
                </td>
            </tr>
            <tr>
                <td>
                    Password
                </td>
                <td class="right">
                    <input class="content" v-model="password" type="text">
                </td>
            </tr>
            <tr>
                <td>
                    Role (USER by default)
                </td>
                <td class="right">
                    <select class="content" v-model="role">
                        <option v-for="role in roles">
                            {{role}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="right">
                    <button class="content" type="button" v-on:click="save()">Save</button>
                </td>
            </tr>
            <tr v-for="message in validationMessages" class="warning-message">
                <td colspan="2">
                    {{message}}
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';

    export default {

        data() {
            return {
                id: "",
                name: "",
                role: "",
                password: "",
                roles: [],
                validationMessages: [],
                selected: [],
                selectAll: false,
                isEdit: false,
                isCreate: false
            }
        },

        created() {
            axios
                .get(`/backend/admin/user/roles`, {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.roles = response.data);
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                users: state => state.dictionary.users,
                userName: state => state.dictionary.userName,
            })
        },

        methods: {
            isFormVisible() {
                return this.isEditFormOpened() || this.isCreateFormOpened();
            },

            openEditForm() {
                this.isEdit = true;
                this.id = this.selected[0];
                this.getUser(this.id);
            },

            openCreateForm() {
                this.isCreate = true;
            },

            closeForm() {
                this.isEdit = false;
                this.isCreate = false;
                this.clearForm();
            },

            isEditFormOpened() {
                return this.isEdit && this.selected.length === 1;
            },

            isCreateFormOpened() {
                return this.isCreate && this.selected.length === 0;
            },

            isEditButtonVisible() {
                return this.selected.length === 1 && !this.isEditFormOpened();
            },

            isAddButtonVisible() {
                return this.selected.length === 0 && !this.isCreateFormOpened();
            },

            isDeleteButtonVisible() {
                return this.selected.length > 0 && !this.isFormVisible();
            },

            getUser(id) {
                axios
                    .get("/backend/admin/user/" + id, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let user = response.data;
                        this.name = user.name;
                        this.password = user.password;
                        this.role = user.role;
                    });
            },

            save() {
                if (this.isEditFormOpened()) {
                    this.edit(this.id);
                } else {
                    this.create();
                }
            },

            closeIfValid(validationMessage) {
                this.validationMessages = validationMessage;
                if (this.validationMessages.length === 0) {
                    this.closeForm();
                }
            },

            create() {
                axios
                    .post(`/backend/user/create`, this.newUser(), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.closeIfValid(response.data);
                        this.emitReopen();
                    });
            },

            edit(id) {
                axios
                    .put("/backend/admin/user/" + id, this.newUser(), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.closeIfValid(response.data);
                        this.emitReopen();
                    });
            },

            deleteSelected() {
                axios
                    .delete("/backend/admin/user/list", {
                        headers: {
                            Authorization: this.authorization
                        },
                        data: this.selected
                    })
                    .then(() => {
                        this.emitReopen();
                        this.selected = [];
                    });
            },

            emitReopen() {
                this.$emit('reopen-users');
            },

            newUser() {
                return {
                    name: this.name,
                    password: this.password,
                    repeatedPassword: this.password,
                    role: this.role
                };
            },

            select() {
                this.selected = [];
                if (!this.selectAll) {
                    for (let i in this.users) {
                        this.selected.push(this.users[i].id);
                    }
                }
            },

            clearForm() {
                this.id = "";
                this.name = "";
                this.password = "";
                this.role = "";
                this.validationMessages = "";
            },

            replaceEmptyWithDash(string) {
                if (string === null || string === "") {
                    return "-";
                }
                return string;
            }
        }
    }
</script>

<style scoped>
    .warning-message {
        text-align: center;
        color: red;
    }
</style>