<template>
    <div>
<!--        {{"isEditMode: " + isEditMode}}<br>-->
<!--        {{"isAddLine: " + isAddLine}}<br>-->
<!--        {{"isEditParameters: " + isEditParameters}}<br>-->
<!--        {{"isEditValues: " + isEditValues}}<br>-->
<!--        {{text}}-->
<!--        {{newItemView}}-->
<!--        {{itemView}}-->
        <table>
            <tbody>
            <tr>
                <td colspan="2" style="text-align: center"><b>{{itemView.header.name}}</b></td>
            </tr>
            <tr style="text-align: left"
                v-for="row in itemView.header.matrix">
                <td style="width: 50%">
                    <input v-if="isEditMode && isEditParameters && row[0] !== 'Name'" v-model="row[0]" type="text"/>
                    <p v-if="!isEditParameters || (isEditMode && isEditParameters && row[0] === 'Name')">
                        {{row[0]}}
                    </p>
                </td>
                <td>
                    <input v-if="isEditMode && isEditValues" v-model="row[1]" type="text"/>
                    <p v-if="(!isShowInfoButton(row[3]) && !isEditValues) || isEditParameters || isAddLine">
                        {{row[1]}}
                    </p>
                    <button v-if="isShowInfoButton(row[3])" type="button"
                            style="width: 100%"
                            @click="setItem(row[2])">
                        {{row[1]}}
                    </button>
                </td>
                <td>
                    <button v-if="isEditParameters && row[0] !== 'Name'"
                            v-model="newItemView"
                            type="button"
                            @click="newItemView.header.matrix.splice(newItemView.header.matrix.indexOf(row), 1)">
                        {{"-"}}
                    </button>
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
            <tr v-if="isEditMode" style="text-align: left">
                <td style="width: 50%">
                    <button v-if="!isAddLine"
                            type="button"
                            style="width: 100%"
                            @click="addLine()">
                        {{"+"}}
                    </button>
                    <button v-if="isAddLine"
                            type="button"
                            style="width: 100%"
                            @click="cancelAddLine()">
                        {{"-"}}
                    </button>
                </td>
                <td></td>
            </tr>
            <tr v-if="isEditMode && !isAddLine" style="text-align: left">
                <td style="width: 50%">
                    <button type="button"
                            style="width: 100%"
                            @click="editParameters()">
                        {{"Edit parameters"}}
                    </button>
                </td>
                <td>
                    <button type="button"
                            style="width: 100%"
                            @click="editValues()">
                        {{"Edit values"}}
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
                isEditValues: false,
                isEditParameters: false,
                newItemView: "",
                text: "start",
                newParameter: "",
                newValue: "",
                paramsToRemove: []
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
            removeLine(parameter) {

            },

            isShowInfoButton(message) {
                return message === 'show button' && !this.isEditMode;
            },

            editParameters() {
                this.isEditParameters = true;
                this.isEditValues = false;
            },

            editValues() {
                this.isEditParameters = false;
                this.isEditValues = true;
            },

            newLineIsEmpty() {
                return this.newParameter === "" || this.newValue === "";
            },

            addLine() {
                this.isAddLine = true;
                this.isEditParameters = false;
                this.isEditValues = false;
            },

            cancelAddLine() {
                this.newParameter = "";
                this.newValue = "";
                this.isAddLine = false;
                this.isEditParameters = false;
                this.isEditValues = true;
            },

            setItem(id) {
                this.$store.dispatch("addItemId", id);
                this.$emit('select-item', id);
            },

            edit() {
                this.isEditMode = true;
                this.editValues();
                this.newItemView = this.itemView;
            },

            cancel() {
                this.newParameter = "";
                this.newValue = "";
                let id = this.itemId;
                this.$emit('cancel', id);
                this.isEditMode = false;
                this.isAddLine = false;
                this.isEditParameters = false;
                this.isEditValues = false;
            },

            save() {
                if (this.isAddLine && !this.newLineIsEmpty()) {
                    let newLine = [this.newParameter, this.newValue];
                    this.newItemView.header.matrix.push(newLine);
                }
                let id = this.newItemView.itemId;
                this.update(id);
                this.newParameter = "";
                this.newValue = "";
                this.isEditMode = false;
                this.isAddLine = false;
                this.isEditParameters = false;
                this.isEditValues = false;
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