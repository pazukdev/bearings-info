<template>
    <div v-if="messages.length > 0">
        <ul v-for="message in messages">
            <li class="alert-message">{{translate(message)}}</li>
        </ul>
    </div>
</template>

<script>
    import dictionaryUtil from "../util/dictionaryUtil";

    export default {
        name: "AlertMessagesSection",

        props: {
            messages: Array
        },

        methods: {
            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>
    div {
        text-align: left;
    }
</style>