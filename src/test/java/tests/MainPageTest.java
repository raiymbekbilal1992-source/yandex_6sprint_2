package tests;

import io.qameta.allure.Step;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pageObjects.MainPage;

import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest extends BaseTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    public void testFAQAnswerIsVisible(int index) {
        MainPage page = openMainPage();
        clickQuestion(page, index);
        checkAnswer(page, index);
    }

    @Step("Открыть главную страницу и принять cookies")
    private MainPage openMainPage() {
        MainPage page = new MainPage(driver);
        page.acceptCookies();
        return page;
    }

    @Step("Нажать на вопрос №{index}")
    private void clickQuestion(MainPage page, int index) {
        page.clickQuestion(index);
    }

    @Step("Проверить: ответ на вопрос №{index} не пустой")
    private void checkAnswer(MainPage page, int index) {
        assertFalse(page.getAnswer(index).isEmpty(),
                "Ответ на вопрос " + index + " пустой или не открылся");
    }
}