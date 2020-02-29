<template>
    <div>
        <EditableImg/>
        <div>
            <EditPanel v-if="editable || isEnglish(appLanguage)" :item-form="item" @save="save"/>
            <div v-else class="default-margin">
                <p class="bordered">{{translate("Editing is available in English only")}}</p>
            </div>
        </div>
        <details v-if="itemView.header != null" class="default-margin" open>
            <summary>{{translate("Specification")}}</summary>
            <ItemDescription :item="item"/>
            <hr>
        </details>
    </div>
</template>

<script>
    import EditableImg from "../../EditableImg";
    import ItemDescription from "./ItemDescription";
    import EditPanel from "../../menu/EditPanel";
    import {mapState} from "vuex";
    import shared from "../../../util/shared";
    import axiosUtil from "../../../util/axiosUtil";
    import dictionaryUtil from "../../../util/dictionaryUtil";

    export default {
        name: "Header",
        components: {EditPanel, ItemDescription, EditableImg},

        props: {
            item: Boolean,
            simpleHeader: Boolean,
            editable: Boolean
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
                let basicUrl = this.basicUrl;
                let userName = this.userName;
                let appLanguage = this.appLanguage;
                let authorization = this.authorization;
                axiosUtil.updateItem(itemId, itemView, basicUrl, userName, appLanguage, authorization);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            isEnglish(lang) {
                return shared.isEnglish(lang);
            }
        }
    }
</script>

<style scoped>
</style>