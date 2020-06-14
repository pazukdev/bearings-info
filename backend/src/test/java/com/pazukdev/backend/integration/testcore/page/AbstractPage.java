package com.pazukdev.backend.integration.testcore.page;

import com.pazukdev.backend.integration.testcore.constant.Constant;
import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.util.ActionUtil;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public abstract class AbstractPage implements Page {

    protected static final String BASIC_URL = "http://localhost/#/";
    protected static final String LANG = "en";

    @FindBy(id = Constant.ElementId.APP_NAME)
    protected WebElement appName;

    @FindBy(id = Constant.ElementId.BUTTON_BACK)
    private WebElement buttonBack;

    @FindBy(id = Constant.ElementId.COPY_URL_BUTTON)
    private WebElement copyUrlButton;

    @FindBy(id = Constant.ElementId.CURRENT_LOCATION_INPUT)
    private WebElement currentLocationInput;

    @FindBy(id = Constant.ElementId.URL_COPIED_TEXT)
    private WebElement urlCopiedText;

    @FindBy(id = Constant.ElementId.HOME_NAVIGATION_LINK)
    private WebElement homeNavigationLink;

    @FindBy(id = Constant.ElementId.ALL_ITEMS_NAVIGATION_LINK)
    private WebElement allItemsNavigationLink;

    @FindBy(id = Constant.ElementId.MENU_NAVIGATION_LINK)
    private WebElement menuNavigationLink;

    @FindBy(id = Constant.ElementId.WISHLIST_LINK)
    private WebElement wishlistLink;

    public Page initElements(final TestContext context) {
        ActionUtil.sleep();
        PageFactory.initElements(context.getDriver(), this);
        return this;
    }

    protected String getURL(final String routeName) {
        return BASIC_URL + routeName + "/" + LANG;
    }

    public WebElement getDetailsByText(final String text, final TestContext context) {
        final String xpath = getParentElementXpathByText(text, "details");
        return context.getDriver().findElement(By.xpath(xpath));
    }

    public WebElement getElementByText(final String text, final TestContext context) {
        return context.getDriver().findElement(By.xpath(getElementXpathByText(text)));
    }

    public String getParentElementXpathByText(final String text, String parent) {
        return getElementXpathByText(text) + "/parent::" + (parent == null ? "*" : parent);
    }

    public String getElementXpathByText(final String text) {
        return "//*[contains(text(), '" + text + "')]";
    }

    public WebElement getLoginButton(final TestContext context) {
        return context.getDriver().findElement(By.id(Constant.ElementId.LOGIN_BUTTON));
    }

    public WebElement getLogoutButton(final TestContext context) {
        return context.getDriver().findElement(By.id(Constant.ElementId.LOGOUT_BUTTON));
    }

}
