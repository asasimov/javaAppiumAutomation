package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch(){

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        searchPageObject.typeSearchLine(search_line);
        int amount_search_of_result = searchPageObject.getAmountOfFoundArticles();

        assertTrue("We found to few results!", amount_search_of_result > 0);
    }

    @Test
    public void testAmountOfEmptySearch(){

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        String search_line = "awfafdytagag";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testFindKeyword(){

        final String keyword = "Java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(keyword);
        int resultCounter = searchPageObject.getAmountOfFoundArticles();

        assertTrue("Too few search results", resultCounter > 1);

        List<WebElement> listOfElements = searchPageObject.searchResultTitles();
        for (WebElement item : listOfElements) {
            assertTrue(
                    String.format("Search result doesn't contains string '%s'", keyword),
                    item.getText().contains(keyword));
        }
    }

    @Test
    public void testSearchByTitleAndDescription() {
        String searchText = "Java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchText);

        Map<String, String> results = new HashMap<>();
        results.put("Java version history", "Wikimedia list article");
        results.put("Java (programming language)", "Object-oriented programming language");
        results.put("Java (software platform)", "Set of several computer software products and specifications");

        for (Map.Entry<String, String> result : results.entrySet()) {
            searchPageObject.waitForElementByTitleAndDescription(result.getKey(), result.getValue());
        }
    }

}
