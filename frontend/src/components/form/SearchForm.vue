<template>
    <div>
        <table>
            <tbody>
            <tr v-if="!editMode && !usageView">
                <td>
                    <input type="search" v-model="filter" @input="onChange" :placeholder="translate('Search') + '...'">
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

    export default {
        name: "SearchForm",

        props: {
            usageView: Boolean,
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
                filter: ""
            }
        },

        methods: {
            onChange() {
                this.$emit('get-filter', this.filter);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>

</style>