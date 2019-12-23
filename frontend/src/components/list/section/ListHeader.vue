<template>
    <div v-if="isShowPartsTableHeader()">
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
                <td class="three-column-table-button-column"/>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import shared from "../../../util/shared";

    export default {
        name: "ListHeader",

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode
            })
        },

        methods: {
            isShowPartsTableHeader() {
                let partsTable = this.itemView.partsTable;
                let header = partsTable.header;
                if (header === null) {
                    return false;
                }
                if (this.editMode === true) {
                    return true;
                }
                if (partsTable.parts.length < 1) {
                    return false;
                }
                if (shared.isInArray("-", header)) {
                    return false;
                }
                return true;
            }
        }
    }
</script>

<style scoped>

</style>