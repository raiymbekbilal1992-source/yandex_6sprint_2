package steps;

import org.openqa.selenium.WebDriver;
import pageobjects.MainPage;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MainPageSteps {

    private MainPage page;

    public MainPageSteps(WebDriver driver) {
        this.page = new MainPage(driver);
    }

    public void checkAnswerNotEmpty(int index) {
        assertFalse(page.getAnswer(index).isEmpty(),
                "Ответ на вопрос " + index + " пустой или не открылся");
    }
}
