package com.pazukdev.backend.integration.testcore.page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginPage extends AbstractPage {

    @FindBy(id = "switch-login-form-button")
    private WebElement switchLoginFormButton;

    @FindBy(id = "continue-as-guest-button")
    private WebElement continueAsGuestButton;

    @FindBy(id = "nickname-input")
    private WebElement nicknameInput;

    @FindBy(id = "email-input")
    private WebElement emailInput;

    @FindBy(id = "password-input")
    private WebElement passwordInput;

    @FindBy(id = "repeat-password-input")
    private WebElement repeatPasswordInput;

    @FindBy(id = "submit-login-form")
    private WebElement submitLoginForm;

    @Override
    public String getUrl() {
        return getURL("login");
    }

    @Override
    public WebElement getControlElement() {
        return continueAsGuestButton;
    }

}
