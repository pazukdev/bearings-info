<template>
    <div id="place-of-creation">
        <div class="default-margin">
            {{translate('App groups on FB and VK')}}<br>
            {{translate("There is lots of interesting here")}}
            <div id="communities-links-section">
                <span>
                    <a target="_blank"
                       href="https://www.facebook.com/groups/app.old.vehicles"
                       :title="translate('Facebook group')">
                        <img src="../assets/fb.png" alt="fb logo"/>
                    </a>
                    <a target="_blank"
                       href="https://vk.com/old.vehicles"
                       :title="translate('vk community')">
                        <img src="../assets/vk.png" alt="vk logo"/>
                    </a>
                </span>
            </div>
            <p style="height: 40px" class="bottom-border" v-if="!hideCopyright">
                {{"© 2017-2020 " + translate("Old Vehicles: Seals & Bearings")}}
            </p>
            <div v-if="beerGlassRendered">
                <br>
                <DonationSection/>
                <br>
            </div>
        </div>

        <div class="default-margin" style="text-align: left">
            {{translate("If the app is helpful to you, please, don't forget to rate the app and leave a review.")}}
            {{translate("It's very important for the project")}}
        </div>
        <GooglePlayLink/>
    </div>
</template>

<script>
import DonationSection from "./DonationSection";
import dictionaryUtil from "../util/dictionaryUtil";
import GooglePlayLink from "./element/GooglePlayLink";

export default {
        name: "AppGroupsSection",

        components: {GooglePlayLink, DonationSection},

        props: {
            beerGlassRendered: Boolean,
            hideCopyright: Boolean
        },

        methods: {
            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>
    #place-of-creation {
        text-align: center;
        margin-top: 60px;
        margin-bottom: 20px;
    }

    #communities-links-section img {
        height:60px;
        text-align: center;
    }

    #communities-links-section span, a {
        display: inline-block;
        margin: 10px;
        vertical-align: middle;
    }
</style>