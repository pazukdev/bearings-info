<template>
    <div>
        <table id="item-image" v-if="isViewWithImage()">
            <tbody>
            <tr v-if="!messagesContain('img removed')">
                <td>
                    <div class="image-preview">
                        <img class="preview" :src="itemView.imgData" alt="Item image">
                    </div>
                </td>
            </tr>
            <tr v-if="editMode && itemView.defaultImg && !messagesContain('img removed')">
                <td>
                    <button id="remove-img-button" type="button" @click="removeImg()">
                        {{"Remove image"}}
                    </button>
                </td>
            </tr>
            <tr v-if="editMode">
                <td>
                    <br>
                    Upload another image<br>
                    Accepts .png images only<br>
                    Size limit: 2MB<br>
                    <br>
                </td>
            </tr>
            <tr v-if="editMode" class="alert-message">
                <td>
                    {{fileUploadMessage}}
                </td>
            </tr>
            <tr v-if="editMode">
                <td>
                    <input type="file" accept="image/png"
                           style="color: black"
                           @change="previewImage"><br><br>
                </td>
            </tr>
            <tr><td><hr></td></tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import shared from "../../util/shared";

    export default {
        name: "ItemViewImage",

        computed: {
            ...mapState({
                editMode: state => state.dictionary.editMode,
                itemView: state => state.dictionary.itemView,
            })
        },

        data() {
            return {
                imgData: "",
                fileUploadMessage: ""
            }
        },

        methods: {
            isViewWithImage() {
                return this.itemView.imgData !== '-';
            },

            messagesContain(message) {
                return shared.isInArray(message, this.itemView.messages);
            },

            removeImg() {
                this.itemView.messages.push("img removed");
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
                        this.itemView.imgData = e.target.result;
                    };
                    reader.readAsDataURL(file);
                }
            }
        }
    }
</script>

<style scoped>
    #item-image {
        /*padding-top: 10px;*/
    }
</style>