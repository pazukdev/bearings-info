<template>
    <div>
        <br>
        <p style="text-align: center; font-weight: bold">{{translate(name)}}</p>
        <br>
        <ol>
            <li v-for="element in list">
                {{translate(element)}}
            </li>
        </ol>
    </div>
</template>

<script>
    import dictionaryUtil from "../../../util/dictionaryUtil";

    export default {
        name: "OrderedList",

        props: {
            name:String,
            list:Array
        },

        methods: {
            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>

</style>