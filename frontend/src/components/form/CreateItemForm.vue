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
                                <option v-for="category in sort(itemView.allCategories)" :value="category">
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
    import userUtil from "../../util/userUtil";

    export default {
        name: "CreateItemForm",

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                loadingState: state => state.dictionary.loadingState,
                itemView: state => state.dictionary.itemView
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
                    let language = routerUtil.getLang(this.$route);
                    this.clearItemCreationMessages();
                    axios
                        .post(this.basicUrl.toString()
                            + "/" + "item/create"
                            + "/" + this.newItemCategory
                            + "/" + this.newItemName
                            + "/" + userUtil.getUserName()
                            + "/" + language, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                        .then(response => {
                            let newItemView = response.data;
                            itemViewUtil.dispatchView(newItemView, this.$route.params.lang);
                            this.logEvent("a new item created", newItemView);
                            routerUtil.toItem(newItemView.itemId, this.$route.params.lang);
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
                return userUtil.isGuest();
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            sort(array) {
                return array.slice().sort();
            }
        }
    }
</script>

<style scoped>

</style>