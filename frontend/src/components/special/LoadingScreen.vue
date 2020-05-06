<template>
    <div class="default-margin">
        <div style="text-align: center">
            <br>
            <br>
            <img alt="loading spinner" src="../../assets/ajax-loader.gif">
            <br>
            <br>
            <p>{{translate(loadingState) + "..."}}</p>
        </div>
        <div v-if="!isEmpty(errorMessage)" style="text-align: center">
            <br>
            <br>
            <p>{{translate(errorMessage)}}</p>
            <p v-if="noServerResponse(errorMessage)">
                {{translate('If you still see message above')}}
            </p>
            <p v-if="noServerResponse(errorMessage)">
                {{translate('try to refresh the page or check your internet connection')}}
            </p>
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import shared from "../../util/shared";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import view from "../../mixin/view";

    export default {
        name: "LoadingScreen",

        mixins: [view],

        computed: {
            ...mapState({
                loadingState: state => state.dictionary.loadingState,
                errorMessage: state => state.dictionary.errorMessage
            })
        },

        methods: {
            isEmpty(errorMessage) {
                return shared.isEmpty(errorMessage);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            noServerResponse(errorMessage) {
                return !this.isEmpty(errorMessage ) && this.errorMessage.toString() === 'No server response';
            }
        }
    }
</script>

<style scoped>

</style>