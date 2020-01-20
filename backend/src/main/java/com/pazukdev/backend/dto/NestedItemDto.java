package com.pazukdev.backend.dto;

import com.pazukdev.backend.service.ItemService;
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
//    private String localizedItemCategory = "-";
    private String creatorName;
    private Integer rating = 0;

    private String buttonText = "-";
//    private String localizedButtonText = "-";
    private String selectText = "-";
//    private String localizedSelectText = "-";

    private String comment = "-";
//    private String localizedComment = "-";
    private String secondComment = "-";
//    private String localizedSecondComment = "-";

    public void setItemCategory(final String itemCategory) {
        this.itemCategory = itemCategory;
//        this.localizedItemCategory = itemCategory;
    }

    public void setButtonText(final String buttonText) {
        this.buttonText = buttonText;
//        this.localizedButtonText = buttonText;
    }

    public void setSelectText(final String selectText) {
        this.selectText = selectText;
//        this.localizedSelectText = selectText;
    }

    public void setComment(final String comment) {
        this.comment = comment;
//        this.localizedComment = comment;
    }

    public void setSecondComment(final String secondComment) {
        this.secondComment = secondComment;
//        this.localizedSecondComment = secondComment;
    }

    public void translate(final String langFrom, final String langTo, final ItemService service) {
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
