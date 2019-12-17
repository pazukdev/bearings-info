<template>
    <div>
        <table>
            <tbody>
            <tr>
                <td style="width: 50%">
                    <button v-if="editMode"
                            type="button"
                            @click="cancel()">
                        {{$t("cancel")}}
                    </button>
                </td>
                <td style="width: 50%; text-align: right">
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
    import itemViewUtil from "../itemViewUtil";

    export default {
        name: "EditPanel",

        props: {
            editMode: Boolean
        },

        methods: {
            cancel() {
                // this.$emit("cancel");
                this.$router.go();
            },

            edit() {
                this.$emit("edit", false);
            },

            save() {
                itemViewUtil.setLoadingStateTrue(this.$store);
                this.$emit("save");
            }
        }
    }
</script>

<style scoped>

</style>