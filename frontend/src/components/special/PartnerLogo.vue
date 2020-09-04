<template>
    <div style="text-align: center">
        <a target="_blank" :href="url">
            <img :src="imgUrl" alt="logo" style="max-width: 110px; max-height: 60px"/>
        </a>
        <br>
<!--        <a target="_blank" :href="url" class="simple-link">-->
<!--            {{getWebsiteAddress(url)}}-->
<!--        </a>-->
        <p>{{getWebsiteAddress(url)}}</p>
        <p>{{text}}</p>
    </div>

</template>

<script>
import shared from "@/util/shared";

export default {
    name: "PartnerLogo",
    props: {
        imgUrl: String,
        url: String,
        text: String
    },
    methods: {
        getWebsiteAddress(url) {
            if (!shared.isEmpty(url)) {
                return shared.getWebsiteAddress(url);
            }
            return url;
            // return shared.getWebsiteAddress(url);
        }
    }
}
</script>

<style scoped>

</style>