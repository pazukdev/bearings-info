package com.pazukdev.backend.dto;

import com.pazukdev.backend.util.SpecificStringUtil;
import com.pazukdev.backend.util.TranslatorUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

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

    public void translate(final String langFrom, final String langTo, final Set<String> dictionary) {
        if (SpecificStringUtil.isEmpty(langFrom) || SpecificStringUtil.isEmpty(langTo)) {
            return;
        }
        if (langTo.equals("en")) {
            translateToEnglish(langFrom, dictionary);
            return;
        }
        if (langFrom.equals("en")) {
            translateToLang(langTo, dictionary);
        }
    }

    private void translateToLang(final String langTo, final Set<String> dictionary) {
        final String langFrom = "en";
        final boolean addToDictionary = false;

        itemCategory = TranslatorUtil.translate(langFrom, langTo, itemCategory, addToDictionary, dictionary);
        buttonText = TranslatorUtil.translate(langFrom, langTo, buttonText, addToDictionary, dictionary);
        selectText = TranslatorUtil.translate(langFrom, langTo, selectText, addToDictionary, dictionary);
        comment = TranslatorUtil.translate(langFrom, langTo, comment, addToDictionary, dictionary);
        secondComment = TranslatorUtil.translate(langFrom, langTo, secondComment, addToDictionary, dictionary);
        translatedVehicleClass = TranslatorUtil.translate(langFrom, langTo, vehicleClass, addToDictionary, dictionary);
    }

    private void translateToEnglish(final String langFrom, final Set<String> dictionary) {
        final String langTo = "en";
        final boolean addToDictionary = true;

        if (langFrom.equals(langTo)) {
            return;
        }

        itemCategory = TranslatorUtil.translate(langFrom, langTo, itemCategory, addToDictionary, dictionary);
        comment = TranslatorUtil.translate(langFrom, langTo, comment, addToDictionary, dictionary);
        secondComment = TranslatorUtil.translate(langFrom, langTo, secondComment, addToDictionary, dictionary);
    }

}
