<template>
    <div>
        <table>
            <tbody>
            <tr>
                <td colspan="2"><hr></td>
            </tr>
            <tr>
                <td colspan="2">{{$t("createNewItem")}}</td>
            </tr>
            <tr style="color: red">
                <td colspan="2">{{categoryMessage}}</td>
            </tr>
            <tr style="color: red">
                <td colspan="2">{{newItemNameMessage}}</td>
            </tr>
            <tr>
                <td class="two-columns-table-left-column">
                    {{$t("category")}}
                </td>
                <td class="two-column-table-right-column">
                    {{$t("name")}}
                </td>
            </tr>
            <tr>
                <td>
                    <input type="text"
                           list="categories"
                           @change="categorySelectOnChange()"
                           v-model="newItemCategory"/>
                    <datalist id="categories">
                        <option v-for="category in allCategories"
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
                        {{$t("create")}}
                    </button>
                </td>
            </tr>
            <tr>
                <td colspan="2"><hr></td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";

    export default {
        name: "CreateItemForm",

        props: {
            allCategories: Array
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                loadingState: state => state.dictionary.loadingState,
                itemView: state => state.dictionary.itemView,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        data() {
            return {
                newItemCategory: "",
                newItemName: "",
                categoryMessage: "",
                newItemNameMessage: "",
                newItemMessage: ""
            }
        },

        methods: {
            categorySelectOnChange() {
                this.categoryMessage = "";
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
                        .post(this.basicUrl
                            + "/" + "item/create-view"
                            + "/" + this.newItemCategory
                            + "/" + this.newItemName
                            + "/" + this.userName
                            + "/" + this.appLanguage, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                        .then(response => {
                            let newItemView = response.data;
                            this.pushTo(newItemView.itemId);
                            this.dispatchView(newItemView);
                            this.logEvent("a new item created", newItemView);
                        });
                }
            },

            dispatchView(itemView) {
                this.$store.dispatch("setItemView", itemView);
                this.$store.dispatch("setLoadingState", false);
            },

            pushTo(itemId) {
                this.$router.push({ path: `/item/id/${itemId}/:lang` });
            },

            logEvent(event, itemView) {
                console.log(event + ": "
                    + "id=" + itemView.itemId
                    + "; name=" + itemView.header.name);
            },

            clearItemCreationMessages() {
                this.categoryMessage = "";
                this.newItemNameMessage = "";
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
        }
    }
</script>

<style scoped>

</style>