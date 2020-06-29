<template>
    <div v-if="isEnglish() && !isGuest()" class="default-margin bordered">
        <details>
            <summary>{{translate("Create new item")}}</summary>
            <form id="create-item-form" @submit="submit">
                <table>
                    <tbody>
                    <tr><td colspan="2" class="alert-message">{{translate(categoryMessage)}}</td></tr>
                    <tr><td colspan="2" class="alert-message">{{translate(newItemNameMessage)}}</td></tr>
                    <tr>
                        <td class="two-columns-table-left-column">
                            {{translate("Category")}}
                        </td>
                        <td class="two-column-table-right-column">
                            {{translate("Name")}}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text"
                                   required
                                   list="categories"
                                   pattern="[a-zA-Zа-яА-Я0-9 +№_\\.;,/-]*"
                                   title="Allowed: letters, numbers, -, +, _, /, ;, №, dot, comma, space"
                                   @change="categorySelectOnChange()"
                                   v-model="newItemCategory"/>
                            <datalist id="categories">
                                <select>
                                    <option v-for="category in sort(itemView.allCategories)"
                                            :value="category">
                                        {{category}}
                                    </option>
                                </select>
                            </datalist>
                        </td>
                        <td>
                            <input type="text"
                                   required
                                   pattern="[a-zA-Zа-яА-Я0-9 +№_\\.;,/-]*"
                                   title="Allowed: letters, numbers, -, +, _, /, ;, №, dot, comma, space"
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
            </form>
        </details>
    </div>
</template>

<script>
    import axios from "axios";
    import itemViewUtil from "../../util/itemViewUtil";
    import routerUtil from "../../util/routerUtil";
    import storeUtil from "../../util/storeUtil";
    import userUtil from "../../util/userUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: "CreateItemForm",

        mixins: [basicComponent, view],

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

            sort(array) {
                return array.slice().sort();
            }
        }
    }
</script>

<style scoped>

</style>