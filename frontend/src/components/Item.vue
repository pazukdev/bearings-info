<template>
    <div>
<!--        {{"isEditMode: " + isEditMode}}<br>-->
<!--        {{"isAddLine: " + isAddLine}}<br>-->
<!--        {{text}}-->
<!--        {{itemView.header.matrix[0][1]}}<br><br>-->
<!--        {{itemView.replacersTable.replacers}}<br><br>-->
<!--        {{itemView.replacers}}<br><br>-->
<!--        {{this.items}}-->
<!--        {{newReplacer}}<br><br>-->
<!--        {{newReplacerItem}}-->
        <table>
            <tbody>
            <tr style="text-align: center">
                <td style="width: 120px"></td>
                <td>
                    <b>{{itemView.header.name}}</b>
                </td>
                <td style="width: 120px">
                    <button style="width: 100%" type="button"
                            @click="stubMethod()">
                        {{"Google search"}}
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
        <table style="width: 440px">
            <tbody>
            <tr style="text-align: left"
                v-for="row in itemView.header.matrix">
                <td>
                    <p>
                        {{row[0]}}
                    </p>
                </td>
                <td>
                    <input v-if="isEditMode" v-model="row[1]" type="text"/>
                    <p v-if="!isShowInfoButton(row[3]) && !isEditMode">
                        {{row[1]}}
                    </p>
                    <button v-if="isShowInfoButton(row[3])" type="button"
                            style="width: 100%"
                            @click="setItem(row[2])">
                        {{row[1]}}
                    </button>
                </td>
                <td>
                    <button v-if="isEditMode && row[0] !== 'Name'"
                            v-model="newItemView"
                            type="button"
                            class="round-button"
                            @click="newItemView.header.matrix.splice(newItemView.header.matrix.indexOf(row), 1)">
                        {{"-"}}
                    </button>
                </td>
            </tr>
            <tr style="text-align: left"
                v-if="isEditMode && isAddLine">
                <td>
                    <input v-model="newParameter" type="text"/>
                </td>
                <td>
                    <input v-model="newValue" type="text"/>
                </td>
                <td>
                    <button v-if="isAddLine"
                            type="button"
                            class="round-button"
                            @click="cancelAddLine()">
                        {{"-"}}
                    </button>
                </td>
            </tr>
            <tr v-if="isEditMode" style="text-align: left">
                <td>
                    <button v-if="!isAddLine"
                            type="button"
                            style="width: 100%"
                            @click="addLine()">
                        {{"+"}}
                    </button>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr style="text-align: left">
                <td>
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
                <td></td>
            </tr>
            <tr v-for="item in itemView.items.tables">
                <td colspan="3">
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
                <td style="text-align: center" colspan="3">
                    {{itemView.replacersTable.name}}
                </td>
            </tr>
            <tr v-if="notStub()"
                style="text-align: left; width: 440px"
                v-for="replacer in itemView.replacersTable.replacers">
                <td v-if="!isEditMode">
                    {{replacer.comment}}
                </td>
                <input v-if="isEditMode" v-model="replacer.comment" type="text"/>
                <td>
                    <button type="button"
                            style="width: 100%"
                            @click="setItem(replacer.itemId)">
                        {{replacer.buttonText}}
                    </button>
                </td>
                <td v-if="isEditMode">
                    <button v-model="newItemView"
                            type="button"
                            class="round-button"
                            @click="newItemView.replacersTable.replacers
                            .splice(newItemView.replacersTable.replacers.indexOf(replacer), 1)">
                        {{"-"}}
                    </button>
                </td>
            </tr>
            <tr style="text-align: center; color: red">
                <td colspan="3">
                    {{alertMessage}}
                </td>
            </tr>
            <tr v-if="notStub() && isEditMode" style="text-align: left">
                <td>
                    <input v-model="newReplacer.comment" type="text"/>
                </td>
                <td>
                    <select class="content" v-model="newReplacer" @change="onChange()">
                        <option v-for="replacer in itemView.replacers" v-bind:value="replacer">
                            {{replacer.selectText}}
                        </option>
                    </select>
                </td>
                <td>
                    <button type="button"
                            class="round-button"
                            style="width: 100%"
                            @click="addReplacer()">
                        {{"+"}}
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
                newValue: "",
                paramsToRemove: [],
                newReplacerComment: "",
                newReplacerName: "",
                newReplacerId: "",
                newReplacerItem: "",
                newComment: "",
                alertMessage: "",
                newReplacer: {
                    id: "",
                    name: "",
                    itemId: "",
                    itemName: "",
                    buttonText: "",
                    selectText: "",
                    comment: ""
                }
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
            addReplacer() {
                this.newReplacer.name = this.itemView.header.matrix[0][1] + this.newReplacer.name;
                if (this.replacerAlreadyInList(this.newReplacer.itemId)) {
                    this.alertMessage = "Replacer already in list";
                } else {
                    this.newItemView.replacersTable.replacers.push(this.createReplacer());
                    this.newReplacer = "";
                    this.alertMessage = "";
                }
            },

            replacerAlreadyInList(id) {
                for(let i=0; i < this.newItemView.replacersTable.replacers.length; i++){
                    if (this.newItemView.replacersTable.replacers[i].itemId === id) {
                        return true
                    }
                }
                return false
            },

            onChange() {
                this.alertMessage = "";
            },

            createReplacer() {
                return {
                    id: this.newReplacer.id,
                    name: this.newReplacer.name,
                    itemId: this.newReplacer.itemId,
                    itemName: this.newReplacer.itemName,
                    buttonText: this.newReplacer.buttonText,
                    selectText: this.newReplacer.selectText,
                    comment: this.newReplacer.comment
                };
            },

            stubMethod() {

            },

            isShowInfoButton(message) {
                return message === 'show button' && !this.isEditMode;
            },

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
                this.newReplacer = "";
                this.alertMessage = "";
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
                this.alertMessage = "";
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
                return this.itemView.replacersTable.replacers.length > 0 || this.isEditMode;
            }
        }
    }
</script>