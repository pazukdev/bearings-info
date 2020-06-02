<template>
    <div>
        <OrderedList :ordered="true" :translate-text="true" :name="name" :list="list"/>
    </div>
</template>

<script>
    import OrderedList from "../element/list/OrderedList";

    export default {
        name: "Disclaimer",
        components: {OrderedList},
        data() {
            return {
                name: "Disclaimer",
                list: [
                    "The app is not a mass media",
                    "The app developer isn't responsible for information posted by app users"
                ]
            }
        }
    }
</script>

<style scoped>

</style>