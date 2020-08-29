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
            <p class="red">{{translate(errorMessage)}}</p>
            <p v-if="noServerResponse(errorMessage)">
                {{translate('If you see message above')}}
            </p>
            <p v-if="noServerResponse(errorMessage)">
                {{translate('try to refresh the page or check your internet connection')}}
            </p>
        </div>
    </div>
</template>

<script>
import {mapState} from "vuex";
import view from "../../mixin/view";
import basicComponent from "@/mixin/basicComponent";

export default {
        name: "LoadingScreen",
        mixins: [basicComponent, view],
        computed: {
            ...mapState({
                errorMessage: state => state.dictionary.errorMessage
            })
        },
        methods: {
            noServerResponse(errorMessage) {
                return !this.isEmpty(errorMessage ) && this.errorMessage.toString() === 'No server response';
            }
        }
    }
</script>

<style scoped></style>