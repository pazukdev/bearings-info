<template>
    <div>
        <UnsortedList :name="name" :list="list"/>
    </div>
</template>

<script>
    import UnsortedList from "../element/list/UnsortedList";

    export default {
        name: "Disclaimer",
        components: {UnsortedList},
        data() {
            return {
                name: "Disclaimer",
                list: [
                    "The app is not a mass media",
                    "Developer and administration the app are not responsible for information posted by app users"
                ]
            }
        }
    }
</script>

<style scoped>

</style>