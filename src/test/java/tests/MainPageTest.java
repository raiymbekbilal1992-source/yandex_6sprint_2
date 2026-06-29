package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pageobjects.MainPage;
import steps.MainPageSteps;

public class MainPageTest extends BaseTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    public void testFAQAnswerIsVisible(int index) {

        MainPage mainPage = new MainPage(driver);
        MainPageSteps steps = new MainPageSteps(driver);

        mainPage.acceptCookies();
        mainPage.clickQuestion(index);

        steps.checkAnswerNotEmpty(index);
    }
}
