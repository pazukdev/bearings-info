package com.pazukdev.backend.integration.testcore.action;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface Action<T> {

    T perform();

}
