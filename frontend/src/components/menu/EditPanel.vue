<template>
    <div v-if="!isMotorcycleCatalogueView()">
        <table>
            <tbody>
            <tr>
                <td class="two-columns-table-left-column">
                    <button v-if="editMode"
                            type="button"
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
                    <button v-if="editMode"
                            type="button"
                            @click="save()">
                        {{$t("save")}}
                    </button>
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

    export default {
        name: "EditPanel",
        components: {ButtonAdd},
        computed: {
            ...mapState({
                userName: state => state.dictionary.userName,
                editMode: state => state.dictionary.editMode,
                itemView: state => state.dictionary.itemView
            })
        },

        methods: {
            cancel() {
                storeUtil.setEditMode(this.$store, false);
                this.$router.go();
            },

            edit() {
                storeUtil.setEditMode(this.$store, true);
            },

            save() {
                storeUtil.setLoadingState(this.$store, true);
                storeUtil.setEditMode(this.$store, false);
                this.$emit("save");
            },

            isMotorcycleCatalogueView() {
                return itemViewUtil.isMotorcycleCatalogueView(this.itemView);
            }
        }
    }
</script>

<style scoped>

</style>