<template>
    <div>
        <EditableImg/>
        <EditPanel :item-form="true"/>
        <br>
        <div style="text-align: center">
            <p v-if="!isEmpty(itemView.localizedCategory)"><b>{{itemView.localizedCategory}}</b></p>
            <p v-if="!isEmpty(itemView.localizedName)"><b>{{itemView.localizedName}}</b></p>
            <p v-if="!isEmpty(itemView.creatorName)">
                {{$t("createdBy")}}
                <router-link class="simple-link"
                             v-if="item" :to="{name: 'user', params: {id: itemView.creatorId, lang: appLanguage}}">
                    {{itemView.creatorName}}
                </router-link>
            </p>
        </div>
        <details v-if="itemView.header != null" class="default-margin" open>
            <summary>{{"Specification"}}</summary>
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

    export default {
        name: "Header",
        components: {EditPanel, ItemDescription, EditableImg},

        props: {
            item: Boolean,
            simpleHeader: Boolean
        },

        computed: {
            ...mapState({
                // basicUrl: state => state.dictionary.basicUrl,
                // authorization: state => state.dictionary.authorization,
                // userName: state => state.dictionary.userName,
                itemView: state => state.dictionary.itemView,
                // editMode: state => state.dictionary.editMode,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            isEmpty(value) {
                return shared.isEmpty(value);
            }
        }
    }
</script>

<style scoped>
</style>