package com.pazukdev.backend.integration.testcore.core.config;

import lombok.Data;

/**
 * @author Aliaxei Voitsel
 */
@Data
public class DefaultConfig implements Config {

    private String remoteNodeUrl;
    private Integer waitTimeout = 60;

}
