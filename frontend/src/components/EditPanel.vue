<template>
    <div>
        <table>
            <tbody>
            <tr>
                <td>
                    <button v-if="editMode"
                            type="button"
                            @click="cancel()">
                        {{$t("cancel")}}
                    </button>
                </td>
                <td style="text-align: right">
                    <button v-if="!editMode"
                            type="button"
                            @click="edit()">
                        {{$t("edit")}}
                    </button>
                    <button v-if="editMode"
                            type="button"
                            @click="save()">
                        {{$t("save")}}
                    </button>
                </td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "EditPanel",

        data() {
            return {
                editMode: false
            }
        },

        methods: {
            cancel() {
                this.$emit("cancel");
            },

            edit() {
                this.editMode = true;
                this.$emit("edit", this.editMode);
            },

            save() {
                this.$emit("save");
            }
        }
    }
</script>

<style scoped>

</style>