<template>
    <div>
<!--        {{text}}-->
<!--        {{isEditMode}}-->
<!--        {{newItemView}}-->
<!--        {{itemView}}-->
        <table>
            <tbody>
            <tr>
                <td colspan="2" style="text-align: center"><b>{{itemView.header.name}}</b></td>
            </tr>
            <tr style="text-align: left"
                v-if="!isEditMode"
                v-for="row in itemView.header.matrix">
                <td style="width: 50%">
                    {{row[0]}}
                </td>
                <td>
                    {{row[1]}}
                </td>
            </tr>
            <tr style="text-align: left"
                v-if="isEditMode"
                v-for="row in itemView.header.matrix">
                <td style="width: 50%">
                    {{row[0]}}
                </td>
                <td>
                    <input v-model="row[1]" type="text"/>
                </td>
            </tr>
            <tr style="text-align: left"
                v-if="isEditMode && isAddLine">
                <td style="width: 50%">
                    <input v-model="newParameter" type="text"/>
                </td>
                <td>
                    <input v-model="newValue" type="text"/>
                </td>
            </tr>
            <tr v-if="itemView.selectableData.name !== 'stub'"
                style="text-align: left"
                v-for="row in itemView.selectableData.matrix">
                <td style="width: 50%">
                    {{row[0]}}
                </td>
                <td>
                    <p v-if="row[2] === 'no id'">{{row[1]}}</p>
                    <button v-if="row[2] !== 'no id'" type="button"
                            style="width: 100%"
                            @click="setItem(row[2])">
                        {{row[1]}}
                    </button>
                </td>
            </tr>
            <tr style="text-align: left">
                <td style="width: 50%">
                </td>
                <td>
                    <button v-if="isEditMode && !isAddLine"
                            type="button"
                            style="width: 100%"
                            @click="addLine()">
                        {{"+"}}
                    </button>
                    <button v-if="isEditMode && isAddLine"
                            type="button"
                            style="width: 100%"
                            @click="cancelAddLine()">
                        {{"-"}}
                    </button>
                </td>
            </tr>
            <tr style="text-align: left">
                <td style="width: 50%">
                    <button v-if="isEditMode"
                            type="button"
                            style="width: 100%"
                            @click="cancel()">
                        {{"Cancel"}}
                    </button>
                </td>
                <td>
                    <button v-if="!isEditMode"
                            type="button"
                            style="width: 100%"
                            @click="edit()">
                        {{"Edit"}}
                    </button>
                    <button v-if="isEditMode"
                            type="button"
                            style="width: 100%"
                            @click="save()">
                        {{"Save"}}
                    </button>
                </td>
            </tr>
            <tr v-for="item in itemView.items.tables">
                <td colspan="2">
                    <table class="get-all-table">
                        <tbody>
                        <tr>
                            <td style="width: 120px">
                                <b>{{item.name}}</b>
                            </td>
                            <td></td>
                            <td style="width: 80px"></td>
                        </tr>
                        <tr v-for="row in item.matrix">
                            <td>
                                {{row[0]}}
                            </td>
                            <td>
                                <button type="button"
                                        style="width: 80%"
                                        @click="setItem(row[3])">
                                    {{row[1]}}
                                </button>
                            </td>
                            <td>
                                {{row[2]}}
                            </td>
                        </tr>
                        <tr>
                            <p></p>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr v-if="notStub()">
                <td style="text-align: center" colspan="2">
                    {{itemView.replacers.name}}
                </td>
            </tr>
            <tr v-if="notStub()" style="text-align: left" v-for="row in itemView.replacers.matrix">
                <td>
                    {{row[0]}}
                </td>
                <td>
                    <button type="button"
                            style="width: 100%"
                            @click="setItem(row[2])">
                        {{row[1]}}
                    </button>
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
                isEditMode: false,
                isAddLine: false,
                newItemView: "",
                text: "start",
                newParameter: "",
                newValue: ""
            }
        },

        created() {
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                itemView: state => state.dictionary.itemViews[state.dictionary.itemViews.length - 1],
                itemId: state => state.dictionary.itemIds[state.dictionary.itemIds.length - 1]
            })
        },

        methods: {
            newLineIsEmpty() {
                return this.newParameter === "" || this.newValue === "";
            },

            addLine() {
                this.isAddLine = true;
            },

            cancelAddLine() {
                this.newParameter = "";
                this.newValue = "";
                this.isAddLine = false;
            },

            setItem(id) {
                this.$store.dispatch("addItemId", id);
                this.$emit('select-item', id);
            },

            edit() {
                this.isEditMode = true;
                this.newItemView = this.itemView;
            },

            cancel() {
                this.newParameter = "";
                this.newValue = "";
                let id = this.itemId;
                this.$emit('cancel', id);
                this.isEditMode = false;
                this.isAddLine = false;
            },

            save() {
                if (this.isAddLine && !this.newLineIsEmpty()) {
                    let newLine = [this.newParameter, this.newValue];
                    this.newItemView.header.matrix.push(newLine);
                }
                let id = this.newItemView.item.id;
                this.update(id);
                this.newParameter = "";
                this.newValue = "";
                this.isEditMode = false;
                this.isAddLine = false;
            },

            update(id) {
                axios
                    .put("/backend/item/" + id, this.newItemView, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(() => {
                        this.$store.dispatch("removeLastItemView");
                        this.$store.dispatch("removeLastItemId");
                        this.$store.dispatch("removeLastComponent");
                        this.setItem(id);
                    });
            },

            notStub() {
                return this.itemView.replacers.name !== "stub";
            }
        }
    }
</script>