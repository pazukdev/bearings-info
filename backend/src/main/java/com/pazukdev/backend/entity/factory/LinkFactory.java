package com.pazukdev.backend.entity.factory;

import com.pazukdev.backend.entity.Link;
import com.pazukdev.backend.util.SpecificStringUtil;

public class LinkFactory {

    public static Link createLink(final String linkType, final String link) {
        final Link linkObj = createLink(link);
        if (linkObj == null) {
            return null;
        }
        linkObj.setType(linkType);
        linkObj.setCountryCode("-");
        return linkObj;
    }

    private static Link createLink(final String link) {
        if (SpecificStringUtil.isEmpty(link)) {
            return null;
        }

        final Link linkObj = new Link();
        linkObj.setUrl(link);
        return linkObj;
    }

}
