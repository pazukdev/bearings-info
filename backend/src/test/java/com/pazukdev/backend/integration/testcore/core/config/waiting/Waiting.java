package com.pazukdev.backend.integration.testcore.core.config.waiting;

/**
 * @author Aliaksei Voitsel
 *
 * For specific waits during dinamic page changes. Related to the concrete project.
 */
@FunctionalInterface
public interface Waiting {

    void doWait();

}
