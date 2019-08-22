<template>
    <div>
        <table style="text-align: center">
            <tr>
                <td style="width: 120px"></td>
                <td></td>
                <td style="text-align: right; width: 80px">
                    <button type="button"
                            v-on:click="openAddToWishListForm()"
                            :disabled="selected.length === 0">
                        Remove
                    </button>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <p id="title" style="text-align: center;"><b>Wishlist</b></p>
                </td>
            </tr>
            <tr>
                <td>Category</td>
                <td>Name</td>
                <td>Quantity</td>
            </tr>
        </table>
        <table class="get-all-table">
            <tbody>
            <tr v-for="table in wishList.tables">
                <td>
                    <table class="get-all-table">
                        <tbody>
                        <tr>
                            <td style="width: 120px">
                                <b>{{table.matrix[0][0]}}</b>
                            </td>
                            <td></td>
                            <td style="width: 80px"></td>
                        </tr>
                        <tr v-for="row in table.matrix">
                            <td>
                                {{row[0]}}
                            </td>
                            <td>
                                {{row[1]}}
                            </td>
                            <td>
                                {{row[2]}}
                            </td>
                        </tr>
                        <tr>
                            <p></p>
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
    import {mapState} from 'vuex';

    export default {

        data() {
            return {
                selected: [],
                selectAll: false
            }
        },

        created() {
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                wishList: state => state.dictionary.wishList
            })
        },

        methods: {
        }
    }
</script>