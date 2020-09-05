<template>
    <div style="text-align: center" class="default-margin"
         v-if="isConditionPresented(logos, condition)">
        <div v-if="condition !== 'top'" style="border-top: solid gray 2px"/>
        <p style="text-align: left; margin-top: 10px; margin-bottom: 4px">
            {{translate(title)}}
        </p>
        <div style="display: inline-block" v-for="logo in logos">
            <PartnerLogo class="logo-margin"
                         v-if="!isEmpty(logo.toString().split(';')[0]) && condition === getCondition(logo)"
                         :img-url="logo.toString().split(';')[0]"
                         :url="logo.toString().split(';')[1]"
                         :text="translate(logo.toString().split(';')[2])"/>
        </div>
        <div style="display: inline-block" class="logo-margin, logo-width"
             v-if="condition !== 'special'">
            <div class="bordered">
                <router-link class="simple-link"
                             :to="{name: 'for_supporters', params: {lang: lang}}">
                    {{translate("Place your logo")}}
                </router-link>
            </div>
            <p>{{translate("Support us")}}</p>
        </div>
    </div>
</template>

<script>
import PartnerLogo from "@/components/special/PartnerLogo";
import dictionaryUtil from "@/util/dictionaryUtil";
import axios from "axios";
import axiosUtil from "@/util/axiosUtil";
import shared from "@/util/shared";
import {mapState} from "vuex";

export default {
    name: "PartnerGroup",
    components: {PartnerLogo},
    props: {
        condition: String,
        title: String
    },
    computed: {
        ...mapState({
            lang: state => state.dictionary.lang
        })
    },
    data() {
        return {
            logos: ["https://www.sovietboxers.com/img/soviet_boxers_280x280.9f0238d8.png", "https://www.sovietboxers.com", "Our website"]
        }
    },

    created() {
        this.getPartnersData();
    },

    methods: {
        translate(text) {
            return dictionaryUtil.translate(text);
        },

        isEmpty(value) {
            return shared.isEmpty(value);
        },

        getCondition(logo) {
            return this.getParam(logo, 3);
        },

        isConditionPresented(logos, condition) {
            for (let logo of logos) {
                if (this.getCondition(logo) === condition) {
                    return true;
                }
            }
            return false;
        },

        getParam(logo, paramIndex) {
            return logo.toString().split(';')[paramIndex];
        },

        getPartnersData() {
            let googleDocId = "1EzWgY_aflqa61zXESE6Uu1tUYZB1ARh16p0NPuxIXzU";
            // /google-doc/get/text/{id}
            axios
                .get(axiosUtil.getBasicUrl() + "/google-doc/get/text/" + googleDocId)
                .then(response => {
                    this.logos = response.data;
                });
        }
    }
}
</script>

<style>
.logo-max-size {
    max-width: 100px;
    max-height: 60px;
}

.logo-width {
    width: 100px;
}

.logo-margin {
    margin: 6px;
}
</style>