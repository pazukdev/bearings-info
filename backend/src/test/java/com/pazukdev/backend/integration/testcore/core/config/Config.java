package com.pazukdev.backend.integration.testcore.core.config;

/**
 * @author Aliaxei Voitsel
 */
public interface Config {

    String getRemoteNodeUrl();

    int getWaitTimeout(); // default timeout for WebDriverWait stored into context in seconds

}
