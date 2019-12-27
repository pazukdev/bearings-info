<template>
    <div>
        <table>
            <tbody>
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
                        <option v-for="category in itemView.allCategories" :value="category">
                            {{category}}
                        </option>
                    </datalist>
                </td>
                <td>
                    <input @change="newItemNameMessage = ''" v-model="newItemName" type="text"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button type="button" @click="create()">
                        {{$t("create")}}
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
        <hr>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import itemViewUtil from "../../util/itemViewUtil";
    import routerUtil from "../../util/routerUtil";

    export default {
        name: "CreateItemForm",

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
                    let language = this.appLanguage.toString();
                    this.clearItemCreationMessages();
                    // basicUrl/item/create/{category}/{name}/{userName}/{language}
                    axios
                        .post(this.basicUrl.toString()
                            + "/" + "item/create"
                            + "/" + this.newItemCategory
                            + "/" + this.newItemName
                            + "/" + this.userName.toString()
                            + "/" + language, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                        .then(response => {
                            let newItemView = response.data;
                            itemViewUtil.dispatchView(this.$store, newItemView);
                            this.logEvent("a new item created", newItemView);
                            routerUtil.toItem(newItemView.itemId, language);
                        });
                }
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
                let array = this.itemView.partsTable.parts;
                for (let j = 0; j < array.length; j++) {
                    let item = array[j];
                    if (item.itemCategory === category && item.itemName === name) {
                        return true;
                    }
                }
                return false;
            },
        }
    }
</script>

<style scoped>

</style>