package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.Item;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImgUtil {

    public static final String IMG_DIRECTORY_PATH = "backend/src/img/";
    private static final String IMG_EXTENSION = "png";
    private static final String IMG_DATA_METADATA = "data:image/png;base64,";

    public static String getItemImgData(final Item item) {
        final String itemCategory = item.getCategory();
        String imgName;
        String imgPath;
        BufferedImage img = null;
        if (item.getImage() != null) {
            imgName = item.getImage();
            imgPath = getImgPath(imgName, itemCategory);
            try {
                img = getImg(imgPath);
            } catch (IOException e1) {
                e1.printStackTrace();
                imgName = getCategoryDefaultImgName(itemCategory);
                imgPath = getImgPath(imgName, itemCategory);
                try {
                    img = getImg(imgPath);
                } catch (IOException e2) {
                    e2.printStackTrace();
                    try {
                        img = getImg(imgPath);
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        imgName = getItemDefaultImgName();
                        imgPath = IMG_DIRECTORY_PATH + "common/" + imgName;
                        try {
                            img = getImg(imgPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return "-";
                        }
                    }
                }
            }
        } else {
            imgName = getCategoryDefaultImgName(itemCategory);
            imgPath = getImgPath(imgName, itemCategory);
            try {
                img = getImg(imgPath);
            } catch (IOException e2) {
                e2.printStackTrace();
                try {
                    img = getImg(imgPath);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    imgName = getItemDefaultImgName();
                    imgPath = IMG_DIRECTORY_PATH + "common/" + imgName;
                    try {
                        img = getImg(imgPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "-";
                    }
                }
            }
        }
        return createBase64ImgData(img);
    }

    private static String getCategoryDefaultImgName(final String itemCategory) {
        return itemCategory.toLowerCase().replaceAll(" ", "_") + "_default.png";
    }

    private static String getItemDefaultImgName() {
        return "default_image_small.png";
    }

    public static BufferedImage getImg(String imgPath) throws IOException {
        final File file = new File(imgPath);
        return ImageIO.read(file);
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

    public static String createBase64ImgData(final BufferedImage img) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, IMG_EXTENSION, baos);
        } catch (IOException e) {
            return "-";
        }
        return IMG_DATA_METADATA + DatatypeConverter.printBase64Binary(baos.toByteArray());
    }

    private static String getImgPath(final String imgName, final String itemCategory) {
        return IMG_DIRECTORY_PATH + itemCategory.toLowerCase().replaceAll(" ", "_") + "/" + imgName;
    }

    public static String getImgName(final String itemCategory, final String itemName) {
        return itemCategory.replaceAll(" ", "_").toLowerCase()
                + "_" + itemName.replaceAll(" ", "_").toLowerCase()
                + "." + IMG_EXTENSION;
    }

    private static void createDirectory(final String imgName, final String itemCategory) {
        final String path = IMG_DIRECTORY_PATH + itemCategory.toLowerCase().replaceAll(" ", "_");
        final File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public static String getAppImgData() {
        final String imgPath = IMG_DIRECTORY_PATH + "common/ic_launcher.png";
        try {
            final BufferedImage img = getImg(imgPath);
            return createBase64ImgData(img);
        } catch (IOException e) {
            return "-";
        }
    }

}
