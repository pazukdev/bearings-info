<template>
    <div>
        <Item @select-item="selectItem"/>
    </div>
</template>

<script>
    import Item from "./Item";

    export default {
        components: {
            Item
        },

        methods: {
            selectItem(id) {
                this.$emit('select-item', id)
            }
        }
    }
</script>