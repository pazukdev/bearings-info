<template>
    <div>
        <OrderedList :name="name" :list="list"/>
    </div>
</template>

<script>
    import OrderedList from "../element/list/OrderedList";

    export default {
        name: "PrivacyPolicy",

        components: {OrderedList},

        data() {
            return {
                name: "Privacy policy",
                list: [
                    "We do not collect any user information implicitly",
                    "We do not transfer account information to third parties unless required by the law"
                ]
            }
        }
    }
</script>

<style scoped>

</style>