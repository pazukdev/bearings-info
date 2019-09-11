<template>
    <div>
        {{text}}
        {{isEditMode}}
        {{newItemView}}
        {{itemView}}
<!--        <table>-->
<!--            <tbody>-->
<!--            <tr>-->
<!--                <td colspan="2" style="text-align: center"><b>{{itemView.header.name}}</b></td>-->
<!--            </tr>-->
<!--            <tr style="text-align: left" v-for="row in itemView.header.matrix">-->
<!--                <td style="width: 50%">-->
<!--                    <input v-model="row[0]" type="text"/>-->
<!--                </td>-->
<!--                <td>-->
<!--                    <input v-model="row[1]" type="text"/>-->
<!--                </td>-->
<!--            </tr>-->
<!--            <tr v-if="itemView.selectableData.name !== 'stub'"-->
<!--                style="text-align: left"-->
<!--                v-for="row in itemView.selectableData.matrix">-->
<!--                <td style="width: 50%">-->
<!--                    {{row[0]}}-->
<!--                </td>-->
<!--                <td>-->
<!--                    <p v-if="row[2] === 'no id'">{{row[1]}}</p>-->
<!--                    <button v-if="row[2] !== 'no id'" type="button"-->
<!--                            style="width: 100%"-->
<!--                            @click="setItem(row[2])">-->
<!--                        {{row[1]}}-->
<!--                    </button>-->
<!--                </td>-->
<!--            </tr>-->
<!--            <tr style="text-align: left">-->
<!--                <td style="width: 50%"></td>-->
<!--                <td>-->
<!--                    <button v-if="!isEditMode"-->
<!--                            type="button"-->
<!--                            style="width: 100%"-->
<!--                            @click="edit()">-->
<!--                        {{"Edit"}}-->
<!--                    </button>-->
<!--                    <button v-if="isEditMode"-->
<!--                            type="button"-->
<!--                            style="width: 100%"-->
<!--                            @click="save()">-->
<!--                        {{"Save"}}-->
<!--                    </button>-->
<!--                </td>-->
<!--            </tr>-->
<!--            <tr v-for="item in itemView.items.tables">-->
<!--                <td colspan="2">-->
<!--                    <table class="get-all-table">-->
<!--                        <tbody>-->
<!--                        <tr>-->
<!--                            <td style="width: 120px">-->
<!--                                <b>{{item.name}}</b>-->
<!--                            </td>-->
<!--                            <td></td>-->
<!--                            <td style="width: 80px"></td>-->
<!--                        </tr>-->
<!--                        <tr v-for="row in item.matrix">-->
<!--                            <td>-->
<!--                                {{row[0]}}-->
<!--                            </td>-->
<!--                            <td>-->
<!--                                <button type="button"-->
<!--                                        style="width: 80%"-->
<!--                                        @click="setItem(row[3])">-->
<!--                                    {{row[1]}}-->
<!--                                </button>-->
<!--                            </td>-->
<!--                            <td>-->
<!--                                {{row[2]}}-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        <tr>-->
<!--                            <p></p>-->
<!--                        </tr>-->
<!--                        </tbody>-->
<!--                    </table>-->
<!--                </td>-->
<!--            </tr>-->
<!--            <tr v-if="notStub()">-->
<!--                <td style="text-align: center" colspan="2">-->
<!--                    {{itemView.replacers.name}}-->
<!--                </td>-->
<!--            </tr>-->
<!--            <tr v-if="notStub()" style="text-align: left" v-for="row in itemView.replacers.matrix">-->
<!--                <td>-->
<!--                    {{row[0]}}-->
<!--                </td>-->
<!--                <td>-->
<!--                    <button type="button"-->
<!--                            style="width: 100%"-->
<!--                            @click="setItem(row[2])">-->
<!--                        {{row[1]}}-->
<!--                    </button>-->
<!--                </td>-->
<!--            </tr>-->
<!--            </tbody>-->
<!--        </table>-->
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';

    export default {

        data() {
            return {
                isEditMode: false,
                newItemView: "",
                text: "start"
            }
        },

        created() {
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                itemView: state => state.dictionary.itemViews[state.dictionary.itemViews.length - 1],
            })
        },

        methods: {
            setItem(id) {
                this.$emit('select-item', id);
            },

            edit() {
                this.isEditMode = true;
                this.newItemView = this.itemView;
            },

            save() {
                let id = this.newItemView.item.id;
                this.update(id)

            },

            update(id) {
                axios
                    .put("/backend/item/" + id, this.newItemView, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(() => {
                        this.$store.dispatch("removeLastItemView");
                        this.$store.dispatch("removeLastItemId");
                        this.setItem(id);
                        this.isEditMode = false;
                    });
            },

            notStub() {
                return this.itemView.replacers.name !== "stub";
            }
        }
    }
</script>