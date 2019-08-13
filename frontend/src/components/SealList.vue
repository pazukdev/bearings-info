<template>
    <div>
        <p id="title" style="text-align: center;"><b>Seals</b></p>
        <table id="productsTable" class="get-all-table">
            <thead>
            <tr>
                <th style="width: 50px"></th>
                <th scope="col">Name</th>
                <th scope="col">Rotation</th>
                <th scope="col">Material</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(seal, index) in seals" :key="index">
                <td>
                    <label class="form-checkbox">
                        <input type="checkbox" :value="seal.id" v-model="selected" @click="closeForm()">
                    </label>
                </td>
                <td>{{replaceEmptyWithDash(seal.name)}}</td>
                <td>{{replaceEmptyWithDash(seal.rotation)}}</td>
                <td>{{replaceEmptyWithDash(seal.rotation)}}</td>
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
                    Rotation
                </td>
                <td class="right">
                    <select class="content" v-model="rotation">
                        <option v-for="rotation in rotations">
                            {{rotation}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    Material
                </td>
                <td class="right">
                    <select class="content" v-model="material">
                        <option v-for="material in materials">
                            {{material}}
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
                rotation: "",
                material: "",
                rotations: [],
                materials: [],
                selected: [],
                selectAll: false,
                isEdit: false,
                isCreate: false,
                seal: ""
            }
        },

        created() {
            axios
                .get(`/backend/seal/rotations`, {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.rotations = response.data);
            axios
                .get(`/backend/seal/materials`, {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.materials = response.data);
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                seals: state => state.dictionary.seals
            })
        },

        methods: {
            isFormVisible() {
                return this.isEditFormOpened() || this.isCreateFormOpened();
            },

            openEditForm() {
                this.isEdit = true;
                this.id = this.selected[0];
                this.getSeal(this.id);
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

            getSeal(id) {
                axios
                    .get("/backend/seal/" + id, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let seal = response.data;
                        this.name = seal.name;
                        this.rotation = seal.rotation;
                        this.material = seal.material;
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
                    .post(`/backend/seal/create`, this.newSeal(), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(() => this.emitReopen());
            },

            edit(id) {
                axios
                    .put("/backend/seal/" + id, this.newSeal(), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(() => this.emitReopen());
            },

            deleteSelected() {
                axios
                    .delete("/backend/seal/list", {
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
                this.$emit('reopen-seals');
            },

            newSeal() {
                return {
                    name: this.name,
                    rotation: this.rotation,
                    material: this.material
                };
            },

            select() {
                this.selected = [];
                if (!this.selectAll) {
                    for (let i in this.seals) {
                        this.selected.push(this.seals[i].id);
                    }
                }
            },

            clearForm() {
                this.id = "";
                this.name = "";
                this.rotation = "";
                this.material = "";
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