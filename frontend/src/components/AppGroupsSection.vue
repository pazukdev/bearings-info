<template>
    <div id="place-of-creation">
        <div class="default-margin">
            <div id="communities-links-section">
                                    <span>
                                        <a target="_blank"
                                           href="https://www.facebook.com/groups/app.old.vehicles"
                                           :title="translate('Facebook group')">
                                        <img src="data:image/png;base64,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"
                                             alt="fb logo"/>
                                    </a>
                                    <a target="_blank"
                                       href="https://vk.com/old.vehicles"
                                       :title="translate('vk community')">
                                        <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAAAXNSR0IB2cksfwAAAAlwSFlzAAALEwAACxMBAJqcGAAAChpJREFUeJztXV1sVMcVRq14iVShVJGqSpFQVR6al7zkBanyS1+ah3Ga0ipJG9qCmtJUpBEiNUmBAKVxQ6AUFUKC+U2wwQSytjEYOzFOsE3iGIzBNBiwcTA2Nj9r8O/iH2ym57t3Ft/Y+3Pn7tx7Z3+O9CmR1nt25nzMmZlzzszMmKG5sJz8WYSfEOYSFhJyCLsIeYQaQsMUVBD2is/fEt/Bd2cTHvW7P0knZLRHCFnCmNWEVsIQgSeIIKFNELZc/MYjfvdXSyHDzCQ8Rcgl1AnjJUqAHYLqxG/it2f6bQdfRZAAV7LNIwLsELRNtCl9yBEuaYnw/2MaEDEVaFOTaGNquzTq4HxNRoPMqJnvt92UCjNdUzahWQMDO0Wz6ENyuzLqwBOEUqana5IF+oAV2hy/7Sot1OjHCLsJIQ0MqRoh0bfH/LazLRGjokkDw7mNesKP/LZ3TKEGPk/o0sBYXgGb1uf9tvs0YZMuym8D+YX3CN/zmwdDmLmvqNLAKH5inPAp83teYWbALt3JsCIAm/hFBibvVg2MoBtgkye8JmN2hoy4pHgzUuiHfsDSY1mbKJCb+bEXZNRp0NlkQSNs5iYh6by0dYpCN4j4LuE1DTqXrFiimpCfEgY06Fiyoo/wM1VkPM4yk7gKwIaPqyAkoEFnUgWBRMmYp0EnUg1POyUDAcNkzvLpCthUPubFzAIzvxufqlgoSwYiuOmU1/AasK39ihb647UaNDrVsdYuGYjiDmvQ4FQHbBw/Kkx/tF6DxqYL1scjAyurdg0ami6AraOvuOjDRRo0Mt0QvTKSmYVgfjcw3VARy1353bh0xXS3xTR0V69uOso/ONbIqxra+NmWbt5wqYvXNrXz2nPtvP5CJ29qvcG//uYWfXaDf1Lfyjfsr+XPvXnA93Y7wCKl7urZN/bx17aU8w/JeJ+d+YZfICNdv93Pb90d4rd7h3jHrT5+/spNXnn6Cv/3/pP8hdUfRdWVvSyfr9pRxS9fC3In0tLRY7THbtt//8+P+Ssbj/C/bCjlL284/BB/Xn+YL3qnhL9M/33lP0eM/i3bWsEX09+6QHrFVDKeJHQ6Vfh+cT0fuz9uy2APCE1ETjSjwUA9/SFHZITljfc/tdVuGLa7Z5DfH5/gI2P3+fDoJO6N3Oeh4THj//F5WNBPkP6rFYUqCYHtn7QSspiZxV6OFJ4jlyEru4+eiajrl3/fZ4y0tut3+MTEA0eE5B0+bavdO0sbHOmHrNpZpZIQ2D7HSkhCOY9TzdelO9Q3OEzG3x9T75/WlfBrN/ukdRdWNsVt82/XHORdwQFp3WFZurlcJSFAIEwGjh0nVGO1t/yso079a291XN0YSbLjpOCT2IQ8s6yAl315WVpvWIK9If6L1+3PUzYBDmaBkDnMzPk6VrZ08zE+bvGzdgUrpni63ymosT0/hSWaOwwDEzXmB6dSWHleNRlccDAHhPw8UWXzlu/nrZ090h3rGxrhL71drJyQbSWnYuo8cPy8dFvDcolWf394K+AGIUCWsv3HB2WNjjpYUtMcU+/GwpPSo29roD6qPiwa7vTfc9TWEVpx2V3BOcQiEJKnQtmLaw4ZDZaVB+TIV+RVRtW7fl8tjRA5Qt4NfBVVH0aPU/mo6n9ukgHkgZB8VQpraAftRBovd0fdbK3d87mxJ5CR94oij5DfrD7Ir3b3OmpjT1/IWJm5TEg+CGlQpXAB+dbBe6PSnYVLKqg4F1HnUtoh3x2QczHbiqfPIfNoiV1UfUG6bRDMYbkfnnCbDKBBKSHAnjL5ZSqkl/Ylf4wwwSOEgRCMjGyPsDHcUXqa3KN8y7BLP0iuCiEdrwhRenQZw7rzlvxmDlJ68tI0fYgxIR4mI9iBW3WA6KFh+ZELKf+qxVhFekAGEJzhhuIdDkMSQ+TuFuQWfUsXgnq9A8NSenYemSQEm8BjdS2O2oNRu8C9JW5EuELIM68X8IvtzqK151pv8BdWTUaDt3xcx8cn5FZZWIKHv79ye6X0Kg0yMjbO1+z6zFMyXCMk/C872CcftYWbR44Dcwcm4rMOApeIrT238oARC2vruiv9fUhxdbMxulKGEAD/wkbH5HbZYRmllY3s3KFKMErjBT7dJMS1+0iQ80C8KpkES1wkofwgg4lJXemydyp+tzbxhJOXgnSxT2QA6vchkYDQyL0R59FVL2XzoTrfCVEWOomGbJoc99DKx+l84qVgdSiTk1eMfGXBxXj49YpCXnnqit/2jivYzQdOXPCLlDxPy3+wcmluv+23zW0JSo5czHtEgxF+z/LyR1Fac/POoN/2tiUdN/u8XnFlKUnhyuJv71bwCQeBPj9kMDTKNx74wgu7PEzhJlzk4ARv51c7Cmn4Ici/r1Zb9hMJZpGDijIgp8DKy42RgpA58uZIbjVc7FKicyA0QiNbeemPFQFlhXJOgSBk0QlnSaNY8nnj1Yf5C+TQj9NmT4X0D40Y5aQu5EZg+8XKSkkTAVZejS3dSgwWlq1Tcuov/uNQQmU/VrnRM8hf3VSm2g7fLiX1+2wIwisoXFPlvtbl10z7DRQ3jEqWE0UTFI5nq7XB9DMizOfjCNjNb9hXK50/jySRKiLhZnLI3XQ4zGZOlZXbj6vsf8TjCFoc2HlpXbFRq5WIi1m9K/qKaGFukRJSdh2JXR0picjnDJlGR9pgOCSanFTAIzEVSzdKSRPNtSisfo98pE0HtxUJmEAP1140qg3jTTE4y4GiBDt659NEX/d1hzQRKHNCOZHCPsY89PlDpumVGjjIgzD+fw/WGUUU2MPsLjtjlPwgZL6x8Av+101HpYKCqCbZQt+tb+7kXcH+aTXEWGSg0OFq911e23SN55WcNlLLWK4r6lfsY9GClLS+OAAkIR+PE1Ie1GKtiUmGICRztYY3sHe1hiAlc/mM+7B3+YwgJHM9k7uQu55JkJK5wMw9yF1gJgjJXETjDpxd8SdIeVqDDqQanF2CaSElc02sOiR2TawgJHORshrgEQM1F/Mz831YT/PuKQbYbq4SMiykLNGgY8kKtZfxW0gp1KBzyYY8V8gQhGQedJEDbPV91wgRpGCSd71AOwWA1+vce11nCimzWeZRsFjw7lEwCymZZ/Oik+Hts3kWUmazzMbRCv8elrSQgphXur/2GX56VS6C65Ywsz7Yk3MmmkKfx4mtwsznu9NpXtHz+W6rsPR54P4k0/2B+7CwyffVXTty7SNCom+z/LaztDDzMBCK78Y0MGSiQB/K0Se/7ZqQUAdmErJZcj8w1iz6MNNveyoV6tB8QlADA9sF2hq9sjAVhJkVLQjl1zA95xi0CYsSlNXqsa/wQpjpyp4ibNJk1ARFW9Cm1HJNsmIhJ5eZIWsvHkIOit/KzZAQQ4RLY4TlzAxHtCkaQdCBjVyF0J2VVi5JhZDBvkN4lJlBTOT1UbyXw8wwTZ4wbsMUVFg+zxHfmSt0aL93+D8J6ZYYeKzdygAAAABJRU5ErkJggg=="
                                             alt="vk logo"/>
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
                <br>
            </div>
        </div>
    </div>
</template>

<script>
    import DonationSection from "./DonationSection";
    import dictionaryUtil from "../util/dictionaryUtil";

    export default {
        name: "AppGroupsSection",

        components: {DonationSection},

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