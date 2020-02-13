<template>
    <div v-if="!isGuest()" class="default-margin">
        <details>
            <summary>{{translate("Create new item")}}</summary>
            <form id="create-item-form" @submit="submit">
                <table>
                    <tbody>
                    <tr><td colspan="2" class="alert-message">{{translate(categoryMessage)}}</td></tr>
                    <tr><td colspan="2" class="alert-message">{{translate(newItemNameMessage)}}</td></tr>
                    <tr>
                        <td class="two-columns-table-left-column">
                            {{translate("category")}}
                        </td>
                        <td class="two-column-table-right-column">
                            {{translate("name")}}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text"
                                   required
                                   list="categories"
                                   pattern="[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я0-9 _-]{0,24}[a-zA-Zа-яА-Я0-9_-]"
                                   :title="translate('Length: 2 - 26 characters: letters, numbers, - , _ , space')"
                                   @change="categorySelectOnChange()"
                                   v-model="newItemCategory"/>
                            <datalist id="categories">
                                <option v-for="category in itemView.allCategories" :value="category">
                                    {{category}}
                                </option>
                            </datalist>
                        </td>
                        <td>
                            <input type="text"
                                   required
                                   pattern="[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я0-9 _-]{0,24}[a-zA-Zа-яА-Я0-9_-]"
                                   :title="translate('Length: 2 - 26 characters: letters, numbers, - , _ , space')"
                                   @change="newItemNameMessage = ''"
                                   v-model="newItemName"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" class="background-grey" :value="translate('Create')"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <hr>
            </form>
        </details>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import itemViewUtil from "../../util/itemViewUtil";
    import routerUtil from "../../util/routerUtil";
    import storeUtil from "../../util/storeUtil";
    import dictionaryUtil from "../../util/dictionaryUtil";

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

            submit(e) {
                e.preventDefault();
                this.clearItemCreationMessages();
                if (this.newItemCategory === "") {
                    this.categoryMessage = "Category not specified";
                } else if (this.newItemName === "") {
                    this.newItemNameMessage = "Item name not specified"
                } else if (this.sameItemNameExistsInCategory(this.newItemCategory, this.newItemName)) {
                    this.newItemNameMessage = "Item with this name already exists in the category"
                } else {
                    storeUtil.setLoadingState("Creating");
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
                            itemViewUtil.dispatchView(newItemView);
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
                let array = this.itemView.children;
                for (let j = 0; j < array.length; j++) {
                    let item = array[j];
                    if (item.itemCategory === category && item.itemName === name) {
                        return true;
                    }
                }
                return false;
            },

            isGuest() {
                return itemViewUtil.isGuest(this.userName);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>

</style>