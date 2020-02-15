<template>
    <div>
        <div class="default-margin" style="text-align: center"
             v-if="isAdminMessageRendered(itemView.adminMessage)">
            <p v-if="!isEmpty(itemView.adminMessage.localizedText)">
                {{itemView.adminMessage.localizedText}}
            </p>
            <p v-else>
                {{itemView.adminMessage.text}}
            </p>
            <a class="simple-link" v-if="!isEmpty(itemView.adminMessage.link)"
               :href="itemView.adminMessage.link">{{getLinkText(itemView.adminMessage)}}</a>
        </div>
        <details class="default-margin">
            <summary>{{translate("About the app")}}</summary>
            <div class="default-margin">
                {{translate(aboutTheApp())}}
                <hr>
            </div>
        </details>
        <Info/>
        <NewsSection/>
        <details class="default-margin" style="text-align: center">
            <summary>{{translate("You can help the app")}}</summary>
            <br>
            <iframe id="yandex-donate-form" src="https://money.yandex.ru/quickpay/shop-widget?writer=seller&targets=%D0%94%D0%BE%D0%BD%D0%B0%D1%82%20%D0%BD%D0%B0%20%D0%BF%D0%BE%D0%B4%D0%B4%D0%B5%D1%80%D0%B6%D0%BA%D1%83%20%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F&targets-hint=&default-sum=&button-text=14&payment-type-choice=on&comment=on&hint=&successURL=https%3A%2F%2Fpazukdev.github.io%2Fsovietboxers%2F%23%2Fru&quickpay=shop&account=4100111880881391" width="423" height="301" frameborder="0" allowtransparency="true" scrolling="no"></iframe>
        </details>
        <LoadingScreen v-if="isLoading()"/>
        <MotorcycleCatalogue v-else/>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import MotorcycleCatalogue from "../list/MotorcycleCatalogue";
    import LoadingScreen from "../special/LoadingScreen";
    import itemViewUtil from "../../util/itemViewUtil";
    import DefaultButton from "../element/button/DefaultButton";
    import NewsSection from "../info/NewsSection";
    import shared from "../../util/shared";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import Info from "../info/Info";

    export default {
        name: "Home",

        components: {Info, NewsSection, DefaultButton, LoadingScreen, MotorcycleCatalogue},

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
                        itemViewUtil.dispatchView(response.data);
                        console.log("home displayed");
                    })
                    .catch(error => {
                        itemViewUtil.dispatchResponseError(error);
                    });
            },

            logEvent(event, itemId, name) {
                console.log(event + ": " + "id=" + itemId + "; name=" + name);
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            },

            isLoading() {
                return shared.isLoading(this.loadingState);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            aboutTheApp() {
                return "Information about seals, bearings and other parts of soviet boxers and some other old or "
                    + "rare vehicles";
            },

            isEmpty(value) {
                return shared.isEmpty(value);
            },

            isAdminMessageRendered(message) {
                return !this.isEmpty(message)
                    && (!this.isEmpty(message.text) || !this.isEmpty(message.localizedText));
            }
        }
    }
</script>

<style scoped>

</style>