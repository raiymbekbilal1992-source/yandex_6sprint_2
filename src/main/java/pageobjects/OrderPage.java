package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.List;

public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Первая форма
    private By name    = By.xpath("//input[@placeholder='* Имя']");
    private By surname = By.xpath("//input[@placeholder='* Фамилия']");
    private By address = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroInput  = By.className("select-search__input");
    private By metroList   = By.className("select-search__options");
    private By metroOption = By.xpath("//li[contains(@class,'select-search__row')]//button");
    private By phone   = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton  = By.xpath("//button[text()='Далее']");

    // Вторая форма
    private By date       = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rent       = By.className("Dropdown-control");
    private By rentOption = By.xpath("//div[contains(@class,'Dropdown-option')]");
    private By blackColor = By.id("black");
    private By comment    = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопки подтверждения и попап
    private By orderButton = By.xpath(
            "//div[contains(@class,'Order_Buttons__1xGrp')]//button[text()='Заказать']"
    );
    private By confirmButton = By.cssSelector("div.Order_Modal_YZ-d3 button:last-child");
    private By successPopup  = By.xpath("//div[contains(text(),'Заказ оформлен')]");

    public void fillFirstForm(String n, String s, String addr, String metroStation, String p) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(name)).sendKeys(n);
        driver.findElement(surname).sendKeys(s);
        driver.findElement(address).sendKeys(addr);

        selectMetro(metroStation);

        driver.findElement(phone).sendKeys(p);
    }

    /**
     * Вводит название станции в поле поиска метро и выбирает первый совпавший вариант.
     */
    private void selectMetro(String stationName) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(metroInput));
        input.click();
        input.sendKeys(stationName);

        // Ждём появления списка с вариантами
        wait.until(ExpectedConditions.visibilityOfElementLocated(metroList));

        // Ищем кнопку, текст которой содержит введённое название
        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(metroOption)
        );

        for (WebElement option : options) {
            if (option.getText().contains(stationName)) {
                option.click();
                return;
            }
        }

        // Фолбэк: кликаем первый элемент если точного совпадения нет
        options.get(0).click();
    }

    public void clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(date));
    }

    public void fillSecondForm(String dateValue) {
        wait.until(ExpectedConditions.elementToBeClickable(date)).click();
        driver.findElement(date).sendKeys(dateValue);

        // Кликаем на выбранный день в календаре (он подсветится)
        By selectedDay = By.xpath("//div[contains(@class,'react-datepicker__day--selected')]");
        wait.until(ExpectedConditions.elementToBeClickable(selectedDay)).click();

        wait.until(ExpectedConditions.elementToBeClickable(rent)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(rentOption)).click();

        driver.findElement(blackColor).click();
        driver.findElement(comment).sendKeys("Позвонить за час");
    }

    public void clickOrder() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

        // Ждём появления модалки после клика
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.Order_Modal__YZ-d3")
        ));
    }

    public void confirmOrder() {
        By buttons = By.cssSelector("button.Button_Button__ra12g.Button_Middle__1CSJM");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        List<WebElement> btns = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(buttons));

        for (WebElement btn : btns) {
            if (btn.getText().trim().equals("Да")) {
                btn.click();
                return;
            }
        }

        throw new RuntimeException("Кнопка 'Да' не найдена");
    }

    public boolean isOrderSuccessVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup)).isDisplayed();
    }
}