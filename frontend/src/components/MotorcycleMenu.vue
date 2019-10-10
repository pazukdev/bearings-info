<template>
    <div>

        <Item @select-item="selectItem"/>

        <table style="text-align: center">
            <tbody>
            <tr>
                <td>
                    <button class="content"
                            type="button"
                            style="width: 174px"
                            v-on:click="openItemsManagement()">
                        {{"Items management"}}
                    </button>
                </td>
            </tr>
            <tr v-if="admin">
                <td>
                    <button class="content"
                            type="button"
                            style="width: 174px"
                            v-on:click="openUsersList()">
                        {{"Users"}}
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import {mapState} from 'vuex';
    import Item from "./Item";

    export default {
        components: {
            Item
        },

        computed: {
            ...mapState({
                userName: state => state.dictionary.userName,
                admin: state => state.dictionary.admin,
                itemView: state => state.dictionary.itemViews[state.dictionary.itemViews.length - 1]
            })
        },

        methods: {

            selectItem(id) {
                this.$emit('select-item', id)
            },

            openSpecialView(specialItemId) {
                this.$store.dispatch("addItemId", specialItemId);
                this.selectItem(specialItemId);
            },

            openItemsManagement() {
                let itemsManagementId = -1;
                this.openSpecialView(itemsManagementId);

            },

            openUsersList() {
                let usersListId = -4;
                this.openSpecialView(usersListId);
            }

        }
    }
</script>