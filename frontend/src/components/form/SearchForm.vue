<template>
    <div>
        <table>
            <tbody>
            <tr v-if="!editMode && !usageView">
                <td>
                    <input type="search" v-model="filter" @input="onInput" @change="onChange"
                           :placeholder="translate('Search') + '...'">
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    {{translate("Found") + ": " + itemsCount}}
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import routerUtil from "../../util/routerUtil";

    export default {
        name: "SearchForm",

        props: {
            usageView: Boolean,
            itemsManagementView: Boolean,
            itemsCount: Number
        },

        computed: {
            ...mapState({
                editMode: state => state.dictionary.editMode,
                itemView: state => state.dictionary.itemView
            })
        },

        data() {
            return {
                filter: this.getFilter()
            }
        },

        methods: {
            onInput() {
                this.$emit('get-filter', this.filter);
            },

            onChange() {
                if (this.itemsManagementView) {
                    routerUtil.toItemsSearch(this.filter);
                }
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            getFilter() {
                return this.$route.params.filter;
            }
        }
    }
</script>

<style scoped>

</style>