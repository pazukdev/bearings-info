<template>
    <div>
        <p id="title" style="text-align: center;"><b>Bearings</b></p>
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
            <tr v-for="(bearing, index) in bearingzz" :key="index">
                <td>
                    <label class="form-checkbox">
                        <input type="checkbox" :value="bearing.id" v-model="selected" @click="closeForm">
                    </label>
                </td>
                <td>{{bearing.name}}</td>
                <td>{{bearing.type}}</td>
                <td>{{bearing.rollingElementsQuantity}}</td>
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
                                v-on:click="deleteBearings()"
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
                    <b v-if="isEditFormOpened()">Edit bearing</b>
                    <b v-if="isCreateFormOpened()">Create bearing</b>
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
                bearings: [],
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
                bearing: ""
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
            axios
                .get("/backend/bearing/list", {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.bearings = response.data);
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                bearingzz: state => state.dictionary.bearings
            })
        },

        methods: {
            isFormVisible() {
                return this.isEditFormOpened() || this.isCreateFormOpened();
            },

            openEditForm() {
                this.isEdit = true;
                this.id = this.selected[0];
                this.getBearing(this.id);
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

            deleteBearings() {
                axios
                    .delete("/backend/bearing/list", {
                        headers: {
                            Authorization: this.authorization
                        },
                        data: this.selected
                    })
                    .then(() => {
                        this.$emit('reopen-bearings');
                    });
                this.selected = [];
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
                    .then(() => this.emitReopenBearings());
            },

            edit(id) {
                axios
                    .put("/backend/bearing/" + id, this.newBearing(), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(() => this.emitReopenBearings());
            },

            emitReopenBearings() {
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

            select() {
                this.selected = [];
                if (!this.selectAll) {
                    for (let i in this.motorcycles) {
                        this.selected.push(this.motorcycles[i].id);
                    }
                }
            },

            clearForm() {
                this.name = "";
                this.type = "";
                this.rollingElement = "";
                this.rollingElementsQuantity = "";
            }
        }
    }
</script>
<style scoped>
</style>
