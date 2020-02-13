export default {
    processUrl(imgUrl) {
        return imgUrl.replace("https://drive.google.com/open?id=", "https://docs.google.com/uc?id=");
    }
}