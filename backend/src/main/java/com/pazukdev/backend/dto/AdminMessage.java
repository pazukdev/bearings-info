package com.pazukdev.backend.dto;

import com.pazukdev.backend.util.TranslatorUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminMessage extends Message {

    private String link;
    private String linkText;

    public void translate(final String langTo, final Set<String> dictionary) {
        text = TranslatorUtil.translate("en", langTo, text, false, dictionary);
    }

}
