<template>
    <div>
        <table id="parts-table">
            <tbody>
            <tr v-if="searchIsRendered()">
                <td>
                    <input type="search" v-model="filter" placeholder="Search...">
                </td>
            </tr>
            <tr v-for="vehicleClass in itemsListAsTables()" v-if="vehicles">
                <td>
                    <v-details v-model="vehicleClass.opened">
                        <summary><b>{{vehicleClass.name}}</b></summary>
                        <table>
                            <tbody>
                            <tr v-for="manufacturer in vehicleClass.manufacturers">
                                <td>
                                    <details>
                                        <summary>{{manufacturer.name}}</summary>
                                        <table class="equal-columns-table">
                                            <tbody>
                                            <tr v-for="item in manufacturer.items">
                                                <td style="text-align: left">
                                                    <p>{{item.comment}}</p>
                                                </td>
                                                <td>
                                                    <ButtonNavigateToItem :part="item"/>
                                                </td>
                                                <td>
                                                    {{item.secondComment}}
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </details>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </v-details>
                </td>
            </tr>
            <tr v-for="table in itemsListAsTables()" v-if="!vehicles && !hideTable(table)">
                <td>
                    <v-details v-model="table.opened">
                        <summary><b>{{table.name}}</b></summary>
                        <table>
                            <tbody>
                            <tr v-for="item in table.items">
                                <td class="two-columns-table-left-column" style="text-align: left">
                                    <p>{{item.comment}}</p>
                                </td>
                                <td class="two-column-table-right-column">
                                    <ButtonNavigateToItem :part="item"/>
                                </td>
                                <td>
                                    <ButtonDelete :item="item" @remove-item="removeItem"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </v-details>
                </td>
            </tr>
            </tbody>
        </table>
        <hr>
    </div>
</template>

<script>
    import itemViewUtil from "../../util/itemViewUtil";
    import ButtonDelete from "../element/button/ButtonDelete";
    import {mapState} from "vuex";
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import EditPanel from "../menu/EditPanel";
    import ItemDescription from "./section/ItemDescription";
    import EditableImg from "../EditableImg";
    import shared from "../../util/shared";
    import arrayUtil from "../../util/arrayUtil";

    export default {
        name: "ItemList",
        components: {
            EditableImg,
            ItemDescription,
            EditPanel,
            ButtonNavigateToItem,
            ButtonDelete},

        props: {
            item: Boolean,
            editableComments: Boolean,
            itemsManagementView: Boolean,
            usageView: Boolean,
            vehicles:Boolean,
            itemViewProp: Object,
            sorted: Boolean
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        data() {
            return {
                filter: ""
            }
        },

        methods: {
            itemsListAsTables() {
                let itemView;
                if (this.usageView && this.itemViewProp != null) {
                    itemView = this.itemViewProp;
                } else {
                    itemView = this.itemView;
                }
                let items = itemView.children;

                // let opened = !this.itemsManagementView && !this.usageView;
                let opened = false;

                if (this.vehicles) {
                    let vehicleClasses = [];
                    let translatedVehicleClasses = [];
                    for (let i = 0; i < items.length; i++) {
                        let vehicleClass = items[i].vehicleClass;
                        let translatedVehicleClass = items[i].translatedVehicleClass;
                        if (!shared.isInArray(vehicleClass, vehicleClasses)) {
                            vehicleClasses.push(vehicleClass);
                        }
                        if (!shared.isInArray(translatedVehicleClass, translatedVehicleClasses)) {
                            translatedVehicleClasses.push(translatedVehicleClass);
                        }
                    }

                    let parentTables = [];

                    for (let i = 0; i < translatedVehicleClasses.length; i++) {
                        let category = translatedVehicleClasses[i];
                        let vehicles = [];

                        for (let j = 0; j < items.length; j++) {
                            let item = items[j];
                            if (item.translatedVehicleClass === category) {
                                vehicles.push(item);
                            }
                        }
                        let childTables = itemViewUtil.itemsListToTables(vehicles, true, this.filter, opened);
                        let parentTable = {
                            name: !shared.isEmpty(category) ? category : "Other",
                            manufacturers: childTables,
                            opened: vehicleClasses[i] === "Motorcycle"
                        };

                        if (childTables.length > 0) {
                            parentTables.push(parentTable);
                        }
                    }
                    return arrayUtil.sortByName(parentTables);
                } else {
                    return itemViewUtil.itemsListToTables(items, this.sorted, this.filter, opened);
                }

            },

            removeItem(item) {
                itemViewUtil.removeItemFromItemList(this.itemView, item);
            },

            isEdit() {
                return this.editableComments && this.editMode;
            },

            hideTable(table) {
                if (table.name.toLowerCase() === "guest" && !itemViewUtil.isAdmin(this.itemView)) {
                    return true;
                }
            },

            searchIsRendered() {
                return !this.editMode && !this.usageView;
            }
        }
    }
</script>

<style scoped>

</style>