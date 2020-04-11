package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.LinkDto;
import com.pazukdev.backend.dto.view.ItemView;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.Link;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.service.ItemService;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Set;

import static com.pazukdev.backend.converter.LinkConverter.convert;
import static com.pazukdev.backend.entity.factory.LinkFactory.createLink;
import static com.pazukdev.backend.util.SpecificStringUtil.isEmpty;
import static com.pazukdev.backend.util.UserActionUtil.ActionType;
import static com.pazukdev.backend.util.UserActionUtil.processLinkAction;

public class LinkUtil {

    public static class LinkType {
        public static final String BUY = "buy";
        public static final String DRAWINGS = "drawings";
        public static final String IMG = "img";
        public static final String MANUAL = "manual";
        public static final String PARTS_CATALOG = "parts catalog";
        public static final String WEBSITE = "website";
        public static final String WIKI = "wiki";
    }

    public static void updateItemLinks(final Item target,
                                       final ItemView source,
                                       final UserEntity user,
                                       final ItemService service) {
        updateLink(source.getWikiLink(), LinkType.WIKI, target, user, service);
        updateLink(source.getManualLink(), LinkType.MANUAL, target, user, service);
        updateLink(source.getPartsCatalogLink(), LinkType.PARTS_CATALOG, target, user, service);
        updateLink(source.getDrawingsLink(), LinkType.DRAWINGS, target, user, service);
        updateLink(source.getWebsiteLink(), LinkType.WEBSITE, target, user, service);

        for (final LinkDto buyLinkDto : source.getBuyLinks()) {

//            final String websiteLink = source.getWebsiteLink();
//            String websiteLang = source.getWebsiteLang();
//            Link websiteLinkUrl = null;
//            if (linkDto.getId() != null) {
//                websiteLinkUrl = service.getLinkRepository().findById(linkDto.getId()).orElse(null);
//            }
//            if (websiteLinkUrl == null) {
//                websiteLinkUrl = getLink(LinkType.WEBSITE, linkDto.getCountryCode(), target.getLinks());
//            }
//            if (websiteLinkUrl != null) {
//                if (isEmpty(websiteLink)) {
//                    target.getLinks().remove(websiteLinkUrl);
//                    processLinkAction(ActionType.DELETE, LinkType.WEBSITE, target, user, service);
//                } else {
//                    if (websiteLinkUrl.getName() == null || !websiteLinkUrl.getName().equals(websiteLink)) {
//                        websiteLinkUrl.setName(websiteLink);
//                        processLinkAction(ActionType.UPDATE, LinkType.WEBSITE, target, user, service);
//                    }
//                    websiteLinkUrl.setLang(websiteLang);
//                }
//            } else {
//                if (!isEmpty(websiteLink)) {
//                    final Link newWebsite = createWebsiteLink(websiteLink, websiteLang);
//                    target.getLinks().add(newWebsite);
//                    processLinkAction(ActionType.ADD, LinkType.WEBSITE, target, user, service);
//                }
//            }
        }

        target.getBuyLinks().clear();
        target.getBuyLinks().addAll(convert(source.getBuyLinks()));

    }

    public static void updateLink(final String linkUrl,
                                   final String linkType,
                                   final Item target,
                                   final UserEntity user,
                                   final ItemService service) {
        final Link link = getLink(linkType, target.getLinks());
        if (link != null) {
            if (isEmpty(linkUrl)) {
                target.getLinks().remove(link);
                if (user != null) {
                    processLinkAction(ActionType.DELETE, linkType, target, user, service);
                }
            } else {
                if (link.getUrl() == null || !link.getUrl().equals(linkUrl)) {
                    link.setUrl(linkUrl);
                    if (user != null) {
                        processLinkAction(ActionType.UPDATE, linkType, target, user, service);
                    }
                }
            }
        } else {
            if (!isEmpty(linkUrl)) {
                final Link newLink = createLink(linkType, linkUrl);
                target.getLinks().add(newLink);
                if (user != null) {
                    processLinkAction(ActionType.ADD, linkType, target, user, service);
                }
            }
        }
    }

    public static void setLinksToItemView(final ItemView target, final Item source) {
        target.setBuyLinks(convert(source.getBuyLinks()));
        for (final Link link : source.getLinks()) {
            final String linkType = link.getType();
            switch (linkType) {
                case LinkType.WIKI:
                    target.setWikiLink(link.getUrl());
                    break;
                case LinkType.WEBSITE:
                    target.setWebsiteLink(link.getUrl());
                    break;
                case LinkType.MANUAL:
                    target.setManualLink(link.getUrl());
                    break;
                case LinkType.PARTS_CATALOG:
                    target.setPartsCatalogLink(link.getUrl());
                    break;
                case LinkType.DRAWINGS:
                    target.setDrawingsLink(link.getUrl());
                    break;
            }
        }
    }

    public static String getLink(final String linkType, final Item item) {
        final Link link = LinkUtil.getLink(linkType, item.getLinks());
        return link != null ? link.getUrl() : null;
    }

    public static Link getLink(final String linkType, final Set<Link> itemLinks) {
        for (final Link link : itemLinks) {
            if (link == null) {
                continue;
            }
            if (link.getType() != null && link.getType().equalsIgnoreCase(linkType)) {
                return link;
            }
        }
        return null;
    }

    public static Link getLink(final String linkType,
                               final String countryCode,
                               final Set<Link> itemLinks) {
        if (isEmpty(countryCode)) {
            return null;
        }
        for (final Link link : itemLinks) {
            if (link == null) {
                continue;
            }
            final String type = link.getType();
            final String code = link.getCountryCode();
            if (isEmpty(code) || isEmpty(type)) {
                continue;
            }
            if (type.equalsIgnoreCase(linkType) && code.equalsIgnoreCase(countryCode)) {
                return link;
            }
        }
        return null;
    }

    public static boolean isUrl(final String s) {
        return UrlValidator.getInstance().isValid(s);
    }

//    public static void addSetToSet(final Set<Link> toAdd, final Set<Link> addTo) {
//        for (final Link link : toAdd) {
//            link.addTo(addTo, true);
//        }
//    }

}
