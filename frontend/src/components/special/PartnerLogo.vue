<template>
    <div style="text-align: center">
        <a target="_blank" :href="url">
            <img :src="imgUrl" alt="logo" class="logo-max-size"/>
        </a>
        <br>
        <p v-if="!isEmpty(websiteName)">{{websiteName}}</p>
        <p v-else>{{getWebsiteAddress(url)}}</p>
        <p class="logo-width">{{text}}</p>
    </div>

</template>

<script>
import shared from "@/util/shared";

export default {
    name: "PartnerLogo",
    props: {
        imgUrl: String,
        url: String,
        websiteName: String,
        text: String
    },
    methods: {
        getWebsiteAddress(url) {
            if (!this.isEmpty(url)) {
                return shared.getWebsiteAddress(url);
            }
            return url;
            // return shared.getWebsiteAddress(url);
        },

        isEmpty(text) {
            return shared.isEmpty(text);
        }
    }
}
</script>

<style scoped>

</style>