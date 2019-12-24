<template>
    <div>
        <LoadingScreen v-if="this.loadingState"/>
        <div v-else>
            <MotorcycleCatalogue/>

            <details open>
                <summary id="menu-summary">{{$t("menu")}}</summary>
                <Menu :admin="true" :basic-url="basicUrl"/>
            </details>

            <div id="place-of-creation">
                {{"Minsk 2019"}}
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import Menu from "../menu/Menu";
    import MotorcycleCatalogue from "../list/MotorcycleCatalogue";
    import LoadingScreen from "../special/LoadingScreen";
    import itemViewUtil from "../../util/itemViewUtil";

    export default {
        name: "Home",

        components: {
            LoadingScreen,
            MotorcycleCatalogue,
            Menu
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                appLanguage: state => state.dictionary.appLanguage,
                itemView: state => state.dictionary.itemView,
                loadingState: state => state.dictionary.loadingState
            })
        },

        data() {
            return {
                admin: false
            }
        },

        created() {
            this.onUrlChange();
        },

        watch: {
            '$route': 'onUrlChange'
        },

        methods: {
            onUrlChange() {
                this.getView();
            },

            getView() {
                axios
                    .get(this.basicUrl
                        + "/" + "item/view"
                        + "/" + "home"
                        + "/" + this.userName
                        + "/" + this.appLanguage.toString(), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let itemView = response.data;
                        itemViewUtil.dispatchView(this.$store, itemView);
                        console.log("home displayed");
                    });
            },

            logEvent(event, itemId, name) {
                console.log(event + ": " + "id=" + itemId + "; name=" + name);
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            }
        }
    }
</script>

<style scoped>

</style>