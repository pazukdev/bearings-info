<template>
    <div>
        <PartList :show-form="showForm" @show-add-form="showAddForm" @hide-add-form="hideAddForm"/>
    </div>
</template>

<script>
    import PartList from "../list/PartList";
    import {mapState} from "vuex";

    export default {
        name: "PartsSection",
        components: {PartList},
        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            })
        },

        data() {
            return {
                showForm: true
            }
        },

        methods: {
            showAddForm() {
                this.showForm = true;
            },

            hideAddForm() {
                this.showForm = false;
            }
        }
    }
</script>

<style scoped>

</style>