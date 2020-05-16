export default {

    processUrl(imgUrl) {
        let badGoogleDriveBasicUrl = "https://drive.google.com/open?id=";
        let goodGoogleDriveBasicUrl = "https://docs.google.com/uc?id=";
        return imgUrl.replace(badGoogleDriveBasicUrl, goodGoogleDriveBasicUrl);
    }

}