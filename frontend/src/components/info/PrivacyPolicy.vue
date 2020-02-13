<template>
    <div>
        <UnsortedList :name="name" :list="list"/>
    </div>
</template>

<script>
    import UnsortedList from "../element/list/UnsortedList";

    export default {
        name: "PrivacyPolicy",

        components: {UnsortedList},

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