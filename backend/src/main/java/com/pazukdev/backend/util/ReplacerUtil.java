package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.entity.item.Replacer;
import com.pazukdev.backend.entity.item.TransitiveItem;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.service.TransitiveItemService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReplacerUtil {

    public static List<Replacer> createReplacers(final TransitiveItem transitiveItem,
                                                 final ItemService itemService,
                                                 TransitiveItemService transitiveItemService) {
        final List<Replacer> replacers = new ArrayList<>();
        final String replacersSourceString = transitiveItem.getReplacer();
        if (replacersSourceString == null || replacersSourceString.equals("-")) {
            return replacers;
        }
        for (final String replacerData : Arrays.asList(replacersSourceString.split("; "))) {
            String replacerName;
            String comment = null;
            if (SpecificStringUtil.containsParentheses(replacerData)) {
                replacerName = SpecificStringUtil.getStringBeforeParentheses(replacerData);
                comment = SpecificStringUtil.getStringBetweenParentheses(replacerData);
            } else {
                replacerName = replacerData;
            }
            final String category = transitiveItem.getCategory();
            final TransitiveItem transitiveReplacerItem = transitiveItemService.find(category, replacerName);
            final Item replacerItem = itemService.getOrCreate(transitiveReplacerItem);

            final Replacer replacer = new Replacer();
            replacer.setName(NestedItemUtil.createName(transitiveItem.getName(), replacerName));
            replacer.setItem(replacerItem);
            if (comment != null) {
                replacer.setComment(comment);
            }

            replacers.add(replacer);
        }

        return replacers;
    }

}
