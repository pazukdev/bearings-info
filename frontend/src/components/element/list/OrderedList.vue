<template>
    <div>
        <br>
        <p v-if="translateText" style="text-align: center; font-weight: bold">
            {{translate(name)}}
        </p>
        <p v-else style="text-align: center; font-weight: bold">
            {{name}}
        </p>
        <br>
        <ol v-if="ordered && translateText">
            <li v-for="element in list">
                {{translate(element)}}
            </li>
        </ol>
        <ol v-if="ordered && !translateText">
            <li v-for="element in list">
                {{element}}
            </li>
        </ol>
        <ul v-if="!ordered && translateText">
            <li v-for="element in list">
                {{translate(element)}}
            </li>
        </ul>
        <ul v-if="!ordered && !translateText">
            <li v-for="element in list">
                {{element}}
            </li>
        </ul>
    </div>
</template>

<script>
    import dictionaryUtil from "../../../util/dictionaryUtil";

    export default {
        name: "OrderedList",

        props: {
            name:String,
            list:Array,
            translateText:Boolean,
            ordered:Boolean
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