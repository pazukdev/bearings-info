<template>
    <div v-if="isViewWithImage()" id="img-container">
        <table>
            <tbody>
            <tr>
                <td>
                    <div>
<!--                        <div class="vl"></div>-->
                        <a :href="getImgUrl()" target="_blank"
                           :title="translate('Tap to open image')">
                            <img id="item-img"
                                 :class="{'small-img':small}"
                                 :src="getImgUrl()" alt="Item image">
                        </a>
                    </div>
                    {{translate(noImageMessage)}}
                </td>
            </tr>
            </tbody>
        </table>

        <table v-if="editMode">
            <tbody>
            <tr v-if="!isEmpty(itemView.img) && !messagesContain('img removed')">
                <td>
                    <button id="remove-img-button" type="button" @click="removeImg()">
                        {{translate("Remove image")}}
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <br>
                    {{translate("Image link")}}
                    <br>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="text" v-model="itemView.img">
                </td>
            </tr>
            <tr v-if="imgFileUploadEnabled()">
                <td>
                    <br>
                    {{translate("or upload file (accepts .png, size limit 2MB)")}}
                    <br>
                </td>
            </tr>
            <tr class="alert-message" v-if="imgFileUploadEnabled()">
                <td>
                    {{translate(fileUploadMessage)}}
                </td>
            </tr>
            <tr v-if="imgFileUploadEnabled()">
                <td>
                    <input type="file" accept="image/png"
                           style="color: black"
                           @change="previewImage"><br><br>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import {mapState} from "vuex";
import shared from "../util/shared";
import routerUtil from "../util/routerUtil";
import dictionaryUtil from "../util/dictionaryUtil";
import imgUtil from "../util/imgUtil";
import basicComponent from "../mixin/basicComponent";

export default {
        name: "EditableImg",

        props: {
            small:Boolean
        },

        mixins: [basicComponent],

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            })
        },

        data() {
            return {
                img: "",
                fileUploadMessage: "",
                noImageMessage: "",
                extraSmall: false
            }
        },

        // https://docs.google.com/uc?id=1FJAJs0bqgNRyzGcEa8gMm0hJkG4ZccfC

        methods: {
            getImgUrl() {
                let itemView = this.itemView;
                let img = itemView.img;
                if (this.isEmpty(img)) {
                    this.extraSmall = true;
                    this.noImageMessage = "No image";
                    return require("../assets/default_image.png");
                } else {
                    this.extraSmall = false;
                    this.noImageMessage = "";
                }
                let isBase64ImgData = img.includes(";base64,");
                if (isBase64ImgData) {
                    return img;
                } else {
                    let imgUrl;
                    if (img.includes("https:") || img.includes("http:")) {
                        imgUrl = img;
                    } else {
                        // e.g.: localhost:8090/bearings-info/api/bearing/bearing_18.png
                        imgUrl = this.basicUrl + "/" + img;
                    }
                    return imgUtil.processUrl(imgUrl);
                }
            },

            imgFileUploadEnabled() {
                return false;
            },

            isViewWithImage() {
                return routerUtil.isItem(this.$route) || routerUtil.isUser(this.$route);
            },

            messagesContain(message) {
                return shared.isInArray(message, this.itemView.messages);
            },

            removeImg() {
                this.itemView.img = "";
            },

            previewImage(event) {
                let input = event.target;
                let file = input.files[0];
                if (file !== null) {
                    if (file.size > 2097152) {
                        this.fileUploadMessage = "Image is too big! Size limit is 2MB";
                        return;
                    }
                    this.fileUploadMessage = "";
                    shared.removeFromArray("img removed", this.itemView.messages);
                    this.itemView.messages.push("img uploaded");
                    let reader = new FileReader();
                    reader.onload = (e) => {
                        this.itemView.img = e.target.result;
                    };
                    reader.readAsDataURL(file);
                }
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>
    #img-container {
        /*padding: 2px 0;*/
    }

    /*.extra-small-img {*/
    /*    width: 100px;*/
    /*}*/

    /*.vl {*/
    /*    border: 3px solid grey;*/
    /*    height: 20%;*/
    /*    position: absolute;*/
    /*    top: 50%;*/
    /*    left: 50%;*/
    /*    transform: translate(-50%, -50%);*/
    /*}*/
</style>