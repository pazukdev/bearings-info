package com.pazukdev.backend.integration.testcore.page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openqa.selenium.WebElement;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MotorcyclePage extends AbstractPage {

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public WebElement getControlElement() {
        return title;
    }

}




















