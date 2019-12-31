<template>
    <div v-if="!isMotorcycleCatalogueView()">
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
                    <button id="save-button" v-if="isSaveButtonRendered() && !saveIsSubmit"
                            type="button"
                            style="background: red"
                            @click="save()">
                        {{$t("save")}}
                    </button>
                    <input id="submit" v-if="isSaveButtonRendered() && saveIsSubmit" type="submit" form="form"/>
                </td>
<!--                <td>-->
<!--                    <ButtonAdd v-if="editMode" style="visibility: hidden"/>-->
<!--                </td>-->
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
            saveIsSubmit: Boolean
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
                storeUtil.setLoadingState(true);
                storeUtil.setEditMode(false);
                this.$emit("save");
            },

            isSaveButtonRendered() {
                return this.editMode;
            },

            isMotorcycleCatalogueView() {
                return itemViewUtil.isMotorcycleCatalogueView(this.itemView);
            }
        }
    }
</script>

<style scoped>
    #submit, #save-button {
        background: red;
    }
</style>