package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.JavascriptExecutor;
import pageObjects.MainPage;
import pageObjects.OrderPage;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "Иван, Иванов, Тверская улица 1, Сокольники, 89001234567",
            "Петр, Петров, Арбат 10, Арбатская, 89998887766"
    })
    public void testOrderFromTopButton(String name, String surname, String address, String metro, String phone) {

        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
        mainPage.clickTopOrderButton();

        OrderPage orderPage = new OrderPage(driver);

        orderPage.fillFirstForm(name, surname, address, metro, phone);
        orderPage.clickNext();
        orderPage.fillSecondForm("25.06.2026");
        orderPage.clickOrder();
        orderPage.confirmOrder();

        assertTrue(orderPage.isOrderSuccessVisible());
    }

    @ParameterizedTest
    @CsvSource({
            "Анна, Смирнова, Тверская улица 3, Сокольники, 89112223344",
            "Олег, Кузнецов, Тверская улица 5, Охотный ряд, 89990001122"
    })
    public void testOrderFromBottomButton(String name, String surname, String address, String metro, String phone) {

        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2500)");
        mainPage.clickBottomOrderButton();

        OrderPage orderPage = new OrderPage(driver);

        orderPage.fillFirstForm(name, surname, address, metro, phone);
        orderPage.clickNext();
        orderPage.fillSecondForm("26.06.2026");
        orderPage.clickOrder();
        orderPage.confirmOrder();

        assertTrue(orderPage.isOrderSuccessVisible());
    }
}