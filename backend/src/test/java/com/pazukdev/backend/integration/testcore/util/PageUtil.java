package com.pazukdev.backend.integration.testcore.util;

import com.pazukdev.backend.integration.testcore.page.Page;

/**
 * @author Siarhei Sviarkaltsau
 */
public class PageUtil {

    public static <T extends Page> T instantiatePage(final Class<T> pageClass) {
        T page = null;

        try {
            page = pageClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }

}
