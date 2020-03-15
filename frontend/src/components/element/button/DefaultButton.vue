<template>
    <div>
        <button type="button" @click="onClick" :style="myStyle">
            {{text.split("=")[0]}}<br>{{text.split("=")[1]}}
        </button>
    </div>
</template>

<script>
    export default {
        name: "DefaultButton",

        props: {
            text: String,
            color: String
        },

        data() {
            return {
                myStyle: {
                    backgroundColor: this.color
                }
            }
        },

        methods: {
            onClick() {
                this.$emit("on-click");
            },

            // setColor() {
            //     console.log(this.color)
            //     if (this.color !== undefined) {
            //         console.log(this.color)
            //         document.getElementsByTagName("button").style.background = this.color;
            //     }
            // }
        }
    }
</script>

<style scoped>

</style>