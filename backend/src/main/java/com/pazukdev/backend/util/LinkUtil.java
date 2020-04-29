package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.view.ItemView;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.Link;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.UserActionRepository;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.pazukdev.backend.converter.LinkConverter.convert;
import static com.pazukdev.backend.entity.factory.LinkFactory.LinkType;
import static com.pazukdev.backend.entity.factory.LinkFactory.createLink;
import static com.pazukdev.backend.util.SpecificStringUtil.isEmpty;
import static com.pazukdev.backend.util.UserActionUtil.ActionType;
import static com.pazukdev.backend.util.UserActionUtil.processLinkAction;

/**
 * @author Siarhei Sviarkaltsau
 */
public class LinkUtil {

    public static void updateItemLinks(final Item target,
                                       final ItemView source,
                                       final UserEntity user,
                                       final List<String> messages,
                                       final UserActionRepository repo) {
        updateLink(source.getWikiLink(), LinkType.WIKI, target, user, messages, repo);
        updateLink(source.getManualLink(), LinkType.MANUAL, target, user, messages, repo);
        updateLink(source.getPartsCatalogLink(), LinkType.PARTS_CATALOG, target, user, messages, repo);
        updateLink(source.getDrawingsLink(), LinkType.DRAWINGS, target, user, messages, repo);
        updateLink(source.getWebsiteLink(), LinkType.WEBSITE, target, user, messages, repo);

        final Set<Link> links = convert(source.getBuyLinks());
        for (final Link link : links) {
            String actionDetails = "";
            if (link.getId() == null) {
                processLinkAction(ActionType.ADD, link, actionDetails, target, user, messages, repo);
            } else {
                for (final Link oldLink : target.getBuyLinks()) {
                    if (link.getId().equals(oldLink.getId())) {
                        final String newUrl = link.getUrl();
                        final String oldUrl = oldLink.getUrl();
                        final String newCountryCode = link.getCountryCode();
                        final String oldCountryCode = oldLink.getCountryCode();
                        final boolean urlChanged = !Objects.equals(newUrl, oldUrl);
                        final boolean countryChanged = !Objects.equals(newCountryCode, oldCountryCode);
                        if (urlChanged) {
                            actionDetails += "url changed: " + oldUrl + " -> " + newUrl;
                        }
                        if (countryChanged) {
                            actionDetails += ", country changed: " + oldCountryCode + " -> " + newCountryCode;
                        }
                        if (urlChanged || countryChanged) {
                            processLinkAction(ActionType.UPDATE, link, actionDetails, target, user, messages, repo);
                        }
                    }
                }
            }
        }

        final List<Link> linksToDelete = new ArrayList<>();

        for (final Link oldLink : target.getBuyLinks()) {
            boolean delete = true;
            for (final Link newLink : links) {
                if (oldLink.getId().equals(newLink.getId())) {
                    delete = false;
                }
            }
            if (delete) {
                linksToDelete.add(oldLink);
            }
        }


        target.getBuyLinks().clear();
        target.getBuyLinks().addAll(links);

        for (final Link link : linksToDelete) {
            processLinkAction(ActionType.DELETE, link, "", target, user, messages, repo);
        }
    }

    public static void updateLink(final String newUrl,
                                  final String linkType,
                                  final Item target,
                                  final UserEntity user,
                                  final List<String> messages,
                                  final UserActionRepository repo) {

        final Link link = getLink(linkType, target.getLinks());
        final boolean processLinkAction = user != null && messages != null;

        String actionDetails = "";

        if (link != null) {
            if (isEmpty(newUrl)) {
                target.getLinks().remove(link);
                if (processLinkAction) {
                    processLinkAction(ActionType.DELETE, link, actionDetails, target, user, messages, repo);
                }
            } else {
                final String oldUrl = link.getUrl();
                final boolean urlChanged = !Objects.equals(newUrl, oldUrl);
                if (urlChanged) {
                    actionDetails += "url changed: " + oldUrl + " -> " + newUrl;
                    link.setUrl(newUrl);
                    processLinkAction(ActionType.UPDATE, link, actionDetails, target, user, messages, repo);
                }
            }
        } else {
            if (!isEmpty(newUrl)) {
                final Link newLink = createLink(linkType, newUrl, "-");
                target.getLinks().add(newLink);
                if (processLinkAction) {
                    processLinkAction(ActionType.ADD, newLink, actionDetails, target, user, messages, repo);
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
