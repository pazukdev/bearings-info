<template>
    <div v-if="!isMotorcycleCatalogueView() && !isGuest()">
        <table>
            <tbody>
            <tr>
                <td class="two-columns-table-left-column">
                    <button v-if="editMode"
                            type="button"
                            style="background: darkgreen"
                            @click="cancel()">
                        {{$t("cancel")}}
                    </button>
                </td>
                <td class="two-column-table-right-column">
                    <button v-if="!editMode"
                            type="button"
                            @click="edit()">
                        {{$t("edit")}}
                    </button>
                    <button id="save-button" v-if="isSaveButtonRendered() && !userForm && !itemForm"
                            type="button"
                            style="background: red"
                            @click="save()">
                        {{"123"}}
<!--                        {{$t("save")}}-->
                    </button>
                    <input class="submit" v-if="isSaveButtonRendered() && userForm"
                           type="submit" form="user-form" :value="$t('save')"/>
                    <input class="submit" v-if="isSaveButtonRendered() && itemForm"
                           type="submit" form="item-form" :value="$t('save')"/>
                </td>
                <td>
                    <ButtonAdd v-if="editMode" style="visibility: hidden"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import storeUtil from "../../util/storeUtil";
    import {mapState} from "vuex";
    import itemViewUtil from "../../util/itemViewUtil";
    import ButtonAdd from "../element/button/ButtonAdd";
    import routerUtil from "../../util/routerUtil";

    export default {
        name: "EditPanel",
        components: {ButtonAdd},

        props: {
            userForm: Boolean,
            itemForm: Boolean
        },

        computed: {
            ...mapState({
                userName: state => state.dictionary.userName,
                editMode: state => state.dictionary.editMode,
                itemView: state => state.dictionary.itemView
            })
        },

        methods: {
            cancel() {
                storeUtil.setEditMode(false);
                routerUtil.refresh();
            },

            edit() {
                storeUtil.setEditMode(true);
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
                return itemViewUtil.isGuest(this.userName);
            }
        }
    }
</script>

<style scoped>
    .submit, #save-button {
        background: red;
    }
</style>