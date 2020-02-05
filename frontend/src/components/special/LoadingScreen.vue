<template>
    <div>
        <div v-if="!isEmpty(errorMessage)" class="alert-message" style="text-align: center">
            {{errorMessage}}
        </div>
        <div v-else class="default-margin">
        {{loadingState + "..."}}
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import shared from "../../util/shared";

    export default {
        name: "LoadingScreen",

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView,
                loadingState: state => state.dictionary.loadingState,
                errorMessage: state => state.dictionary.errorMessage
            })
        },

        methods: {
            isEmpty(errorMessage) {
                return shared.isEmpty(errorMessage);
            }
        }
    }
</script>

<style scoped>

</style>