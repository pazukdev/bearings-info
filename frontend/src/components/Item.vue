<template>
    <div>
        {{itemView.itemId}}<br><br>
<!--        {{itemView.viewType}}<br><br>-->
<!--        {{userName}}<br><br>-->
<!--        {{"isEditMode: " + isEditMode}}<br>-->
<!--        {{newItemCategory}}<br><br>-->
<!--        {{itemView.wishListIds}}<br><br>-->
<!--        {{itemView.categories}}<br><br>-->
<!--        {{itemView.idsToRemove}}<br><br>-->
<!--        {{itemView.header.matrix[0][1]}}<br><br>-->
<!--        {{itemView.items.tables}}<br><br>-->
<!--        {{itemView.replacersTable}}<br><br>-->
<!--        {{itemView.partsTable}}<br><br>-->
<!--        {{itemView.replacersTable}}<br><br>-->
<!--        {{itemView.replacers}}<br><br>-->
<!--        {{newItemView.partsTable}}<br><br>-->
<!--        {{newPart}}<br><br>-->
<!--        {{this.items}}-->
<!--        {{newReplacer}}<br><br>-->
<!--        {{newHeaderRow}}<br><br>-->
<!--        {{newChildItem}}<br><br>-->
<!--        {{newItemCategory}}<br><br>-->
<!--        {{newItemName}}<br><br>-->
<!--        {{newItemNameMessage}}<br><br>-->
        <table style="border-spacing: 0px; text-align: right">
            <tbody>
            <tr>
                <td style="text-align: left">
                    <button type="button" v-on:click="openWishList()">
                        {{"Wishlist: " + itemView.wishListIds.length + " items"}}
                    </button>
                </td>
                <td>
                    {{userName}}
                </td>
            </tr>
            <tr v-if="admin">
                <td></td>
                <td>
                    {{"You are admin"}}
                </td>
            </tr>
            </tbody>
        </table>
        <table>
            <tbody>
            <tr style="text-align: center">
                <td style="width: 120px"></td>
                <td>
                    <b>{{itemView.header.name}}</b>
                </td>
                <td style="width: 120px">
                    <button v-if="itemView.searchEnabled"
                            style="width: 100%"
                            type="button"
                            @click="stubMethod()">
                        {{"Google search"}}
                    </button>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <table style="text-align: center" v-if="isItemsManagementView()">
                        <tbody>
                        <tr>
                            <td colspan="3"><hr></td>
                        </tr>
                        <tr>
                            <td colspan="3">{{"Create new item"}}</td>
                        </tr>
                        <tr style="color: red">
                            <td colspan="3">{{categoryMessage}}</td>
                        </tr>
                        <tr style="color: red">
                            <td colspan="3">{{newItemNameMessage}}</td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text"
                                       list="categories"
                                       class="content"
                                       @change="categorySelectOnChange()"
                                       v-model="newItemCategory"/>
                                <datalist id="categories">
                                    <option v-for="category in itemView.categories"
                                            v-bind:value="category">
                                        {{category}}
                                    </option>
                                </datalist>
                            </td>
                            <td>
                                <input @change="newItemNameMessage = ''" v-model="newItemName" type="text"/>
                            </td>
                            <td>
                                <button class="content"
                                        type="button"
                                        v-on:click="create()">
                                    {{"Create"}}
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2"><hr></td>
                        </tr>
                        </tbody>
                    </table>
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
                    <input v-if="isEditMode && !isItemsManagementView()" v-model="row[1]" type="text"/>
                    <p v-if="!isShowInfoButton(row[3])
                    && (!isEditMode || (isEditMode && isItemsManagementView()))">
                        {{row[1]}}
                    </p>
                    <button v-if="isShowInfoButton(row[3])" type="button"
                            style="width: 100%"
                            @click="setItem(row[2])">
                        {{row[1]}}
                    </button>
                </td>
                <td>
                    <button v-if="isEditMode && !isItemsManagementView() && row[0] !== 'Name'"
                            v-model="newItemView"
                            type="button"
                            class="round-button"
                            @click="removeRowFromHeader(row)">
                        {{"-"}}
                    </button>
                </td>
            </tr>
            <tr style="text-align: center; color: red">
                <td colspan="3">
                    {{newHeaderRowMessage}}
                </td>
            </tr>
            <tr style="text-align: left" v-if="isEditMode && !isItemsManagementView()">
                <td>
                    <input v-model="newHeaderRow.parameter" type="text"/>
                </td>
                <td>
                    <input v-model="newHeaderRow.value" type="text"/>
                </td>
                <td>
                    <button type="button"
                            class="round-button"
                            @click="addHeaderRow()">
                        {{"+"}}
                    </button>
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    <button v-if="isEditMode"
                            type="button"
                            style="width: 194px"
                            @click="cancel()">
                        {{"Cancel"}}
                    </button>
                </td>
                <td style="text-align: right">
                    <button v-if="!isEditMode"
                            type="button"
                            style="width: 194px"
                            @click="edit()">
                        {{"Edit"}}
                    </button>
                    <button v-if="isEditMode"
                            type="button"
                            style="width: 194px"
                            @click="save()">
                        {{"Save"}}
                    </button>
                </td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td style="text-align: right">
                    <button v-if="!isInWishList(itemView.itemId) && isOrdinaryItemView() && !isEditMode"
                            type="button"
                            style="width: 194px"
                            @click="addThisItemToWishList()">
                        {{"Add to Wish List"}}
                    </button>
                    <p v-if="isInWishList(itemView.itemId) && isOrdinaryItemView()">
                        {{"Item in Wish List"}}
                    </p>
                </td>
                <td></td>
            </tr>
            <tr style="height: 26px"><td colspan="3"></td></tr>
            <tr v-if="isPartsTitleVisible()">
                <td style="text-align: center" colspan="3">
                    {{itemView.partsTable.name}}
                </td>
            </tr>
            <tr v-for="item in itemView.items.tables"
                v-if="isMotorcycleCatalogueView()">
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
                                        style="width: 100%"
                                        @click="setItem(row[3])">
                                    {{row[1]}}
                                </button>
                            </td>
                            <td>
                                {{row[2]}}
                            </td>
                            <td v-if="isEditMode">
                                <button v-model="newItemView"
                                        type="button"
                                        class="round-button"
                                        @click="removePartFromList(row)">
                                    {{"-"}}
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <p></p>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr v-if="!isMotorcycleCatalogueView()" v-for="table in itemView.partsTable.tables">
                <td v-if="table.parts.length > 0" colspan="3">
                    <table class="get-all-table">
                        <tbody>
                        <tr v-if="arrayHaveActiveItems(table.parts)">
                            <td style="width: 120px">
                                <b>{{table.name}}</b>
                            </td>
                            <td></td>
                            <td style="width: 80px"></td>
                        </tr>
                        <tr v-for="part in table.parts" v-if="statusActive(part)">
                            <td style="width: 120px">
                                <p v-if="!isEditMode || (isEditMode && isItemsManagementView())">
                                    {{part.location}}
                                </p>
                                <input style="width: 120px"
                                       v-if="isEditMode && !isItemsManagementView()"
                                       v-model="part.location" type="text"/>
                            </td>
                            <td>
                                <button type="button"
                                        style="width: 146px"
                                        @click="setItem(part.itemId)">
                                    {{part.buttonText}}
                                </button>
                            </td>
                            <td>
                                <p v-if="!isEditMode
                                && part.quantity > 0">{{part.quantity}}</p>
                                <input style="width: 80px"
                                       v-if="isEditMode && !isItemsManagementView()"
                                       v-model="part.quantity" type="text"/>
                            </td>
                            <td>
                            <td v-if="isEditMode">
                                <button v-model="newItemView"
                                        v-if="isItemDeleteButtonVisibleToCurrentUser(part)"
                                        type="button"
                                        class="round-button"
                                        @click="removePartFromList(part, table.parts)">
                                    {{"-"}}
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <p></p>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr v-if="notStub(itemView.partsTable.name)">
                <td colspan="3">
                    <table>
                        <tbody>
                        <tr style="text-align: center; color: red">
                            <td colspan="3">
                                {{newPartMessage}}
                            </td>
                        </tr>
                        <tr v-if="isEditMode" style="text-align: left">
                            <td>
                                <input style="width: 120px" v-model="newPart.location" type="text"/>
                            </td>
                            <td>
                                <select style="width: 146px"
                                        class="content"
                                        v-model="newPart"
                                        @change="partSelectOnChange()">
                                    <option v-for="part in itemView.possibleParts"
                                            v-if="selectOptionVisible(part)"
                                            v-bind:value="part">
                                        {{part.selectText}}
                                    </option>
                                </select>
                            </td>
                            <td>
                                <input style="width: 100%" v-model="newPart.quantity" type="text"/>
                            </td>
                            <td>
                                <button type="button"
                                        class="round-button"
                                        @click="addPart()">
                                    {{"+"}}
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr v-if="isReplacersTitleVisible()">
                <td style="text-align: center" colspan="3">
                    {{itemView.replacersTable.name}}
                </td>
            </tr>
            <tr v-if="notStub(itemView.replacersTable.name) && statusActive(replacer)"
                style="text-align: left; width: 440px"
                v-for="replacer in itemView.replacersTable.replacers">
                <td>
                    <p v-if="!isEditMode">{{replacer.comment}}</p>
                    <input v-if="isEditMode" v-model="replacer.comment" type="text"/>
                </td>
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
                            @click="removeReplacerFromList(replacer)">
                        {{"-"}}
                    </button>
                </td>
            </tr>
            <tr style="text-align: center; color: red">
                <td colspan="3">
                    {{newReplacerMessage}}
                </td>
            </tr>
            <tr v-if="notStub(itemView.replacersTable.name) && isEditMode" style="text-align: left">
                <td>
                    <input v-model="newReplacer.comment" type="text"/>
                </td>
                <td>
                    <select class="content" v-model="newReplacer" @change="replacerSelectOnChange()">
                        <option v-for="replacer in itemView.replacers"
                                v-if="selectOptionVisible(replacer)"
                                v-bind:value="replacer">
                            {{replacer.selectText}}
                        </option>
                    </select>
                </td>
                <td>
                    <button type="button"
                            class="round-button"
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
                text: "",
                isEditMode: false,
                newItemView: "",
                newItemCategory: "",
                newItemName: "",
                newHeaderRowMessage: "",
                newPartMessage: "",
                newReplacerMessage: "",
                categoryMessage: "",
                newItemNameMessage: "",
                actionType: "",
                newHeaderRow: {
                    parameter: "",
                    value: ""
                },
                newPart: {
                    id: "",
                    name: "",
                    itemId: "",
                    itemName: "",
                    itemCategory: "",
                    buttonText: "",
                    selectText: "",
                    comment: "",
                    location: "",
                    quantity: "",
                    status: "",
                    creatorName: ""
                },
                newReplacer: {
                    id: "",
                    name: "",
                    itemId: "",
                    itemName: "",
                    itemCategory: "",
                    buttonText: "",
                    selectText: "",
                    comment: "",
                    location: "",
                    quantity: "",
                    status: "",
                    creatorName: ""
                }
            }
        },

        created() {
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                admin: state => state.dictionary.admin,
                itemView: state => state.dictionary.itemViews[state.dictionary.itemViews.length - 1],
                itemId: state => state.dictionary.itemIds[state.dictionary.itemIds.length - 1]
            })
        },

        methods: {

            openWishList() {

            },

            isInWishList(itemId) {
                for (let i=0; i < this.itemView.wishListIds.length; i++) {
                    if (this.itemView.wishListIds[i] === itemId) {
                        return true;
                    }
                }
                return false;
            },

            addThisItemToWishList() {
                this.newItemView = this.itemView;
                this.itemView.addToWishList = true;
                this.save();
            },

            addHeaderRow() {
                this.newHeaderRowMessage = "";
                if (this.newLineIsEmpty()) {
                    this.newHeaderRowMessage = "Parameter and value fields shouldn't be empty"
                } else if (this.rowAlreadyInList(this.newHeaderRow.parameter)) {
                    this.newHeaderRowMessage = "Parameter already exists"
                } else {
                    let row = [this.newHeaderRow.parameter, this.newHeaderRow.value];
                    this.newItemView.header.matrix.push(row);
                    this.clearNewHeaderRow();
                }
            },

            addPart() {
                this.newPartMessage = "";
                this.newPart.name = this.getItemName() + this.newPart.name;
                let category = this.newPart.itemCategory;
                let targetTable = this.getTable(category);
                if (this.childItemAlreadyInList(this.newPart.itemId, targetTable)) {
                    this.newPartMessage = "Part already in list";
                } else if (this.newPart.quantity === "0") {
                    this.newPartMessage = "Quantity shouldn't be zero";
                } else if (this.newPart.quantity.includes("-")) {
                    this.newPartMessage = "Quantity shouldn't include - character";
                } else {
                    targetTable.parts.push(this.newPart);
                    this.clearNewPart();
                }
            },

            addReplacer() {
                this.newReplacerMessage = "";
                this.newReplacer.name = this.getItemName() + this.newReplacer.name;
                if (this.replacerAlreadyInList(this.newReplacer.itemId)) {
                    this.newReplacerMessage = "Replacer already in list";
                } else {
                    this.newItemView.replacersTable.replacers.push(this.newReplacer);
                    this.clearNewReplacer();
                }
            },

            getItemName() {
                return this.itemView.header.matrix[0][1];
            },

            newLineIsEmpty() {
                return this.newHeaderRow.parameter === "" || this.newHeaderRow.value === "";
            },

            rowAlreadyInList(parameter) {
                for (let i=0; i < this.newItemView.header.matrix.length; i++) {
                    if (this.newItemView.header.matrix[i][0] === parameter) {
                        return true;
                    }
                }
                return false;
            },

            childItemAlreadyInList(id, table) {
                for (let i=0; i < table.parts.length; i++) {
                    if (table.parts[i].itemId === id) {
                        return true;
                    }
                }
                return false;
            },

            replacerAlreadyInList(id) {
                for (let i=0; i < this.newItemView.replacersTable.replacers.length; i++) {
                    if (this.newItemView.replacersTable.replacers[i].itemId === id) {
                        return true;
                    }
                }
                return false;
            },

            removeRowFromHeader(row) {
                this.removeFromArray(row, this.newItemView.header.matrix);
            },

            removePartFromList(part, array) {
                this.removeFromArray(part, array);
                if (this.isItemsManagementView()) {
                    this.itemView.idsToRemove.push(part.itemId);
                }
            },

            removeReplacerFromList(replacer) {
                this.removeFromArray(replacer, this.newItemView.replacersTable.replacers);
            },

            removeFromArray(element, array) {
                array.splice(array.indexOf(element), 1)
            },

            getTable(category) {
                for (let i=0; i < this.newItemView.partsTable.tables.length; i++) {
                    if (this.newItemView.partsTable.tables[i].name === category) {
                        return this.newItemView.partsTable.tables[i];
                    }
                }
                return this.newItemView.partsTable;
            },

            partSelectOnChange() {
                this.newPartMessage = "";
            },

            replacerSelectOnChange() {
                this.newReplacerMessage = "";
                this.categoryMessage = "";
            },

            categorySelectOnChange() {
                this.categoryMessage = "";
            },

            stubMethod() {

            },

            isShowInfoButton(message) {
                return message === 'show button' && !this.isEditMode && !this.isItemsManagementView();
            },

            create() {
                this.clearItemCreationMessages();
                if (this.newItemCategory === "") {
                    this.categoryMessage = "Category not specified";
                } if (this.newItemName === "") {
                    this.newItemNameMessage = "Item name not specified"
                } else {
                    this.clearItemCreationMessages();
                    axios
                        .post("backend/item/create/"
                            + this.newItemCategory
                            + "/" + this.newItemName
                            + "/" + this.userName, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                        .then(response => {
                            let newItemView = response.data;
                            this.setItem(newItemView.itemId);
                        });
                }
            },

            setItem(id) {
                this.$store.dispatch("addItemId", id);
                this.$emit('select-item', id);
                this.switchEditModeOff();
            },

            edit() {
                this.isEditMode = true;
                this.newItemView = this.itemView;
            },

            cancel() {
                let id = this.itemId;
                this.$emit('cancel', id);
                this.switchEditModeOff();
            },

            switchEditModeOff() {
                this.isEditMode = false;
                this.clearAllEditData();
            },

            clearAllEditData() {
                this.clearNewHeaderRow();
                this.clearNewPart();
                this.clearNewReplacer();
                this.clearAllMessages();
                this.clearNewItemData();
            },

            clearAllMessages() {
                this.newHeaderRowMessage = "";
                this.newPartMessage = "";
                this.newReplacerMessage = "";
                this.clearItemCreationMessages();
            },

            clearItemCreationMessages() {
                this.categoryMessage = "";
                this.newItemNameMessage = "";
            },

            clearNewItemData() {
                this.newItemCategory = "";
                this.newItemName = "";
            },

            clearNewHeaderRow() {
                this.newHeaderRow = {
                    parameter: "",
                    value: ""
                };
            },

            clearNewPart() {
                this.newPart = this.clearItem();
            },

            clearNewReplacer() {
                this.newReplacer = this.clearItem();
            },

            clearItem() {
                return  {
                    id: "",
                    name: "",
                    itemId: "",
                    itemName: "",
                    itemCategory: "",
                    buttonText: "",
                    selectText: "",
                    comment: "",
                    location: "",
                    quantity: "",
                    status: "",
                    creatorName: ""
                }
            },

            save() {
                let id;
                if (this.isItemsManagementView()) {
                    id = -1;
                } else {
                    id = this.newItemView.itemId;
                }
                this.update(id);
            },

            update(id) {
                axios
                    .put("/backend/item/update/" + id + "/" + this.userName, this.newItemView, {
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

            isPartsTitleVisible() {
                return (this.notStub(this.itemView.partsTable.name) && this.itemHaveActiveParts())
                    || (this.notStub(this.itemView.partsTable.name) && this.isEditMode);
            },

            isReplacersTitleVisible() {
                return (this.notStub(this.itemView.replacersTable.name)
                    && this.arrayHaveActiveItems(this.itemView.replacersTable.replacers))
                || (this.notStub(this.itemView.replacersTable.name) && this.isEditMode);
            },

            itemHaveActiveParts() {
                for (let i = 0; i < this.itemView.partsTable.tables.length; i++) {
                    let table = this.itemView.partsTable.tables[i];
                    if (this.arrayHaveActiveItems(table.parts)) {
                        return true;
                    }
                }
                return false;
            },

            arrayHaveActiveItems(array) {
                for (let i=0; i < array.length; i++) {
                    if (this.statusActive(array[i])) {
                        return true;
                    }
                }
                return false;
            },

            isOrdinaryItemView() {
                return !this.isItemsManagementView() && !this.isMotorcycleCatalogueView();
            },

            isItemsManagementView() {
                return this.itemView.itemId === -1;
            },

            isMotorcycleCatalogueView() {
                return this.itemView.itemId === -2;
            },

            notStub(name) {
                return name !== "stub";
            },

            statusActive(item) {
                return item.status === "active";
            },

            isNotThisItem(item) {
                return item.itemId !== this.itemView.itemId;
            },

            selectOptionVisible(option) {
                return this.statusActive(option) && this.isNotThisItem(option);
            },

            isItemDeleteButtonVisibleToCurrentUser(item) {
                return this.admin || this.currentUserIsCreator(item)
            },

            currentUserIsCreator(item) {
                this.text = item;
                return item.creatorName === this.userName;
            },
        }
    }
</script>