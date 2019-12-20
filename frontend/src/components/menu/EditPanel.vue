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
    import storeUtil from "../../util/storeUtil";
    import {mapState} from "vuex";

    export default {
        name: "EditPanel",

        computed: {
            ...mapState({
                userName: state => state.dictionary.userName,
                editMode: state => state.dictionary.editMode
            })
        },

        methods: {
            cancel() {
                storeUtil.setEditMode(this.$store, false);
                this.$router.go();
            },

            edit() {
                storeUtil.setEditMode(this.$store, true);
            },

            save() {
                storeUtil.setLoadingState(this.$store, true);
                storeUtil.setEditMode(this.$store, false);
                this.$emit("save");
            }
        }
    }
</script>

<style scoped>

</style>