package com.pazukdev.backend.entity.factory;

import com.pazukdev.backend.entity.Link;
import com.pazukdev.backend.util.SpecificStringUtil;

public class LinkFactory {

    public static Link createSellerLink(final String link, final String lang) {
        final Link linkObj = createLink(link);
        if (linkObj == null) {
            return null;
        }
        linkObj.setType("seller");
        linkObj.setLang(SpecificStringUtil.isEmpty(lang) ? "all" : lang);
        return linkObj;
    }

    public static Link createWikiLink(final String link) {
        final Link linkObj = createLink(link);
        if (linkObj == null) {
            return null;
        }
        linkObj.setType("wiki");
        linkObj.setLang("all");
        return linkObj;
    }

    private static Link createLink(final String link) {
        if (SpecificStringUtil.isEmpty(link)) {
            return null;
        }

        final Link linkObj = new Link();
        linkObj.setName(link);
        return linkObj;
    }

}
