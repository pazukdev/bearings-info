<template>
    <div class="default-margin">
        <details>
            <summary>{{translate("Info")}}</summary>
            <div class="default-margin">
                <router-link :to="{name: 'user_agreement', params: {lang: $route.params.lang}}" class="simple-link">
                    {{translate("User agreement")}}
                </router-link>
                <br>
                <router-link :to="{name: 'privacy_policy', params: {lang: $route.params.lang}}" class="simple-link">
                    {{translate("Privacy policy")}}
                </router-link>
                <br>
                <router-link :to="{name: 'disclaimer', params: {lang: $route.params.lang}}" class="simple-link">
                    {{translate("Disclaimer")}}
                </router-link>
            </div>
        </details>
    </div>
</template>

<script>
    import dictionaryUtil from "../../util/dictionaryUtil";

    export default {
        name: "Info",

        methods: {
            translate(text) {
                return dictionaryUtil.translate(text);
            },
        }
    }
</script>

<style scoped>

</style>