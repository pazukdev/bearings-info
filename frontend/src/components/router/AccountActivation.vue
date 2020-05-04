<template>
    <div>
        <br>
        <br>
        <br>
        <br>
        <br>
        <div v-if="activated" style="text-align: center" class="default-margin">
            {{translate("Email verification completed")}}<br>
            {{translate("Your account activated")}}<br>
            <br>
            <router-link :to="{name: 'home', params: {lang: lang}}" class="button">
                {{"Ok" + ", " + translate("navigate to home page")}}
            </router-link>
        </div>
        <div v-else style="text-align: center">
            {{translate("Check your email")}}<br>
            <br>
            {{userData.email}}<br>
            <br>
            {{translate("If email didn't arrive in a few minutes check spam folder")}}<br>
            {{translate("If there is no email in the spam folder")}}
            {{", "}}
            {{translate("please")}}<br>
            {{" "}}
            {{translate("contact me")}}<br>
            <br>
            {{"old.vehicles.app@gmail.com"}}
            <AppGroupsSection :beer-glass-rendered="false" :hide-copyright="true"/>

<!--            {{translate("Something went wrong") + "..."}}-->
        </div>
    </div>
</template>

<script>
    import basicComponent from "../../mixin/basicComponent";
    import axios from "axios";
    import view from "../../mixin/view";
    import shared from "../../util/shared";
    import axiosUtil from "../../util/axiosUtil";
    import AppGroupsSection from "../AppGroupsSection";

    export default {
        name: "AccountActivation",
        components: {AppGroupsSection},
        mixins: [basicComponent, view],

        data() {
            return {
                activated: false
            }
        },

        created() {
            let userId = this.$route.params.id;
            if (!shared.isEmpty(userId)) {
                this.activateUser(userId);
            }
        },

        methods: {
            activateUser(userId) {
                // /user/id/{id}/activate
                axios
                    .post(axiosUtil.getBasicUrl()
                        + "/" + "user"
                        + "/" + "id"
                        + "/" + userId
                        + "/" + "activate", {
                            headers: {
                                Authorization: axiosUtil.getAuthorization()
                            }
                        })
                    .then(response => {
                        console.log(response.status);
                        if (response.status === 200) {
                            this.activated = true;
                            console.log("user id=" + userId + " activated");
                            this.userData.id = userId;
                            this.userData.status = response.data;
                        }
                    });
            },
        }
    }
</script>

<style scoped>

</style>