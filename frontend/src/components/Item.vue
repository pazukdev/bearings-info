<template>
    <div>
        <div v-if="isLoading()" style="text-align: center; padding-top: 50%">
            {{"Loading..."}}
        </div>
        <div v-if="!isLoading()">
                    <div style="text-align: left">
<!--                        {{"itemView.imgData: " + itemView.imgData}}<br>-->
<!--                        {{"imgData: " + imgData}}<br>-->
<!--                        {{"newItemView.imageData: " + this.newItemView.imgData}}<br>-->
                    </div>
            <table id="header-menu">
                <tbody>
                <tr>
                    <td class="third-part-wide">
                        <button type="button"
                                v-if="!isWishListView() && !isGuest()"
                                v-on:click="openWishList()">
                            {{"Wishlist: " + itemView.wishListIds.length + " items"}}
                        </button>
                    </td>
                    <td></td>
                    <td class="third-part-wide" style="text-align: right">
                        <div v-if="!isGuest()">{{itemView.userData.itemName}}</div>
                        <div v-if="!isGuest()">{{"Rating: " + itemView.userData.rating}}</div>
                        <div v-if="isAdmin()">{{"You are admin"}}</div>
                        <div v-if="isGuest()">{{"You are guest"}}</div>
                    </td>
                </tr>
                <tr><td colspan="3"><hr></td></tr>
                <tr>
                    <td>
                        <button v-if="isAddToWishlistButtonVisible()"
                                type="button"
                                @click="addThisItemToWishList()">
                            {{"Add to Wish List"}}
                        </button>
                        <p v-if="isInWishList(itemView.itemId) && isOrdinaryItemView() && !isGuest()">
                            {{"Item in Wish List"}}
                        </p>
                    </td>
                    <td></td>
                    <td>
                        <button v-if="itemView.searchEnabled"
                                type="button"
                                @click="searchInGoogle()">
                            {{"Google search"}}
                        </button>
                    </td>
                </tr>
                <tr v-if="isOrdinaryItemView()">
                    <td colspan="3"><hr></td>
                </tr>
                </tbody>
            </table>

            <table id="item-creation-menu">
                <tbody>
                <tr>
                    <td colspan="3">
                        <b>{{itemView.header.name}}</b>
                    </td>
                </tr>
                <tr v-if="isOrdinaryItemView()">
                    <td colspan="3">
                        {{"Created by " + itemView.creatorName}}
                    </td>
                </tr>
                <tr style="height: 10px"><td></td></tr>
                <tr v-if="isItemsManagementView()">
                    <td colspan="3">
                        <table>
                            <tbody>
                            <tr>
                                <td colspan="2"><hr></td>
                            </tr>
                            <tr>
                                <td colspan="2">{{"Create new item"}}</td>
                            </tr>
                            <tr style="color: red">
                                <td colspan="2">{{categoryMessage}}</td>
                            </tr>
                            <tr style="color: red">
                                <td colspan="2">{{newItemNameMessage}}</td>
                            </tr>
                            <tr>
                                <td class="two-columns-table-left-column">
                                    {{"Category"}}
                                </td>
                                <td class="two-column-table-right-column">
                                    {{"Name"}}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text"
                                           list="categories"
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
                                    <input @change="newItemNameMessage = ''"
                                           v-model="newItemName"
                                           type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <button type="button"
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

            <table id="item-description">
                <tbody>
                <tr style="text-align: left"
                    v-for="row in itemView.header.matrix">
                    <td class="two-columns-table-left-column">
                        <p>
                            {{row[0]}}
                        </p>
                    </td>
                    <td class="two-column-table-right-column">
                        <input v-if="isEditMode && isOrdinaryItemView()" v-model="row[1]" type="text"/>
                        <p v-if="!isShowInfoButton(row[3])
                    && (!isEditMode || (isEditMode && !isOrdinaryItemView()))">
                            {{row[1]}}
                        </p>
                        <button v-if="isShowInfoButton(row[3])"
                                type="button"
                                @click="getItemView(row[2])">
                            {{row[1]}}
                        </button>
                    </td>
                    <td>
                        <button v-if="isEditMode && isOrdinaryItemView() && row[0] !== 'Name'"
                                v-model="newItemView"
                                type="button"
                                class="round-delete-button"
                                @click="removeRowFromHeader(row)">
                            {{"-"}}
                        </button>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" class="alert-message">
                        {{newHeaderRowMessage}}
                    </td>
                </tr>
                <tr v-if="isEditMode && isOrdinaryItemView()">
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
                <tr>
                    <td>
                        <button v-if="isEditMode"
                                type="button"
                                @click="cancel()">
                            {{"Cancel"}}
                        </button>
                    </td>
                    <td v-if="isEditButtonVisible()" style="text-align: right">
                        <button v-if="!isEditMode"
                                type="button"
                                @click="edit()">
                            {{"Edit"}}
                        </button>
                        <button v-if="isEditMode"
                                type="button"
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
                   v-if="isViewWithImage()">
                <tbody>
                <tr v-if="itemView.imgData !== '-'">
                    <td>
                        <div class="image-preview">
                            <img class="preview" :src="itemView.imgData" @change="previewImage">
                        </div>
                    </td>
                </tr>
                <tr v-if="isEditMode">
                    <td>
                        <br>
                        Upload another image<br>
                        Accepts .png images only<br>
                        Size limit: 2MB<br>
                        <br>
                    </td>
                </tr>
                <tr v-if="isEditMode" class="alert-message">
                    <td>
                        {{fileUploadMessage}}
                    </td>
                </tr>
                <tr v-if="isEditMode">
                    <td>
                        <input type="file" accept="image/png"
                               style="color: black"
                               @change="previewImage"><br><br>
