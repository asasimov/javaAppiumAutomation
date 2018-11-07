import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject mainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testFindKeyword(){

        final String keyword = "Java";

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5);

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                keyword, "Cannot find search input",
                5);

        List<WebElement> listOfElements = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        for (WebElement item : listOfElements){
            Assert.assertTrue(
                    String.format("Title does not contain a keyword '%s'", keyword),
                    item.getText().contains(keyword));
        }
    }


    @Test
    public void testAssertTitle() {

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java", "Cannot find search input",
                5);

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic by 'Java'",
                5);

        mainPageObject.assertElementPresent(
                By.id("id:org.wikipedia:id/view_page_title_text"),
                "The article page does not contain a text title");
    }

}
