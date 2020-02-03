package com.pazukdev.backend.dto;

import com.pazukdev.backend.util.TranslatorUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminMessage extends Message {

    private String link;
    private String linkText;

    public void translate(final String langTo) {
        text = TranslatorUtil.translate("en", langTo, text, false);
    }

}
