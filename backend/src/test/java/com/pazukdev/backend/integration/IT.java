package com.pazukdev.backend.integration;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.core.TestContextImpl;
import com.pazukdev.backend.integration.testcore.page.GooglePage;
import com.pazukdev.backend.integration.testcore.page.HomePage;
import com.pazukdev.backend.integration.testcore.page.ItemPage;
import com.pazukdev.backend.integration.testcore.page.MenuPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.pazukdev.backend.integration.testcore.constant.Constant.TestData.*;
import static com.pazukdev.backend.integration.testcore.util.ActionUtil.getPage;
import static com.pazukdev.backend.integration.testcore.util.AsserterUtil.*;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(SpringRunner.class)
public class IT {

    private TestContext context;

    @Before
    public void createContext() {
        this.context = new TestContextImpl();
    }

    @Test
    public void basicWebdriverAndBrowserTest() {
        assertPageLoaded(context, getPage(context, new GooglePage()));
    }

    @Test
    public void getHomePageTest() {
        assertPageLoaded(context, getPage(context, new HomePage()));
    }

    @Test
    public void backButtonTest() {
        final HomePage homePage = getPage(context, new HomePage());
        homePage.getMenuNavigationLink().click();
        assertPageLoaded(context, new MenuPage());
        homePage.getButtonBack().click();
        assertPageLoaded(context, homePage);
    }

    @Test
    public void getLinkTest() {
        final HomePage homePage = getPage(context, new HomePage());
        homePage.getCopyUrlButton().click();
        assertValue(homePage.getCurrentLocationInput(), homePage.getUrl());
        assertText(homePage.getUrlCopiedText(), "Url copied to clipboard");
    }

    @Test
    public void openItemViaButtonTest() {
        final HomePage homePage = getPage(context, new HomePage());
        homePage.getDetailsByText(ITEM_MANUFACTURER, context).click();
        homePage.getElementByText(ITEM_NAME, context).click();
        assertPageLoaded(context, new ItemPage(), null, ITEM_NAME);
    }

    @Test
    public void openItemByCategoryAndNameInUrlTest() {
        final ItemPage itemPage = getPage(context, new ItemPage(ITEM_CATEGORY + "&" + ITEM_NAME));
        assertPageLoaded(context, itemPage, null, ITEM_NAME);
    }

    @Test
    public void loginTest() {
        final HomePage homePage = getPage(context, new HomePage());
        homePage.getLoginButton(context).click();

//        loginPage.getNicknameInput().sendKeys("user");
    }

    @Test
    public void continueAsGuestTest() {
        final HomePage homePage = getPage(context, new HomePage());
//        homePage.getLoginButton(context).click();
//        final LoginPage loginPage = new LoginPage();
//        loginPage.initElements(context);
//        sleep(2000);
        homePage.initElements(context);
        homePage.getCopyUrlButton().click();
//        loginPage.getSwitchLoginFormButton().click();
//        assertPageLoaded(context, new LoginPage());
//        loginPage.getNicknameInput().sendKeys("user");
    }

}

























