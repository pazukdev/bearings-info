<template>
    <div v-if="isViewWithImage()" id="img-container">
        <table>
            <tbody>
            <tr v-if="isImgRendered()">
                <td>
                    <div class="image-preview">
                        <img class="preview" :src="getImgUrl()" alt="Item image">
                    </div>
                    <br>
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

    export default {
        name: "EditableImg",

        computed: {
            ...mapState({
                editMode: state => state.dictionary.editMode,
                itemView: state => state.dictionary.itemView,
                basicUrl: state => state.dictionary.basicUrl
            })
        },

        data() {
            return {
                img: "",
                fileUploadMessage: "",
                noImageMessage: "",
            }
        },

        // https://docs.google.com/uc?id=1FJAJs0bqgNRyzGcEa8gMm0hJkG4ZccfC

        methods: {
            getImgUrl() {
                if (routerUtil.isHome(this.$route)) {
                    return "https://pazukdev.github.io/sovietboxers/img/app_logo.9a3c3892.png";
                }
                let defaultImg = "https://drive.google.com/open?id=1wyb_Av3pKeP2gnzLPRfz40FjJaUZkQde";
                let itemView = this.itemView;
                let img = itemView.img;
                if (this.isEmpty(img)) {
                    img = defaultImg;
                    this.noImageMessage = "No image";
                } else {
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

            isImgRendered() {
                // return !messagesContain('img removed');
                return true;
            },

            imgFileUploadEnabled() {
                return false;
            },

            isViewWithImage() {
                if (routerUtil.isHome(this.$route)) {
                    return true;
                }
                let view = this.itemView;
                return !this.isEmpty(view.img) || !this.isEmpty(view.defaultImg);
            },

            isEmpty(value) {
                if (shared.isEmpty(value)) {
                    return true;
                }
                return value.includes("/-");
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
        padding: 20px 0;
    }
</style>