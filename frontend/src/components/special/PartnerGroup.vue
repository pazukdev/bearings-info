<template>
    <div style="text-align: center" class="default-margin">
        <p style="text-align: left; margin-top: 10px; margin-bottom: 4px">
            {{translate("Thanks to these guys")}}
        </p>
        <div style="display: inline-block" v-for="logo in logos">
            <PartnerLogo v-if="!isEmpty(logo.toString().split(';')[0]) && condition === logo.toString().split(';')[3]"
                         :img-url="logo.toString().split(';')[0]"
                         :url="logo.toString().split(';')[1]"
                         :text="translate(logo.toString().split(';')[2])" style="margin: 6px"/>
        </div>
        <div style="display: inline-block; width: 110px; margin: 6px">
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
        condition: String
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

<style scoped>

</style>