<template>
    <div>
        <EditableImg/>
        <EditPanel v-if="editable || appLanguage.toString() === 'en'" :item-form="item" @save="save"/>
        <br>
        <div style="text-align: center">
            <p v-if="!isEmpty(itemView.localizedCategory)"><b>{{itemView.localizedCategory}}</b></p>
            <p v-if="!isEmpty(itemView.localizedName)"><b>{{itemView.localizedName}}</b></p>
            <p v-if="item">
                {{translate("Created by")}}
                <router-link class="simple-link"
                             v-if="showCreatorLink()"
                             :to="{name: 'user', params: {id: itemView.creatorData.id, lang: appLanguage}}">
                    {{itemView.creatorData.name}}
                </router-link>
                <span v-else>{{translate("deleted user")}}</span>
            </p>
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
    import itemViewUtil from "../../../util/itemViewUtil";
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
            isEmpty(value) {
                return shared.isEmpty(value);
            },

            showCreatorLink() {
                if (this.isEmpty(this.itemView.creatorData)) {
                    return false;
                }
                if (this.itemView.creatorData.status !== 'active') {
                    return this.isAdmin();
                }
                return true;
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            },

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
            }
        }
    }
</script>

<style scoped>
</style>