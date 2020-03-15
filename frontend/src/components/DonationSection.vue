<template>
    <div class="default-margin">
<!--        <details style="text-align: center">-->
<!--            <summary><b>{{translate("You can help the project")}}</b></summary>-->
<!--            {{"&#x1f37a;"}}-->
<!--            <summary>{{translate("Beer for developer")}}</summary>-->
<!--            <br>-->
<!--            <div>-->
<!--                {{translate("Developer likes beer")}}-->
<!--            </div>-->
<!--            <br>-->
<!--            <details>-->
<!--                <summary style="text-align: center">-->
<!--                    <img src="https://www.sovietboxers.com/img/beer.95fcf001.png"-->
<!--                         alt="beer icon" :title="translate('Pour a glass')">-->
<!--                </summary>-->
<!--                <details class="default-margin">-->
<!--                    <summary>{{translate("#1 Pay Pal donation box")}}</summary>-->
<!--                    <br>-->
<!--                    <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">-->
<!--                        <input type="hidden" name="cmd" value="_donations" />-->
<!--                        <input type="hidden" name="business" value="volha1588@gmail.com" />-->
<!--                        <input type="hidden" name="item_name" value="Donation to help the project" />-->
<!--                        <input type="hidden" name="currency_code" value="USD" />-->
<!--                        <input type="image"-->
<!--                               src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif"-->
<!--                               border="0" name="submit"-->
<!--                               title="Donate with PayPal"-->
<!--                               alt="Donate with PayPal button"/>-->
<!--                        <img alt="" border="0" width="1" height="1"-->
<!--                             src="https://www.paypal.com/en_US/i/scr/pixel.gif"/>-->
<!--                    </form>-->
<!--                </details>-->
<!--                <details class="default-margin" style="text-align: center">-->
<!--                    <summary>{{translate("#2 Yandex donation box")}}</summary>-->
<!--                    <br>-->
<!--                    <iframe id="yandex-donate-form"-->
<!--                            src="https://money.yandex.ru/quickpay/shop-widget?writer=seller&targets=Donation%20to%20help%20the%20project%20%2F%20%D0%94%D0%BE%D0%BD%D0%B0%D1%82%20%D0%BD%D0%B0%20%D0%BF%D0%BE%D0%B4%D0%B4%D0%B5%D1%80%D0%B6%D0%BA%D1%83%20%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82%D0%B0&targets-hint=&default-sum=&button-text=13&payment-type-choice=on&comment=on&hint=&successURL=&quickpay=shop&account=4100111880881391"-->
<!--                            frameborder="0" allowtransparency="true" scrolling="no"/>-->
<!--                </details>-->
<!--            </details>-->
<!--        </details>-->

        <p>{{translate("Beer for developer")}}</p>
        <br>
        <details>
            <summary style="text-align: center">
                <img style="width: 60px" src="https://www.sovietboxers.com/img/beer.95fcf001.png"
                     alt="beer icon" :title="translate('Pour a glass')">
            </summary>
            <details class="default-margin">
                <summary>{{translate("#1 Pay Pal donation box")}}</summary>
                <br>
                <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
                    <input type="hidden" name="cmd" value="_donations" />
                    <input type="hidden" name="business" value="volha1588@gmail.com" />
                    <input type="hidden" name="item_name" value="Donation to help the project" />
                    <input type="hidden" name="currency_code" value="USD" />
                    <input type="image"
                           src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif"
                           border="0" name="submit"
                           title="Donate with PayPal"
                           alt="Donate with PayPal button"/>
                    <img alt="" border="0" width="1" height="1"
                         src="https://www.paypal.com/en_US/i/scr/pixel.gif"/>
                </form>
            </details>
            <details class="default-margin"
                     style="text-align: center; border-bottom: solid grey 1px; padding-bottom: 10px">
                <summary>{{translate("#2 Yandex donation box")}}</summary>
                <br>
                <iframe id="yandex-donate-form"
                        src="https://money.yandex.ru/quickpay/shop-widget?writer=seller&targets=Donation%20to%20help%20the%20project%20%2F%20%D0%94%D0%BE%D0%BD%D0%B0%D1%82%20%D0%BD%D0%B0%20%D0%BF%D0%BE%D0%B4%D0%B4%D0%B5%D1%80%D0%B6%D0%BA%D1%83%20%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82%D0%B0&targets-hint=&default-sum=&button-text=13&payment-type-choice=on&comment=on&hint=&successURL=&quickpay=shop&account=4100111880881391"
                        frameborder="0" allowtransparency="true" scrolling="no"/>
            </details>
        </details>

    </div>
</template>

<script>
    import dictionaryUtil from "../util/dictionaryUtil";

    export default {
        name: "DonationSection",

        methods: {
            translate(text) {
                return dictionaryUtil.translate(text);
            },

            b() {
                return "U+1F37A";
            }
        }
    }
</script>

<style scoped>
    summary {

    }

    input, img {
        background: initial;
        height: initial;
        width: initial;
        max-height: initial;
        max-width: initial;
        border-radius: initial;
        margin: initial;
    }

</style>