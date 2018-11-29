package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";
    private static final String login = "TestUser92";
    private static final String password = "testUser93";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();

        String article_title = articlePageObject.getArticleTitle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);

        if(Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else if (Platform.getInstance().isIOS()){
            articlePageObject.addArticleToMySaved();
            articlePageObject.clickCloseButton();
        } else {

            navigationUI.openNavigation();

            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login.",
                    article_title,
                    articlePageObject.getArticleTitle());

            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
        }
        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticles() {
        String first_article_title = "Java (programming language)";
        String second_article_title = "JavaScript";
        String first_search_text = "Java";
        String second_search_text = "JavaScript";

        NavigationUI navigationUI = NavigationUIFactory.get(driver);

        if (Platform.getInstance().isMW()){
            navigationUI.openNavigation();

            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(first_search_text);
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();

        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToMyList(name_of_folder);
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.addArticleToMySaved();
            articlePageObject.clickCloseButton();
        } else {
            articlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login.",
                    article_title,
                    articlePageObject.getArticleTitle());

            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(second_search_text);
        searchPageObject.clickByArticleWithSubstring("rogramming language");

        articlePageObject.waitForTitleElement();
        article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToMySavedList();
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.addArticleToMySaved();
        } else {
            articlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login.",
                    article_title,
                    articlePageObject.getArticleTitle());

            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()){
            myListsPageObject.openFolderByName(name_of_folder);
        }

        myListsPageObject.swipeByArticleToDelete(first_article_title);

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            myListsPageObject.openArticleFromSavedFolderByTitle(second_article_title);
            String title = articlePageObject.getArticleTitle();
            assertEquals("Wrong article title", second_article_title, title);
        } else {
            myListsPageObject.openArticleFromSavedFolderByTitle(second_article_title);
            assertTrue("Saved article is not present in my list.", articlePageObject.articlePresentInMyList());
        }
    }

}
