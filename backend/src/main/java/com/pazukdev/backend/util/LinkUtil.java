package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.view.ItemView;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.Link;
import com.pazukdev.backend.entity.TransitiveItem;
import com.pazukdev.backend.entity.factory.LinkFactory;

public class LinkUtil {

    public static void updateItemLinks(final Item target, final ItemView source) {
        final String wikiLink = source.getWikiLink();
        final Link wiki = getLink("wiki", target);
        if (wiki != null) {
            if (SpecificStringUtil.isEmpty(wikiLink)) {
                target.getLinks().remove(wiki);
            } else {
                wiki.setName(wikiLink);
            }
        } else {
            if (!SpecificStringUtil.isEmpty(wikiLink)) {
                final Link newWiki = LinkFactory.createWikiLink(wikiLink);
                target.getLinks().add(newWiki);
            }
        }

        final String sellerLink = source.getSellerLink();
        String sellerLinkLang = source.getSellerLang();
        final Link seller = getLink("seller", target);
        if (seller != null) {
            if (SpecificStringUtil.isEmpty(sellerLink)) {
                target.getLinks().remove(seller);
            } else {
                seller.setName(sellerLink);
                seller.setLang(sellerLinkLang);
            }
        } else {
            if (!SpecificStringUtil.isEmpty(sellerLink)) {
                final Link newSeller = LinkFactory.createSellerLink(sellerLink, sellerLinkLang);
                target.getLinks().add(newSeller);
            }
        }
    }

    public static void setLinksToItemView(final ItemView target, final Item source) {
        final String defaultSellerLang = "all";
        target.setSellerLang(defaultSellerLang);

        for (final Link link : source.getLinks()) {
            if (link.getType().equals("wiki")) {
                target.setWikiLink(link.getName());
            }
            if (link.getType().equals("seller")) {
                target.setSellerLink(link.getName());
                target.setSellerLang(link.getLang());
            }

        }
    }

    public static void addLinksToItem(final Item target, final TransitiveItem source) {
        final Link wiki = LinkFactory.createWikiLink(source.getWiki());
        if (wiki != null) {
            target.getLinks().add(wiki);
        }

        if (target.getName().equalsIgnoreCase("pvl-1 serg")) {
            final String link = "https://vk.com/market-181468296?w=product-181468296_3462964%2Fquery";
            final String lang = null;
            final Link seller = LinkFactory.createSellerLink(link, lang);
            target.getLinks().add(seller);
        }
    }

    private static Link getLink(final String linkType, final Item item) {
        for (final Link link : item.getLinks()) {
            if (link.getType().equalsIgnoreCase(linkType)) {
                return link;
            }
        }
        return null;
    }

}
