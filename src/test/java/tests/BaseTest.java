package tests;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {

    protected WebDriver driver;
    protected static final String BASE_URL = "https://qa-scooter.education-services.ru/";

    @BeforeEach
    @Step("Запустить браузер и открыть сайт")
    public void setUp(TestInfo testInfo) {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    @Step("Закрыть браузер")
    public void tearDown(TestInfo testInfo) {
        if (driver != null) {
            takeScreenshot();
            driver.quit();
        }
    }

    @Attachment(value = "Скриншот", type = "image/png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}