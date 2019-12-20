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
            <tr v-if="isEditMode && itemView.defaultImg && !messagesContain('img removed')">
                <td>
                    <button id="remove-img-button" type="button" @click="removeImg()">
                        {{"Remove image"}}
                    </button>
                </td>
            </tr>
            <tr v-if="isEditMode">
                <td>
                    <br>
                    Upload another image<br>
                    Accepts .png images only<br>
                    Size limit: 2MB<br>
                    <br>
                </td>
            </tr>
            <tr v-if="isEditMode" class="alert-message">
                <td>
                    {{fileUploadMessage}}
                </td>
            </tr>
            <tr v-if="isEditMode">
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
    export default {
        name: "ItemViewImage"
    }
</script>

<style scoped>

</style>