package com.pazukdev.backend.listener;

import com.pazukdev.backend.entity.Link;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.factory.LinkFactory;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.util.BeanUtil;
import com.pazukdev.backend.util.LoggerUtil;
import com.pazukdev.backend.util.UserUtil;

import javax.persistence.PostRemove;

import static com.pazukdev.backend.util.UserActionUtil.ActionType;
import static com.pazukdev.backend.util.UserActionUtil.processLinkAction;

/**
 * @author Siarhei Sviarkaltsau
 */
public class AuditListener {

    @PostRemove
    public void postRemove(final Object o) {
        postAction(o, ActionType.DELETE);
    }

    private void postAction(final Object o, final String actionType) {
        final ItemService itemService = BeanUtil.getBean(ItemService.class);
        if (o instanceof Link) {
            final String userName = UserUtil.getCurrentUserName();
            final Link link = (Link) o;

            if (link.getType().equals(LinkFactory.LinkType.BUY)) {
                final UserEntity user = itemService.getUserService().findFirstByName(userName);
                processLinkAction(actionType, link, user, itemService.getUserActionRepository());
            }

            final String logMessage = "Link"
                    + " type=" + link.getType()
                    + " countryCode=" + link.getCountryCode()
                    + " url=" + link.getUrl()
                    + " " + actionType.toLowerCase()
                    + "d by user " + userName;
            LoggerUtil.info(logMessage);
        }
    }

}
