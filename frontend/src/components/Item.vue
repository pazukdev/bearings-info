<template>
    <div>
<!--        {{file}}<br><br>-->
<!--        {{imageData}}<br><br>-->

        <table id="header-menu" class="no-border">
            <tbody>
            <tr>
                <td style="width: 33%">
                    <button type="button"
                            v-if="!isWishListView()"
                            v-on:click="openWishList()">
                        {{"Wishlist: " + itemView.wishListIds.length + " items"}}
                    </button>
                </td>
                <td></td>
                <td style="width: 33%; text-align: right">
                    <div>{{itemView.userData.itemName}}</div>
                    <div>{{"Rating: " + itemView.userData.rating}}</div>
                    <div v-if="itemView.userData.comment === 'Admin'">{{"You are admin"}}</div>
                </td>
            </tr>
            <tr><td colspan="3"><hr></td></tr>
            <tr>
                <td>
                    <button v-if="!isInWishList(itemView.itemId) && isOrdinaryItemView() && !isEditMode"
                            type="button"
                            style="width: 100%"
                            @click="addThisItemToWishList()">
                        {{"Add to Wish List"}}
                    </button>
                    <p v-if="isInWishList(itemView.itemId) && isOrdinaryItemView()">
                        {{"Item in Wish List"}}
                    </p>
                </td>
                <td></td>
                <td>
                    <button v-if="itemView.searchEnabled"
                            style="width: 100%"
                            type="button"
                            @click="stubMethod()">
                        {{"Google search"}}
                    </button>
                </td>
            </tr>
            <tr v-if="isOrdinaryItemView()">
                <td colspan="3"><hr></td>
            </tr>
            </tbody>
        </table>

        <table id="item-creation-menu" class="no-border">
            <tbody>
            <tr style="text-align: center">
                <td colspan="3">
                    <b>{{itemView.header.name}}</b>
                </td>
            </tr>
            <tr v-if="isOrdinaryItemView()" style="text-align: center">
                <td colspan="3">
                    {{"Created by " + itemView.creatorName}}
                </td>
            </tr>
            <tr style="height: 10px"><td></td></tr>
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
                            <td colspan="3"><hr></td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>

        <table id="item-description">
            <tbody>
            <tr style="text-align: left"
                v-for="row in itemView.header.matrix">
                <td style="width: 50%">
                    <p>
                        {{row[0]}}
                    </p>
                </td>
                <td>
                    <input v-if="isEditMode && isOrdinaryItemView()" v-model="row[1]" type="text"/>
                    <p v-if="!isShowInfoButton(row[3])
                    && (!isEditMode || (isEditMode && !isOrdinaryItemView()))">
                        {{row[1]}}
                    </p>
                    <button v-if="isShowInfoButton(row[3])" type="button"
                            style="width: 100%"
                            @click="setItem(row[2])">
                        {{row[1]}}
                    </button>
                </td>
                <td>
                    <button v-if="isEditMode && isOrdinaryItemView() && row[0] !== 'Name'"
                            v-model="newItemView"
                            type="button"
                            class="round-button"
                            style="background: red"
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
            <tr style="text-align: left" v-if="isEditMode && isOrdinaryItemView()">
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
            <tr style="height: 10px"><td></td></tr>
            <tr style="text-align: left">
                <td>
                    <button v-if="isEditMode"
                            type="button"
                            style="width: 194px"
                            @click="cancel()">
                        {{"Cancel"}}
                    </button>
                </td>
                <td v-if="!isMotorcycleCatalogueView()" style="text-align: right">
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
            <tr style="height: 26px">
                <td colspan="3"><hr></td>
            </tr>
            </tbody>
        </table>

        <table id="item-image"
               class="no-border"
               style="text-align: center"
        v-if="isViewWithImage()">
            <tbody>
            <tr v-if="imageData.length === 0">
                <td>
                    <img style="max-width: 100%"
                         :src="require(`../assets//${itemView.image}`)"
                         :alt="itemView.header.name">
                </td>
            </tr>
            <tr v-if="imageData.length > 0">
                <td>
                    <div class="image-preview">
                        <img class="preview" style="max-width: 100%" :src="imageData">
                    </div>
                </td>
            </tr>
            <tr v-if="isEditMode"><td>Upload another image</td></tr>
            <tr v-if="isEditMode">
                <td>
                    <input type="file" @change="previewImage">
                    <button @click="onUpload">Upload!</button>
                </td>
            </tr>
            <tr><td><hr></td></tr>
            </tbody>
        </table>

        <table id="parts-table"
        style="text-align: center">
            <tbody>
            <tr v-if="isPartsTitleVisible()">
                <td>
                    {{itemView.partsTable.name}}
                </td>
            </tr>
            <tr v-if="isShowPartsTableHeader() && isPartsTitleVisible">
                <td>
                    <table id="parts-header" style="text-align: center">
                        <tbody>
                        <tr>
                            <td style="width: 120px">{{itemView.partsTable.header[0]}}</td>
                            <td>{{itemView.partsTable.header[1]}}</td>
                            <td style="width: 80px; text-align: right"
                                v-if="itemView.partsTable.header[2] !== '-'">
                                {{itemView.partsTable.header[2]}}
                            </td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr v-for="table in itemView.partsTable.tables">
                <td v-if="table.parts.length > 0" colspan="3">
                    <table class="get-all-table">
                        <tbody>
                        <tr v-if="arrayHaveActiveItems(table.parts) && !isMotorcycleCatalogueView()">
                            <td style="width: 120px">
                                <b>{{table.name}}</b>
                            </td>
                            <td></td>
                            <td style="width: 80px"></td>
                            <td></td>
                        </tr>
                        <tr v-for="part in table.parts" v-if="statusActive(part)">
                            <td style="width: 120px">
                                <p v-if="!isEditMode || (isEditMode && !isOrdinaryItemView())">
                                    {{getFirstColumnValue(part)}}
                                </p>
                                <input style="width: 120px"
                                       v-if="isEditMode && isOrdinaryItemView()"
                                       v-model="part.location" type="text"/>
                            </td>
                            <td>
                                <p style="width: 146px"
                                     v-if="isUserListView()">
                                    {{part.buttonText}}
                                </p>
                                <button type="button"
                                        style="width: 146px"
                                        v-if="!isUserListView()"
                                        @click="setItem(part.itemId)">
                                    {{part.buttonText}}
                                </button>
                            </td>
                            <td>
                                <p v-if="!isEditMode && isShowQuantityValue()">
                                    {{part.quantity}}
                                </p>
                                <input style="width: 80px"
                                       v-if="isEditMode && isOrdinaryItemView()"
                                       v-model="part.quantity" type="text"/>
                            </td>
                            <td v-if="isEditMode && part.comment !== 'Admin'">
                                <button v-model="newItemView"
                                        v-if="isItemDeleteButtonVisibleToCurrentUser(part)"
                                        type="button"
                                        class="round-button"
                                        style="background: red"
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
            <tr v-if="notStub(itemView.partsTable.name) && isOrdinaryItemView()">
                <td>
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
            <tr v-if="isReplacersTitleVisible()"><td><hr></td></tr>
            </tbody>
        </table>

        <table id="replacers-table" style="text-align: center">
            <tbody>
            <tr v-if="isReplacersTitleVisible()">
                <td colspan="6">
                    {{itemView.replacersTable.name}}
                </td>
            </tr>
            <tr v-if="isReplacersTitleVisible()">
                <td colspan="5" style="height: 20px"></td>
            </tr>
            <tr v-if="notStub(itemView.replacersTable.name) && statusActive(replacer)"
                style="text-align: left"
                v-for="replacer in itemView.replacersTable.replacers">
                <td style="width: 160px">
                    <p v-if="!isEditMode">{{replacer.comment}}</p>
                    <input style="width: 160px"
                           v-if="isEditMode"
                           v-model="replacer.comment"
                           type="text"/>
                </td>
                <td style="text-align: center">
                    <button type="button"
                            style="width: 160px"
                            @click="setItem(replacer.itemId)">
                        {{replacer.buttonText}}
                    </button>
                </td>
                <td style="width: 30px; text-align: center">{{replacer.rating}}</td>
                <td>
                    <button v-if="!isEditMode && !isRated(replacer)" v-model="newItemView"
                            type="button"
                            class="round-button"
                            @click="rateAction('up', replacer.itemId)">
                        {{"&#x2191;"}}
                    </button>
                </td>
                <td style="text-align: right">
                    <button v-if="!isEditMode && !isRated(replacer)" v-model="newItemView"
                            type="button"
                            class="round-button"
                            @click="rateAction('down', replacer.itemId)">
                        {{" &#x2193;"}}
                    </button>
                    <button v-if="!isEditMode && isRated(replacer)" v-model="newItemView"
                            type="button"
                            class="round-button"
                            @click="rateAction('cancel', replacer.itemId)">
                        {{"x"}}
                    </button>
                    <button v-if="isEditMode" v-model="newItemView"
                            type="button"
                            class="round-button"
                            style="background: red"
                            @click="removeReplacerFromList(replacer)">
                        {{"-"}}
                    </button>
                </td>
            </tr>
            <tr style="text-align: center; color: red">
                <td colspan="5">
                    {{newReplacerMessage}}
                </td>
            </tr>
            <tr v-if="notStub(itemView.replacersTable.name) && isEditMode" style="text-align: left">
                <td style="width: 160px">
                    <input style="width: 160px" v-model="newReplacer.comment" type="text"/>
                </td>
                <td style="text-align: center">
                    <select class="content"
                            style="width: 160px"
                            v-model="newReplacer"
                            @change="replacerSelectOnChange()">
                        <option v-for="replacer in itemView.replacers"
                                v-if="selectOptionVisible(replacer)"
                                v-bind:value="replacer">
                            {{replacer.selectText}}
                        </option>
                    </select>
                </td>
                <td style="text-align: right" colspan="3">
                    <button type="button"
                            class="round-button"
                            @click="addReplacer()">
                        {{"+"}}
                    </button>
                </td>
            </tr>
            <tr><td colspan="5"><hr></td></tr>
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
                file: null,
                imageData: "",
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
                    creatorName: "",
                    rating: ""
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
                    creatorName: "",
                    rating: ""
                }
            }
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

            onUpload() {
                let data = new FormData();
                data.append("file", this.file);
                axios
                    .put("/backend/item/file-upload/" + this.itemView.itemId, data, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
            },

            previewImage(event) {
                let input = event.target;
                this.file = input.files[0];
                if (this.file !== null) {
                    let reader = new FileReader();
                    reader.onload = (e) => {
                        this.imageData = e.target.result;
                    };
                    reader.readAsDataURL(this.file);
                }
            },

            isShowPartsTableHeader() {
                return !(this.itemView.partsTable.header[0] === "-"
                    && this.itemView.partsTable.header[1] === "-"
                    && this.itemView.partsTable.header[2] === "-")
                    && this.itemView.category !== "Motorcycle";
            },

            addThisItemToWishList() {
                this.newItemView = this.itemView;
                this.itemView.addToWishList = true;
                this.save();
            },

            rateAction(action, itemId) {
                this.itemView.rate = {
                    action: action,
                    itemId: itemId
                };
                this.newItemView = this.itemView;
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

            isInWishList(itemId) {
                for (let i=0; i < this.itemView.wishListIds.length; i++) {
                    if (this.itemView.wishListIds[i] === itemId) {
                        return true;
                    }
                }
                return false;
            },

            isRated(replacer) {
                return this.isInArray(replacer.itemId, this.itemView.ratedItems);
            },

            isInArray(id, array) {
                for (let i=0; i < array.length; i++) {
                    if (array[i] === id) {
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
                if (this.isItemsManagementView() || this.isWishListView() || this.isUserListView()) {
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
                return message === 'show button' && !this.isEditMode && this.isOrdinaryItemView();
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
                        .post("/backend/item/create-view/"
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

            openWishList() {
                let wishListId = -3;
                this.setItem(wishListId);
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
                this.imageData = "";
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
                    creatorName: "",
                    rating: ""
                }
            },

            save() {
                this.update(this.newItemView.itemId);
            },

            update(id) {
                axios
                    .put("/backend/item/update-view/" + id + "/" + this.userName, this.newItemView, {
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
                return !this.isMotorcycleCatalogueView()
                    && (this.notStub(this.itemView.partsTable.name) && this.itemHaveActiveParts())
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

            getFirstColumnValue(item) {
                if (this.isUserListView()) {
                    return item.comment;
                } else {
                    return item.location;
                }

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
                return this.itemView.itemId > 0;
            },

            isItemsManagementView() {
                return this.itemView.itemId === -1;
            },

            isMotorcycleCatalogueView() {
                return this.itemView.itemId === -2;
            },

            isWishListView() {
                return this.itemView.itemId === -3;
            },

            isUserListView() {
                return this.itemView.itemId === -4;
            },

            isShowQuantityValue() {
                return this.isOrdinaryItemView() || this.isUserListView();
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
                return this.admin
                    || this.currentUserIsCreator(item)
                    || this.isOrdinaryItemView()
                    || this.isWishListView();
            },

            currentUserIsCreator(item) {
                this.text = item;
                return item.creatorName === this.userName;
            },

            isViewWithImage() {
                return this.itemView.image !== null;
            }
        }
    }
</script>