<template>
    <div v-if="!isMotorcycleCatalogueView() && !isGuest()">
        <table>
            <tbody>
            <tr>
                <td class="two-columns-table-left-column">
                    <button v-if="editMode"
                            type="button"
                            class="background-green"
                            @click="cancel()">
                        {{translate("Cancel")}}
                    </button>
                </td>
                <td class="two-column-table-right-column">
                    <button v-if="!editMode"
                            type="button"
                            @click="edit()">
                        {{translate("Edit")}}
                    </button>
                    <button id="save-button" v-if="isSaveButtonRendered() && !userForm && !itemForm"
                            type="button"
                            class="background-red"
                            @click="save()">
                        {{translate('Save')}}
                    </button>
                    <input class="background-red" v-if="isSaveButtonRendered() && userForm"
                           type="submit" form="user-form" :value="translate('Save')"/>
                    <input class="background-red" v-if="isSaveButtonRendered() && itemForm"
                           type="submit" form="item-form" :value="translate('Save')"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from "axios";
    import storeUtil from "../../util/storeUtil";
    import {mapState} from "vuex";
    import itemViewUtil from "../../util/itemViewUtil";
    import ButtonAdd from "../element/button/ButtonAdd";
    import routerUtil from "../../util/routerUtil";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import userUtil from "../../util/userUtil";
    import basicComponent from "../../mixin/basicComponent";

    export default {
        name: "EditPanel",
        components: {ButtonAdd},

        props: {
            userForm: Boolean,
            itemForm: Boolean
        },

        mixins: [basicComponent],

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            })
        },

        methods: {
            cancel() {
                storeUtil.setEditMode(false);
                routerUtil.refresh();
            },

            edit() {
                if (routerUtil.isItem(this.$route)) {
                    storeUtil.setLoadingStateLoading();
                    axios
                        .get(this.basicUrl
                            + "/" + "item"
                            + "/" + "edit-data"
                            + "/" + this.itemView.itemId, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                        .then(response => {
                            this.itemView.possibleParts = response.data.parts;
                            this.itemView.possibleReplacers = response.data.replacers;
                            storeUtil.setEditMode(true);
                        }).finally(() => storeUtil.setLoadingStateOff());
                } else {
                    storeUtil.setEditMode(true);
                }
            },

            save() {
                storeUtil.setLoadingStateDefault();
                storeUtil.setEditMode(false);
                this.$emit("save");
            },

            isSaveButtonRendered() {
                return this.editMode;
            },

            isMotorcycleCatalogueView() {
                return itemViewUtil.isMotorcycleCatalogueView(this.itemView);
            },

            isGuest() {
                return userUtil.isGuest();
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>
</style>