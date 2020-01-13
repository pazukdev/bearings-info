<template>
    <div>
        <EditableImg/>
        <ItemDescription :item="item"/>
        <EditPanel @save="save"/>
    </div>
</template>

<script>
    import EditableImg from "../../EditableImg";
    import ItemDescription from "./ItemDescription";
    import EditPanel from "../../menu/EditPanel";
    import {mapState} from "vuex";
    import axiosUtil from "../../../util/axiosUtil";

    export default {
        name: "Header",
        components: {EditPanel, ItemDescription, EditableImg},

        props: {
            item: Boolean
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            save() {
                this.update(this.itemView.itemId);
            },

            update(itemId) {
                let itemView = this.itemView;
                let basicUrl = this.basicUrl.toString();
                let userName = this.userName.toString();
                let appLanguage = this.appLanguage.toString();
                let authorization = this.authorization;
                axiosUtil.updateItem(itemId, itemView, basicUrl, userName, appLanguage, authorization);
            }
        }
    }
</script>

<style scoped>

</style>