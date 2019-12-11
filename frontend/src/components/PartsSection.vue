<template>
    <div>
        <table id="parts-table">
            <tbody>
            <tr v-if="isPartsTitleVisible()">
                <td>
                    {{itemView.partsTable.localizedName}}
                </td>
            </tr>
            <tr v-if="isShowPartsTableHeader()">
                <td>
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
                            <td class="three-column-table-button-column"></td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr v-for="table in itemView.partsTable.tables" v-if="arrayHaveActiveItems(table.parts)">
                <td colspan="3">
                    <v-details v-model="table.opened">
                        <summary><b>{{table.localizedName}}</b></summary>
                        <table id="get-all-table">
                            <tbody>
                            <tr v-for="part in table.parts" v-if="statusActive(part)">
                                <td class="three-column-table-left-column">
                                    <p class="three-column-table-left-column-text"
                                       v-if="!isEditMode || (isEditMode && !isOrdinaryItemView())">
                                        {{getFirstColumnValue(part)}}
                                    </p>
                                    <input v-if="isEditMode && isOrdinaryItemView()"
                                           v-model="part.location" type="text"/>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <p v-if="isUserListView()">
                                        {{part.buttonText}}
                                    </p>
                                    <button type="button"
                                            v-if="!isUserListView()"
                                            @click="navigateToItem(part.itemId)">
                                        {{part.buttonText}}
                                    </button>
                                </td>
                                <td class="three-column-table-right-column">
                                    <div v-if="isShowQuantityValue()" class="parts-right-column-text">
                                        {{part.quantity}}
                                    </div>
                                    <div v-if="isMotorcycleCatalogueView() || isItemsManagementView()"
                                         class="parts-right-column-text">
                                        {{part.localizedComment}}
                                    </div>
                                    <input v-if="isEditMode && isOrdinaryItemView()"
                                           v-model="part.quantity" type="text"/>
                                </td>
                                <td class="three-column-table-button-column" v-if="isEditMode && part.comment !== 'Admin'">
                                    <button v-model="itemView"
                                            v-if="isItemDeleteButtonVisibleToCurrentUser(part)"
                                            type="button"
                                            class="round-button"
                                            style="background: red"
                                            @click="removePartFromList(part, table.parts)">
                                        {{"-"}}
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <p></p>
                            </tr>
                            </tbody>
                        </table>
                    </v-details>
                </td>
            </tr>
            <tr v-if="notStub(itemView.partsTable.name) && isOrdinaryItemView()">
                <td>
                    <table>
                        <tbody>
                        <tr style="text-align: center; color: red">
                            <td colspan="3">
                                {{newPartMessage}}
                            </td>
                        </tr>
                        <tr v-if="isEditMode" style="text-align: left">
                            <td class="three-column-table-left-column">
                                <input v-model="newPart.location" type="text"/>
                            </td>
                            <td class="three-column-table-middle-column">
                                <select v-model="newPart"
                                        @change="partSelectOnChange()">
                                    <option v-for="part in itemView.possibleParts"
                                            v-if="selectOptionVisible(part)"
                                            v-bind:value="part">
                                        {{part.selectText}}
                                    </option>
                                </select>
                            </td>
                            <td class="three-column-table-right-column">
                                <input type="text" v-model="newPart.quantity"/>
                            </td>
                            <td class="three-column-table-button-column">
                                <button type="button"
                                        class="round-button"
                                        @click="addPart()">
                                    {{"+"}}
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "PartsSection"
    }
</script>

<style scoped>

</style>