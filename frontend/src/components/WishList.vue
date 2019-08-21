<template>
    <div>
        <p id="title" style="text-align: center"><b>Wishlist</b></p>
        <table>
            <tbody>
            <tr v-for="table in wishList.tables">
                <td>
                    <table style="text-align: center">
                        <tbody>
                        <tr v-for="row in table.matrix">
                            <td style="width: 120px">
                                {{row[0]}}
                            </td>
                            <td>
                                {{row[1]}}
                            </td>
                            <td style="width: 60px">
                                {{row[2]}}
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
    import {mapState} from 'vuex';

    export default {

        data() {
            return {

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