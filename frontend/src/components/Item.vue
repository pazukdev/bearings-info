<template>
    <div>
        <table>
            <tbody>
            <tr>
                <td colspan="2" style="text-align: center"><b>{{itemView.header.name}}</b></td>
            </tr>
            <tr style="text-align: left" v-for="row in itemView.header.matrix">
                <td style="width: 70%">
                    {{row[0]}}
                </td>
                <td>
                    {{row[1]}}
                </td>
            </tr>
            <tr v-for="item in itemView.items.tables">
                <td colspan="2">
                    <table class="get-all-table">
                        <tbody>
                        <tr>
                            <td style="width: 120px">
                                <b>{{item.name}}</b>
                            </td>
                            <td></td>
                            <td style="width: 80px"></td>
                        </tr>
                        <tr v-for="row in item.matrix">
                            <td>
                                {{row[0]}}
                            </td>
                            <td>
                                <button type="button"
                                        style="width: 60%"
                                        @click="$emit('select-item', row[3])">
                                    {{row[1]}}
                                </button>
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
            <tr>
                <td style="text-align: center" colspan="2">
                    {{itemView.replacers.name}}
                </td>
            </tr>
            <tr style="text-align: left" v-for="row in itemView.replacers.matrix">
                <td style="width: 70%">
                    {{row[0]}}
                </td>
                <td>
                    <button type="button"
                            style="width: 100%"
                            @click="$emit('select-item', row[2])">
                        {{row[1]}}
                    </button>
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
                itemView: state => state.dictionary.itemViews[state.dictionary.itemViews.length - 1]
            })
        },

        methods: {
        }
    }
</script>