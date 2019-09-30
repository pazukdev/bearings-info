<template>
    <div>
        <Item @select-item="selectItem"/>
        <table style="text-align: center">
            <tbody>
            <tr>
                <td>{{"Additional"}}</td>
            </tr>
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
            </tbody>
        </table>
    </div>
</template>

<script>
    import Item from "./Item";

    export default {
        components: {
            Item
        },

        methods: {

            selectItem(id) {
                this.$emit('select-item', id)
            },

            openItemsManagement() {
                let itemsManagementSpecialId = -1;
                this.$store.dispatch("addItemId", itemsManagementSpecialId);
                this.selectItem(itemsManagementSpecialId);
            }

        }
    }
</script>