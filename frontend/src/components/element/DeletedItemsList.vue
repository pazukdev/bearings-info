<template>
    <div>
        <table v-if="!isEmpty(array) && array.length > 0" style="text-align: left">
            <tbody>
            <tr v-for="(item, index) in array">
                <td>{{index + 1}}</td>
                <td>{{getText(item)}}</td>
                <td>{{translate("deleted")}}</td>
                <td>
                    <button type="button" @click="$emit('restore', item)">
                        {{"Restore"}}
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import basicComponent from "../../mixin/basicComponent";
    import shared from "../../util/shared";

    export default {
        name: "DeletedItemsList",

        mixins: [basicComponent],

        props: {
            array : Array,
            row: Boolean,
            buyLink: Boolean
        },

        methods: {
            getText(item) {
                if (this.buyLink) {
                    return this.translate(this.getCountryName(item.countryCode));
                }
                if (this.row) {
                    return item.parameter + " " + this.translate("param");
                }
                return item.buttonText;
            },

            getCountryName(alpha2Code) {
                return shared.getCountryName(alpha2Code);
            }
        }
    }
</script>

<style scoped>

</style>