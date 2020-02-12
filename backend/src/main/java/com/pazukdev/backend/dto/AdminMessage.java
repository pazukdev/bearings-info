package com.pazukdev.backend.dto;

import com.pazukdev.backend.util.TranslatorUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminMessage extends Message {

    private String link;
    private String linkText;

    public void translate(final String langTo, final List<String> dictionary) {
        final boolean name = false;
        final boolean addToDictionary = false;
        text = TranslatorUtil.translate("en", langTo, text, name, addToDictionary, dictionary);
    }

}
