<template>
    <div v-if="messages.length > 0">
        <ul v-for="message in messages">
            <li class="alert-message">{{message}}</li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "AlertMessagesSection",

        props: {
            messages: Array
        }
    }
</script>

<style scoped>
    div {
        text-align: left;
    }
</style>