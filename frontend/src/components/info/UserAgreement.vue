<template>
    <div>
        <OrderedList :name="name" :list="list"/>
    </div>
</template>
<script>
    import OrderedList from "../element/list/OrderedList";

    export default {
        name: "UserAgreement",
        components: {OrderedList},
        data() {
            return {
                name: "User agreement",
                list: [
                    "By using this app you agree to the terms below",
                    "It's prohibited to post information whose placement violates international and national laws",
                    "It's prohibited to post information irrelevant to the app",
                    "Responsibility for the information posted lies with the person who posted it",
                    // "The app is free",
                    "The app is not a web-store",
                    // "The app do not contain any in-app purchases",
                    // "The app do not accept or process any payments",
                ]
            }
        }
    }
</script>

<style scoped>

</style>