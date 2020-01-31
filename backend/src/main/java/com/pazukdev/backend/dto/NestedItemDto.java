package com.pazukdev.backend.dto;

import com.pazukdev.backend.util.SpecificStringUtil;
import com.pazukdev.backend.util.TranslatorUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NestedItemDto extends AbstractDto {

    private Long itemId;
    private String itemName = "-";
    private String itemCategory = "-";
    private String creatorName;
    private Integer rating = 0;

    private String buttonText = "-";
    private String selectText = "-";

    private String comment = "-";
    private String secondComment = "-";

    private String vehicleIcon;
    private String vehicleClass;
    private String translatedVehicleClass;

    public void setVehicleClass(final String vehicleClass) {
        this.vehicleClass = vehicleClass;
        this.translatedVehicleClass = vehicleClass;
    }

    public void translate(final String langFrom, final String langTo) {
        if (SpecificStringUtil.isEmpty(langFrom) || SpecificStringUtil.isEmpty(langTo)) {
            return;
        }
        if (langTo.equals("en")) {
            translateToEnglish(langFrom);
            return;
        }
        if (langFrom.equals("en")) {
            translateToLang(langTo);
        }
    }

    private void translateToLang(final String langTo) {
        final String langFrom = "en";
        final boolean addToDictionary = false;

        itemCategory = TranslatorUtil.translate(langFrom, langTo, itemCategory, addToDictionary);
        buttonText = TranslatorUtil.translate(langFrom, langTo, buttonText, addToDictionary);
        selectText = TranslatorUtil.translate(langFrom, langTo, selectText, addToDictionary);
        comment = TranslatorUtil.translate(langFrom, langTo, comment, addToDictionary);
        secondComment = TranslatorUtil.translate(langFrom, langTo, secondComment, addToDictionary);
        translatedVehicleClass = TranslatorUtil.translate(langFrom, langTo, vehicleClass, addToDictionary);
    }

    private void translateToEnglish(final String langFrom) {
        final String langTo = "en";
        final boolean addToDictionary = true;

        if (langFrom.equals(langTo)) {
            return;
        }

        itemCategory = TranslatorUtil.translate(langFrom, langTo, itemCategory, addToDictionary);
        comment = TranslatorUtil.translate(langFrom, langTo, comment, addToDictionary);
        secondComment = TranslatorUtil.translate(langFrom, langTo, secondComment, addToDictionary);
    }

}
