<template>
    <div>
        <table>
            <tr v-if="!isAddToWishListFormOpened()">
                <td style="width: 290px"></td>
                <td style="text-align: right">
                    <button type="button"
                            v-on:click="openAddToWishListForm()"
                            :disabled="!isAddToWishListButtonEnabled()">
                        Add to wishlist
                    </button>
                </td>
            </tr>
            <tr v-if="isAddToWishListFormOpened()">
                <td style="width: 290px; text-align: center"></td>
                <td>
                    <input style="width: 40px" v-model="quantity"/>
                </td>
                <td>
                    <button type="button"
                            class="default-width-2"
                            v-on:click="addToWishList()"
                            :disabled="!(quantity > 0)">
                        Add
                    </button>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <p id="title" style="text-align: center;"><b>Bearings</b></p>
                </td>
            </tr>
        </table>
        <table id="productsTable" class="get-all-table">
            <thead>
            <tr>
                <th style="width: 50px"></th>
                <th scope="col">Name</th>
                <th scope="col">Type</th>
                <th scope="col">Rolling<br>elements, pcs</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(bearing, index) in bearings" :key="index">
                <td>
                    <label class="form-checkbox">
                        <input type="checkbox" :value="bearing.id" v-model="selected" @click="closeForm()">
                    </label>
                </td>
                <td>{{replaceEmptyWithDash(bearing.name)}}</td>
                <td>{{replaceEmptyWithDash(bearing.type)}}</td>
                <td>{{replaceEmptyWithDash(bearing.rollingElementsQuantity)}}</td>
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
        <table class="creation-form" v-if="isFormVisible() && !isAddToWishListFormOpened()">
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
                    Type
                </td>
                <td class="right">
                    <select class="content" v-model="type">
                        <option v-for="type in types">
                            {{type}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    Rolling element
                </td>
                <td class="right">
                    <select class="content" v-model="rollingElement">
                        <option v-for="rollingElement in rollingElements">
                            {{rollingElement}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    Rolling elements quantity
                </td>
                <td class="right">
                    <input v-model="rollingElementsQuantity" type="text"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="right">
                    <button class="content" type="button" v-on:click="save()">Save</button>
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
                type: "",
                rollingElement: "",
                rollingElementsQuantity: "",
                types: [],
                rollingElements: [],
                selected: [],
                selectAll: false,
                isEdit: false,
                isCreate: false,
                isAddToWishList: false,
                bearing: "",
                quantity: 1
            }
        },

        created() {
            axios
                .get("/backend/bearing/types", {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.types = response.data);
            axios
                .get("/backend/bearing/rolling-elements", {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.rollingElements = response.data);
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                bearings: state => state.dictionary.bearings,
                userName: state => state.dictionary.userName,
                wishList: state => state.dictionary.wishList
            })
        },

        methods: {
            isFormVisible() {
                return this.isEditFormOpened() || this.isCreateFormOpened() || this.isAddToWishListFormOpened();
            },

            openEditForm() {
                this.isEdit = true;
                this.id = this.selected[0];
                this.getBearing(this.id);
            },

            openCreateForm() {
                this.isCreate = true;
            },

            openAddToWishListForm() {
                this.isAddToWishList = true;
                this.id = this.selected[0];
                this.getBearing(this.id);
            },

            closeForm() {
                this.isEdit = false;
                this.isCreate = false;
                this.isAddToWishList = false;
                this.clearForm();
            },

            isAddToWishListFormOpened() {
                return this.isAddToWishList && this.selected.length === 1;

            },

            isEditFormOpened() {
                return this.isEdit && this.selected.length === 1;
            },

            isCreateFormOpened() {
                return this.isCreate && this.selected.length === 0;
            },

            isEditButtonVisible() {
                return this.selected.length === 1 && !this.isEditFormOpened() && !this.isAddToWishListFormOpened();
            },

            isAddButtonVisible() {
                return this.selected.length === 0 && !this.isCreateFormOpened() && !this.isEditFormOpened();
            },

            isDeleteButtonVisible() {
                return this.selected.length > 0 && !this.isFormVisible();
            },

            isAddToWishListButtonEnabled() {
                return this.selected.length === 1 && !this.isEditFormOpened() && !this.isCreateFormOpened();
            },

            getBearing(id) {
                axios
                    .get("/backend/bearing/" + id, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let bearing = response.data;
                        this.name = bearing.name;
                        this.type = bearing.type;
                        this.rollingElement = bearing.rollingElement;
                        this.rollingElementsQuantity = bearing.rollingElementsQuantity;
                    });
            },

            save() {
                if (this.isEditFormOpened()) {
                    this.edit(this.id);
                } else {
                    this.create();
                }
                this.closeForm();
            },

            create() {
                axios
                    .post(`/backend/bearing/create`, this.newBearing(), {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                    .then(() => this.emitReopen());
            },

            edit(id) {
                axios
                    .put("/backend/bearing/" + id, this.newBearing(), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(() => this.emitReopen());
            },

            deleteSelected() {
                axios
                    .delete("/backend/bearing/list", {
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
                this.$emit('reopen-bearings');
            },

            newBearing() {
                return {
                    name: this.name,
                    type: this.type,
                    rollingElement: this.rollingElement,
                    rollingElementsQuantity: this.rollingElementsQuantity
                };
            },

            addToWishList() {
                let newItem = {
                    type: "bearing",
                    name: this.name,
                    quantity: this.quantity
                };

                axios
                    .put("/backend/" + this.userName + "/add-item", newItem, {
                        headers: {
                            Authorization: this.authorization
                        },
                    })
                    .then(() => {
                        this.emitReopen();
                        this.clearForm();
                        this.selected = [];
                    })
            },

            select() {
                this.selected = [];
                if (!this.selectAll) {
                    for (let i in this.bearings) {
                        this.selected.push(this.bearings[i].id);
                    }
                }
            },

            clearForm() {
                this.id = "";
                this.name = "";
                this.type = "";
                this.rollingElement = "";
                this.rollingElementsQuantity = "";
                this.quantity = 1;
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
</style>
