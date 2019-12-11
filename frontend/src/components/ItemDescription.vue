<template>
    <div>
        <div>
            <p><b>{{itemView.header.name}}</b></p>
            <p>{{$t("createdBy") + " " + itemView.creatorName}}</p>
        </div>

        <table id="item-description">
            <tbody>
            <tr style="text-align: left"
                v-for="row in itemView.header.rows">
                <td class="two-columns-table-left-column">
                    <p>
                        {{row.parameter}}
                    </p>
                </td>
                <td class="two-column-table-right-column">
                    <input v-if="isEditMode && isOrdinaryItemView()" v-model="row.value" type="text"/>
                    <p v-if="!isShowInfoButton(row)
                    && (!isEditMode || (isEditMode && !isOrdinaryItemView()))">
                        {{row.value}}
                    </p>
                    <button v-if="isShowInfoButton(row)"
                            type="button"
                            @click="navigateToItem(row.itemId)">
                        {{row.value}}
                    </button>
                </td>
                <td>
                    <button v-if="isRemoveHeaderRowButtonVisible(row.deletable)"
                            v-model="itemView"
                            type="button"
                            class="round-delete-button"
                            @click="removeRowFromHeader(row)">
                        {{"-"}}
                    </button>
                </td>
            </tr>
            <tr>
                <td colspan="3" class="alert-message">
                    {{newHeaderRowMessage}}
                </td>
            </tr>
            <tr v-if="isEditMode && isOrdinaryItemView()">
                <td>
                    <input v-model="newHeaderRow.parameter" type="text"/>
                </td>
                <td>
                    <input v-model="newHeaderRow.value" type="text"/>
                </td>
                <td>
                    <button type="button"
                            class="round-button"
                            @click="addHeaderRow()">
                        {{"+"}}
                    </button>
                </td>
            </tr>
            <tr style="height: 10px"><td></td></tr>
            <tr style="height: 26px">
                <td colspan="3"><hr></td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "ItemDescription"
    }
</script>

<style scoped>

</style>