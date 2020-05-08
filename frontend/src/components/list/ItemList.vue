<template>
    <div>
        <SearchForm :items-count="itemsCount"
                    :items-management-view="itemsManagementView"
                    @get-filter="getFilter"/>
        <DeletedItemsList :array="getDeletedItems()" @restore="restore"/>
        <table id="parts-table">
            <tbody>
            <tr v-for="vehicleClass in itemsListAsTables()" v-if="vehicles">
                <td>
                    <v-details v-model="vehicleClass.opened">
                        <summary><b>{{vehicleClass.name}}</b></summary>
                        <table>
                            <tbody>
                            <tr v-for="manufacturer in vehicleClass.manufacturers">
                                <td>
                                    <v-details v-model="opened">
                                        <summary>{{getTextPlusCount(manufacturer.name, manufacturer.items.length)}}</summary>
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
                                                    <a v-if="!isEmpty(item.secondComment)"
                                                       :href="processImgUrl(item.secondComment)"
                                                       target="_blank"
                                                       :title="translate('Tap to open image')">
                                                        <img class="list-img"
                                                             :src="processImgUrl(item.secondComment)" alt="Item image">
                                                    </a>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </v-details>
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
                        <summary><b>{{getTextPlusCount(table.name, table.items.length)}}</b></summary>
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
                                    <ButtonDelete :item="item"
                                                  :items-management-view="itemsManagementView"
                                                  @remove-item="removeItem"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </v-details>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import itemViewUtil from "../../util/itemViewUtil";
    import ButtonDelete from "../element/button/ButtonDelete";
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import EditPanel from "../menu/EditPanel";
    import ItemDescription from "./section/ItemDescription";
    import EditableImg from "../EditableImg";
    import shared from "../../util/shared";
    import arrayUtil from "../../util/arrayUtil";
    import SearchForm from "../form/SearchForm";
    import imgUtil from "../../util/imgUtil";
    import userUtil from "../../util/userUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";
    import DeletedItemsList from "../element/DeletedItemsList";
    import itemsList from "../../mixin/itemsList";

    export default {
        name: "ItemList",
        components: {
            DeletedItemsList,
            SearchForm,
            EditableImg,
            ItemDescription,
            EditPanel,
            ButtonNavigateToItem,
            ButtonDelete},

        props: {
            itemsManagementView: Boolean,
            usageView: Boolean,
            vehicles:Boolean,
            urlFilter: String,
            itemViewProp: Object
        },

        mixins: [basicComponent, view, itemsList],

        data() {
            return {
                filter: this.urlFilter,
                itemsCount: 0,
                opened: false
            }
        },

        methods: {
            itemsListAsTables() {
                let motorcycleCategory = this.translate("Motorcycle");
                let itemView;
                if (this.usageView && this.itemViewProp != null) {
                    itemView = this.itemViewProp;
                } else {
                    itemView = this.itemView;
                }
                let items = itemView.children;

                if (shared.isEmpty(items)) {
                    let millisecondsToWait = 1000;
                    setTimeout(function() {
                        throw "still waiting for itemView";
                    }, millisecondsToWait);
                    return [];
                }

                let opened = false;

                if (this.vehicles) {
                    let vehicleClasses = [];
                    for (let i = 0; i < items.length; i++) {
                        let vehicleClass = items[i].vehicleClass;
                        if (!shared.isInArray(vehicleClass, vehicleClasses)) {
                            vehicleClasses.push(vehicleClass);
                        }
                    }

                    let parentTables = [];

                    for (let i = 0; i < vehicleClasses.length; i++) {
                        let category = vehicleClasses[i];
                        let vehicles = [];

                        for (let j = 0; j < items.length; j++) {
                            let item = items[j];
                            if (item.vehicleClass === category) {
                                itemViewUtil.translateItem(item);
                                vehicles.push(item);
                            }
                        }
                        let filter = this.filter;
                        let childTables = itemViewUtil.itemsListToTables(vehicles, true, filter, opened).tables;
                        this.opened = !this.isEmpty(filter) && filter.length > 2;
                        let parentTable = {
                            name: !shared.isEmpty(category) ? category : this.translate("Not specified"),
                            manufacturers: childTables,
                            opened: category === motorcycleCategory || this.opened
                        };

                        if (childTables.length > 0) {
                            parentTables.push(parentTable);
                        }
                    }

                    this.itemsCount = arrayUtil.countVehicles(parentTables);
                    return arrayUtil.sortByName(parentTables);
                } else {
                    let result = itemViewUtil.itemsListToTables(items, this.sorted, this.filter, opened);
                    this.itemsCount = result.itemsCount;
                    return result.tables;
                }

            },

            hideTable(table) {
                if (table.name.toLowerCase() === "guest" && !userUtil.isAdmin(this.itemView)) {
                    return true;
                }
            },

            searchIsRendered() {
                return !this.editMode && !this.usageView;
            },

            getFilter(filter) {
                this.filter = filter;
            },

            processImgUrl(imgUrl) {
                return imgUtil.processUrl(imgUrl);
            }
        }
    }
</script>

<style scoped>
    img {
        /*max-height: 100%;*/
    }
</style>