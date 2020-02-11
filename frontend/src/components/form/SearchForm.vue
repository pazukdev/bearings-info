<template>
    <div>
        <table>
            <tbody>
            <tr v-if="!editMode && !usageView">
                <td>
                    <input type="search" v-model="filter" @input="onChange" placeholder="Search...">
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    {{"Found: " + itemsCount}}
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "SearchForm",

        props: {
            usageView: Boolean,
            itemsCount: Number
        },

        computed: {
            ...mapState({
                editMode: state => state.dictionary.editMode
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
            }
        }
    }
</script>

<style scoped>

</style>