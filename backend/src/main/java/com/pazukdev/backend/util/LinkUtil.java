package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.view.ItemView;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.Link;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.UserActionRepository;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Set;

import static com.pazukdev.backend.converter.LinkConverter.convert;
import static com.pazukdev.backend.entity.factory.LinkFactory.LinkType;
import static com.pazukdev.backend.entity.factory.LinkFactory.createLink;
import static com.pazukdev.backend.util.SpecificStringUtil.isEmpty;
import static com.pazukdev.backend.util.UserActionUtil.ActionType;
import static com.pazukdev.backend.util.UserActionUtil.processLinkAction;

public class LinkUtil {

    public static void updateItemLinks(final Item target,
                                       final ItemView source,
                                       final UserEntity user,
                                       final UserActionRepository repository) {
        updateLink(source.getWikiLink(), LinkType.WIKI, target, user, repository);
        updateLink(source.getManualLink(), LinkType.MANUAL, target, user, repository);
        updateLink(source.getPartsCatalogLink(), LinkType.PARTS_CATALOG, target, user, repository);
        updateLink(source.getDrawingsLink(), LinkType.DRAWINGS, target, user, repository);
        updateLink(source.getWebsiteLink(), LinkType.WEBSITE, target, user, repository);

        target.getBuyLinks().clear();
        target.getBuyLinks().addAll(convert(source.getBuyLinks()));

    }

    public static void updateLink(final String linkUrl,
                                  final String linkType,
                                  final Item target,
                                  final UserEntity user,
                                  final UserActionRepository repository) {

        final Link link = getLink(linkType, target.getLinks());

        if (link != null) {
            if (isEmpty(linkUrl)) {
                target.getLinks().remove(link);
                if (user != null) {
                    processLinkAction(ActionType.DELETE, link, target, user, repository);
                }
            } else {
                if (link.getUrl() == null || !link.getUrl().equals(linkUrl)) {
                    link.setUrl(linkUrl);
                    if (user != null) {
                        processLinkAction(ActionType.UPDATE, link, target, user, repository);
                    }
                }
            }
        } else {
            if (!isEmpty(linkUrl)) {
                final Link newLink = createLink(linkType, linkUrl, "-");
                target.getLinks().add(newLink);
                if (user != null) {
                    processLinkAction(ActionType.ADD, newLink, target, user, repository);
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

    public static boolean isUrl(final String s) {
        return UrlValidator.getInstance().isValid(s);
    }

}