<!--                        <button @click="onUpload">Upload!</button>-->
                    </td>
                </tr>
                <tr><td><hr></td></tr>
                </tbody>
            </table>

            <table id="parts-table">
                <tbody>
                <tr v-if="isPartsTitleVisible()">
                    <td>
                        {{itemView.partsTable.name}}
                    </td>
                </tr>
                <tr v-if="isShowPartsTableHeader()">
                    <td>
                        <table id="parts-header">
                            <tbody>
                            <tr>
                                <td class="three-column-table-left-column">
                                    {{itemView.partsTable.header[0]}}
                                </td>
                                <td class="three-column-table-middle-column">
                                    {{itemView.partsTable.header[1]}}
                                </td>
                                <td class="three-column-table-right-column" v-if="itemView.partsTable.header[2] !== '-'">
                                    {{itemView.partsTable.header[2]}}
                                </td>
                                <td class="three-column-table-button-column"></td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr v-for="table in itemView.partsTable.tables">
                    <td v-if="table.parts.length > 0" colspan="3">
                        <table id="get-all-table">
                            <tbody>
                            <tr v-if="arrayHaveActiveItems(table.parts)">
                                <td class="three-column-table-left-column">
                                    <b>{{table.name}}</b>
                                </td>
                                <td class="three-column-table-middle-column"></td>
                                <td class="three-column-table-right-column"></td>
                                <td class="three-column-table-button-column"></td>
                            </tr>
                            <tr v-for="part in table.parts" v-if="statusActive(part)">
                                <td class="three-column-table-left-column">
                                    <p class="three-column-table-left-column-text"
                                       v-if="!isEditMode || (isEditMode && !isOrdinaryItemView())">
                                        {{getFirstColumnValue(part)}}
                                    </p>
                                    <input v-if="isEditMode && isOrdinaryItemView()"
                                           v-model="part.location" type="text"/>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <p v-if="isUserListView()">
                                        {{part.buttonText}}
                                    </p>
                                    <button type="button"
                                            v-if="!isUserListView()"
                                            @click="getItemView(part.itemId)">
                                        {{part.buttonText}}
                                    </button>
                                </td>
                                <td class="three-column-table-right-column">
                                    <div v-if="isShowQuantityValue()" class="parts-right-column-text">
                                        {{part.quantity}}
                                    </div>
                                    <div v-if="!isEditMode && isMotorcycleCatalogueView()" class="parts-right-column-text">
                                        {{part.comment}}
                                    </div>
                                    <input v-if="isEditMode && isOrdinaryItemView()"
                                           v-model="part.quantity" type="text"/>
                                </td>
                                <td class="three-column-table-button-column" v-if="isEditMode && part.comment !== 'Admin'">
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
                                <td class="three-column-table-left-column">
                                    <input v-model="newPart.location" type="text"/>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <select v-model="newPart"
                                            @change="partSelectOnChange()">
                                        <option v-for="part in itemView.possibleParts"
                                                v-if="selectOptionVisible(part)"
                                                v-bind:value="part">
                                            {{part.selectText}}
                                        </option>
                                    </select>
                                </td>
                                <td class="three-column-table-right-column">
                                    <input type="text" v-model="newPart.quantity"/>
                                </td>
                                <td class="three-column-table-button-column">
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
                <tr v-if="isReplacersTableVisible()"><td><hr></td></tr>
                </tbody>
            </table>

            <table v-if="isReplacersTableVisible()" id="replacers-table" style="text-align: center">
                <tbody>
                <tr>
                    <td colspan="6">
                        {{itemView.replacersTable.name}}
                    </td>
                </tr>
                <tr>
                    <td colspan="5" style="height: 20px"></td>
                </tr>
                <tr v-if="notStub(itemView.replacersTable.name) && statusActive(replacer)"
                    style="text-align: left"
                    v-for="replacer in itemView.replacersTable.replacers">
                    <td class="three-column-table-left-column">
                        <p v-if="!isEditMode">{{replacer.comment}}</p>
                        <input v-if="isEditMode"
                               v-model="replacer.comment"
                               type="text"/>
                    </td>
                    <td class="three-column-table-middle-column">
                        <button type="button"
                                @click="getItemView(replacer.itemId)">
                            {{replacer.buttonText}}
                        </button>
                    </td>
                    <td class="three-column-table-right-column">{{replacer.rating}}</td>
                    <td>
                        <button v-if="isRateButtonVisible(replacer)" v-model="newItemView"
                                type="button"
                                class="round-button"
                                @click="rateAction('up', replacer.itemId)">
                            {{"&#x2191;"}}
                        </button>
                    </td>
                    <td>
                        <button v-if="isRateButtonVisible(replacer)" v-model="newItemView"
                                type="button"
                                class="round-button"
                                @click="rateAction('down', replacer.itemId)">
                            {{" &#x2193;"}}
                        </button>
                        <button v-if="isUnrateButtonVisible(replacer)" v-model="newItemView"
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
                <tr style="color: red">
                    <td colspan="5">
                        {{newReplacerMessage}}
                    </td>
                </tr>
                <tr v-if="notStub(itemView.replacersTable.name) && isEditMode">
                    <td>
                        <input v-model="newReplacer.comment" type="text"/>
                    </td>
                    <td>
                        <select v-model="newReplacer"
                                @change="replacerSelectOnChange()">
                            <option v-for="replacer in itemView.replacers"
                                    v-if="selectOptionVisible(replacer)"
                                    v-bind:value="replacer">
                                {{replacer.selectText}}
                            </option>
                        </select>
                    </td>
                    <td></td>
                    <td></td>
                    <td>
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

            <table id="additional-menu" v-if="isAdditionalMenuDisplayed()">
                <tbody>
                <tr><td colspan="3">{{"Additional menu"}}</td></tr>
                <tr>
                    <td class="three-column-table-left-column"></td>
                    <td class="three-column-table-middle-column">
                        <button type="button"
                                v-on:click="openItemsManagement()">
                            {{"Items management"}}
                        </button>
                    </td>
                    <td class="three-column-table-right-column"></td>
                    <td class="three-column-table-button-column"></td>
                </tr>
                <tr v-if="isAdmin()">
                    <td></td>
                    <td>
                        <button type="button"
                                v-on:click="openUsersList()">
                            {{"Users"}}
                        </button>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="3">
                        <div style="text-align: center; margin-top: 60px; margin-bottom: 20px">
                            {{"Minsk 2019"}}
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';

    export default {

        data() {
            return {
                text: "",
                // file: "",
                imgData: "",
                isEditMode: false,
                newItemView: "",
                newItemCategory: "",
                newItemName: "",
                newHeaderRowMessage: "",
                newPartMessage: "",
                newReplacerMessage: "",
                categoryMessage: "",
                newItemNameMessage: "",
                fileUploadMessage: "",
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
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                loadingState: state => state.dictionary.loadingState,
                itemView: state => state.dictionary.itemView,
                itemIds: state => state.dictionary.itemIds,
                lastItemId: state => state.dictionary.itemIds[state.dictionary.itemIds.length - 1]
            })
        },

        created() {
            this.setBasicUrl();
            if (!this.isAuthorized()) {
                this.loginAsGuest();
            } else {
                this.getItemView(this.lastItemId);
            }
        },

        methods: {
            setBasicUrl() {
                let hostname = window.location.hostname;
                let basicUrl;
                if (hostname === "localhost") {
                    basicUrl = "backend";
                } else {
                    basicUrl = "https://bearings-info.herokuapp.com";
                }
                this.$store.dispatch("setBasicUrl", basicUrl);
            },

            loginAsGuest() {
                let username = "guest";
                let password = "user";
                let credentialsUrl ="username=" + username + "&" + "password=" + password;
                axios
                    .post(this.basicUrl + "/login", credentialsUrl)
                    .then(response => {
                        if (response.status === 200) {
                            this.$store.dispatch("setLoadingState", true);
                            let authorization = response.data.Authorization;
                            this.$store.dispatch("setAuthorization", authorization);
                            this.$store.dispatch("setUserName", username);
                            console.log("logged in as " + username);
                            this.getItemView(this.lastItemId);
                        }
                    })
                    .catch(error => {
                        console.log("login as " + username + " failed");
                    });
            },

            getItemView(itemId) {
                this.$store.dispatch("setLoadingState", true);
                this.switchEditModeOff();
                axios
                    .get(this.basicUrl + "/item/get-view/" + itemId + "/" + this.userName, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let itemView = response.data;
                        this.dispatchView(itemView);
                        this.logEvent("item view displayed: item", itemView);
                    });
            },

            create() {
                this.clearItemCreationMessages();
                if (this.newItemCategory === "") {
                    this.categoryMessage = "Category not specified";
                } else if (this.newItemName === "") {
                    this.newItemNameMessage = "Item name not specified"
                } else if (this.sameItemNameExistsInCategory(this.newItemCategory, this.newItemName)) {
                    this.newItemNameMessage = "Item with this name already exists"
                } else {
                    this.$store.dispatch("setLoadingState", true);
                    this.clearItemCreationMessages();
                    axios
                        .post(this.basicUrl + "/item/create-view/"
                            + this.newItemCategory
                            + "/" + this.newItemName
                            + "/" + this.userName, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                        .then(response => {
                            let newItemView = response.data;
                            this.dispatchView(newItemView);
                            this.logEvent("a new item created", newItemView);
                        });
                }
            },

            update(itemId) {
                this.$store.dispatch("setLoadingState", true);
                this.switchEditModeOff();
                axios
                    .put(this.basicUrl + "/item/update-view/" + itemId + "/" + this.userName, this.newItemView, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let updatedItemView = response.data;
                        this.dispatchView(updatedItemView);
                        this.logEvent("item updated", updatedItemView);
                    });
            },

            onUpload() {
                // let data = new FormData();
                // data.append("file", this.file);
                // axios
                //     .put(this.basicUrl + "/item/file-upload/" + this.itemView.itemId, data, {
                //         headers: {
                //             'Content-Type' : 'image/png',
                //             Authorization: this.authorization
                //         }
                //     });

                // axios
                //     .post(this.basicUrl + "/item/file-upload/"
                //         + this.itemView.itemId
                //         + this.imageData.toString(), {
                //         headers: {
                //             Authorization: this.authorization
                //         }
                //     });

                // this.newItemView.newImageUrl = this.imageData.toString();
            },

            dispatchView(itemView) {
                this.$store.dispatch("addItemId", itemView.itemId);
                this.$store.dispatch("setItemView", itemView);
                this.$store.dispatch("setLoadingState", false);
            },

            logEvent(event, itemView) {
                console.log(event + ": "
                    + "id=" + itemView.itemId
                    + "; name=" + itemView.header.name);
            },

            isLoading() {
                return this.loadingState === true;
            },

            redirectToLogin() {
                if (!this.isAuthorized()) {
                    this.goToLogin();
                }
            },

            goToLogin() {
                this.$router.push({ path: '/login'});
            },

            isAuthorized() {
                return this.authorization !== "";
            },

            isAdditionalMenuDisplayed() {
                return this.isHome() && !this.isGuest();
            },

            isHome() {
                return this.itemIds.length === 1;
            },

            isAdmin() {
                return this.itemView.userData.comment === "Admin";
            },

            isGuest() {
                return this.itemView.userData.comment === "Guest" && this.userName === "guest";
            },

            openWishList() {
                let wishListId = -3;
                this.getItemView(wishListId);
            },

            openItemsManagement() {
                let itemsManagementId = -1;
                this.getItemView(itemsManagementId);

            },

            openUsersList() {
                let usersListId = -4;
                this.getItemView(usersListId);
            },

            searchInGoogle() {
                let q = "buy " + this.itemView.header.name;
                window.open('http://google.com/search?q=' + q);
            },

            previewImage(event) {
                let input = event.target;
                let file = input.files[0];
                if (file !== null) {
                    if (file.size > 2097152) {
                        this.fileUploadMessage = "Image is too big! Size limit is 2MB";
                        return;
                    }
                    this.fileUploadMessage = "";
                    let reader = new FileReader();
                    reader.onload = (e) => {
                        this.itemView.imgData = e.target.result;
                    };
                    reader.readAsDataURL(file);
                }
            },

            isShowPartsTableHeader() {
                return !(this.itemView.partsTable.header[0] === "-"
                    && this.itemView.partsTable.header[1] === "-"
                    && this.itemView.partsTable.header[2] === "-")
                    && this.itemView.category !== "Motorcycle"
                    && this.isPartsTitleVisible();
            },

            isAddToWishlistButtonVisible() {
                return !this.isInWishList(this.itemView.itemId)
                    && this.isOrdinaryItemView()
                    && !this.isEditMode
                    && !this.isGuest();
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

            isRateButtonVisible(replacer) {
                return !this.isEditMode && !this.isRated(replacer) && !this.isGuest();
            },

            isUnrateButtonVisible(replacer) {
                return !this.isEditMode && this.isRated(replacer) && !this.isGuest();
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

            isShowInfoButton(message) {
                return message === 'show button' && !this.isEditMode && this.isOrdinaryItemView();
            },

            edit() {
                this.isEditMode = true;
                this.newItemView = this.itemView;
            },

            cancel() {
                this.getItemView(this.lastItemId);
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
                this.imgData = "";
            },

            clearAllMessages() {
                this.newHeaderRowMessage = "";
                this.newPartMessage = "";
                this.newReplacerMessage = "";
                this.fileUploadMessage = "";
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

            isPartsTitleVisible() {
                return !this.isMotorcycleCatalogueView()
                    && (this.notStub(this.itemView.partsTable.name) && this.itemHaveActiveParts())
                    || (this.notStub(this.itemView.partsTable.name) && this.isEditMode);
            },

            isReplacersTableVisible() {
                return (this.notStub(this.itemView.replacersTable.name)
                    && this.arrayHaveActiveItems(this.itemView.replacersTable.replacers))
                || (this.notStub(this.itemView.replacersTable.name) && this.isEditMode);
            },

            sameItemNameExistsInCategory(category, name) {
                for (let i = 0; i < this.itemView.partsTable.tables.length; i++) {
                    let table = this.itemView.partsTable.tables[i];
                    if (table.name === category) {
                        for (let j = 0; j < table.parts.length; j++) {
                            let item = table.parts[j];
                            if (item.itemName === name) {
                                return true;
                            }
                        }
                    }
                }
                return false;
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

            isEditButtonVisible() {
                return !this.isMotorcycleCatalogueView() && !this.isGuest();
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
                return (!this.isEditMode && (this.isOrdinaryItemView() || this.isUserListView()))
                    || (this.isEditMode && this.isUserListView());
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
                return this.itemView.userData.comment === "Admin"
                    || this.currentUserIsCreator(item)
                    || this.isOrdinaryItemView()
                    || this.isWishListView();
            },

            currentUserIsCreator(item) {
                this.text = item;
                return item.creatorName === this.userName;
            },

            isViewWithImage() {
                // return this.itemView.image !== null;
                return true;
            }
        }
    }
</script>

<style scoped>
    #header-menu, #item-creation-menu, #item-image {
        border-spacing: 0;
    }

    .two-columns-table-left-column {
        width: 50%;
    }

    .two-column-table-right-column, .three-column-table-right-column, #get-all-table {
        width: 100%;
    }

    .three-column-table-left-column, .three-column-table-middle-column {
        width: 33.33%;
    }

    .three-column-table-left-column, .three-column-table-left-column-text {
        text-align: left;
    }

    .three-column-table-right-column {
        text-align: center;
    }

    .three-column-table-button-column {
    }

    .round-delete-button {
        background: red;
    }
</style>