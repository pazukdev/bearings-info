package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.ItemImg;
import com.pazukdev.backend.entity.TransitiveItem;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImgUtil {

    private static final String IMG_DIRECTORY_PATH = "backend/src/img/";
    private static final String IMG_EXTENSION = "png";
    private static final String IMG_DATA_METADATA = "data:image/png;base64,";

    public static String getItemImgData(final Item item) {
        try {
            if (item.getImage() != null) {
                return item.getImage().getImageData();
            } else if (item.getCategory().equals("Motorcycle")) {
                return createBase64ImgData("motorcycle_default.png", item.getCategory());
            } else {
                return createBase64ImgData("common/default_image_small.png");
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static void createImgFileInFileSystem(final String base64Data, final Item item) throws IOException {
        final String itemCategory = item.getCategory();
        final String imgName = getImgName(itemCategory, item.getName());
        createDirectory(imgName, itemCategory);

        byte[] decodedBytes = Base64.getDecoder().decode(base64Data.split(",")[1]);
        final ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
        BufferedImage img = ImageIO.read(bis);
        final String imgPath = getImgPath(imgName, itemCategory);
        final File file = new File(imgPath);
        ImageIO.write(img, IMG_EXTENSION, file);
    }

    public static ItemImg createItemImg(final TransitiveItem transitiveItem) {
        return createItemImage(transitiveItem.getImage(), transitiveItem.getCategory());
    }

    public static ItemImg createItemImg(final String base64Data, final String imgName) {
        if (base64Data == null) {
            return null;
        }

        final ItemImg itemImg = new ItemImg();
        itemImg.setName(imgName);
        itemImg.setImageData(base64Data);
        return itemImg;
    }

    private static ItemImg createItemImage(final String imgName, final String itemCategory) {
        if (imgName == null) {
            return null;
        }
        String base64Data = null;
        try {
            base64Data = createBase64ImgData(imgName, itemCategory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createItemImg(base64Data, imgName);
    }

    private static String createBase64ImgData(final String imageName, final String itemCategory) throws IOException {
        return createBase64ImgData(getImgPath(imageName, itemCategory));
    }

    public static String createBase64ImgData(final String imgPath) throws IOException {
        final File file = new File(imgPath);
        final BufferedImage img = ImageIO.read(file);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, IMG_EXTENSION, baos);
        return IMG_DATA_METADATA + DatatypeConverter.printBase64Binary(baos.toByteArray());
    }

    private static String getImgPath(final String imgName, final String itemCategory) {
        return IMG_DIRECTORY_PATH + itemCategory.toLowerCase() + "/" + imgName;
    }

    public static String getImgName(final String itemCategory, final String itemName) {
        return itemCategory.replaceAll(" ", "_").toLowerCase()
                + "_" + itemName.replaceAll(" ", "_").toLowerCase()
                + "." + IMG_EXTENSION;
    }

    private static void createDirectory(final String imgName, final String itemCategory) {
        final File directory = new File(getImgPath(imgName, itemCategory));
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

}
