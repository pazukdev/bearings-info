<template>
    <div>
        <div class="default-margin">
            <p style="font-weight: bold">
                {{translate("For our supporters")}}
            </p>
            <p>
                {{translate("If you have your own project (website, app, blog, FB or VK group, YouTube channel) - let us know. Email us or contact us on FB or VK. We'll place your logo with a link to your project in our app.")}}
            </p>
            <br>
            <p style="font-weight: bold">
                {{translate("How long will your link be displayed")}}
            </p>
            <p>
                {{translate("Of course, we cannot leave all links permanently. The list will become huge and meaningless.")}}
                <br>
                {{translate("Display time corresponds to donation: 0.5 EUR per 1 day. But it isn't a strict condition. Probably your link will stay visible longer.  We don't sell ad space. It's just a way to thank you for your help. As well as our little help for your project.")}}
            </p>
        </div>
    </div>
</template>

<script>
import dictionaryUtil from "@/util/dictionaryUtil";

export default {
    name: "InfoForSupporters",
    methods: {
        translate(text) {
            return dictionaryUtil.translate(text);
        }
    }
}
</script>

<style scoped>

</style>