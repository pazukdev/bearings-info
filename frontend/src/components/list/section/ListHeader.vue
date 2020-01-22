<template>
    <div v-if="isShowPartsTableHeader()">
<!--        {{itemView.partsTable.header}}-->
        <table id="parts-header">
            <tbody>
            <tr>
                <td class="three-column-table-left-column">
                    {{getHeader()[0]}}
                </td>
                <td class="three-column-table-middle-column">
                    {{getHeader()[1]}}
                </td>
                <td class="three-column-table-right-column" v-if="getHeader()[2] !== '-'">
                    {{getHeader()[2]}}
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

        props: {
            header: Array
        },

        methods: {
            getHeader() {
                if (this.header != null) {
                    return this.header;
                }
                return this.itemView.partsTable.header;
            },

            isShowPartsTableHeader() {
                if (this.header != null) {
                    return true;
                }
                if (!this.itemView.partsEnabled) {
                    return false;
                }

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