<template>
    <div>
        <table>
            <tbody>
            <tr>
                <td colspan="2" style="text-align: center"><b>{{table.name}}</b></td>
            </tr>
            <tr style="text-align: left" v-for="row in table.matrix">
                <td style="width: 70%">
                    {{row[0]}}
                </td>
                <td>
                    {{row[1]}}
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
                table: state => state.dictionary.table
            })
        },

        methods: {
        }
    }
</script>