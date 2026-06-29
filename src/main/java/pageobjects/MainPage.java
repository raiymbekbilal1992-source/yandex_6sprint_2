package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Кнопка принятия cookies
    private By cookieButton = By.id("rcc-confirm-button");

    // Кнопки «Заказать» (верхняя и нижняя)
    private By orderButtons = By.xpath("//button[text()='Заказать']");

    // Заголовки вопросов FAQ
    private String questionId = "accordion__heading-";

    // Панели ответов FAQ
    private String answerId = "accordion__panel-";

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void acceptCookies() {
        List<WebElement> cookies = driver.findElements(cookieButton);
        if (!cookies.isEmpty()) {
            cookies.get(0).click();
        }
    }

    public void clickTopOrderButton() {
        driver.findElements(orderButtons).get(0).click();
    }

    public void clickBottomOrderButton() {
        driver.findElements(orderButtons).get(1).click();
    }

    public void scrollToBottomOrderButton() {
        WebElement bottomButton = driver.findElements(orderButtons).get(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", bottomButton);
    }

    public void clickQuestion(int index) {
        WebElement question = driver.findElement(By.id(questionId + index));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);
        question.click();
    }

    public String getAnswer(int index) {
        By answerLocator = By.id(answerId + index);
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBe(answerLocator, "")
        ));
        return driver.findElement(answerLocator).getText();
    }
}