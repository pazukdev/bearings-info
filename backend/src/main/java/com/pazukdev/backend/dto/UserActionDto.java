package com.pazukdev.backend.dto;

import com.pazukdev.backend.util.TranslatorUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserActionDto extends AbstractDto {

    private Long userId;
    private Long itemId;
    private Long parentId;
    private String userName;
    private String itemName;
    private String parentName;
    private String actionType;
    private String itemType;
    private String itemCategory;
    private String date;

    public void translate(final String langTo, final Set<String> dictionary) {
        actionType = TranslatorUtil.translate("en", langTo, actionType, false, dictionary);
        itemType = TranslatorUtil.translate("en", langTo, itemType, false, dictionary);
        itemCategory = TranslatorUtil.translate("en", langTo, itemCategory, false, dictionary);
        itemName = TranslatorUtil.translate("en", langTo, itemName, false, dictionary);
        parentName = TranslatorUtil.translate("en", langTo, parentName, false, dictionary);
    }

}
