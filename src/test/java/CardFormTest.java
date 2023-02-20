import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.nio.channels.Selector;
import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardFormTest {
    @Test
    void shouldTestValid(){
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id=city] input").setValue ("Пермь");
        $("span[data-test-id=date] input").doubleClick();
        $("span[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("span[data-test-id=date] input").setValue("24.02.2023");
        $("span[data-test-id=name] input").setValue("Ольга Бражкина");
        $("span[data-test-id=phone] input").setValue("+71234567891");
        $("[data-test-id=agreement]").click();
        $x("//span[text()='Забронировать']").click();
        $x ("//div[contains(text(), 'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $x ("//div[contains(text(), 'Встреча успешно забронирована на ')]").shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void shouldWithoutCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Березники");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue("24.02.2023");
        $("[data-test-id=name] input").setValue("Ольга Бражкина");
        $("[data-test-id=phone] input").setValue("+71234567891");
        $("[data-test-id=agreement]").click();
        $x("//span[text()='Забронировать']").click();
        $x("//span[contains (text (), 'Доставка в выбранный город недоступна')]").shouldBe(appear);

    }

    @Test
    void shouldTestWithoutDate() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id=city] input").setValue("Пермь");
        $("span[data-test-id=date] input").doubleClick();
        $("span[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("span[data-test-id=name] input").setValue("Ольга Бражкина");
        $("span[data-test-id=phone] input").setValue("+71234567891");
        $("[data-test-id=agreement]").click();
        $x("//span[text()='Забронировать']").click();
        $x("//span[text()= 'Неверно введена дата']").shouldBe(visible);
    }

    @Test
    void shouldTestNotValidDate() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Пермь");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue("20.02.2023");
        $("[data-test-id=name] input").setValue("Ольга Бражкина");
        $("[data-test-id=phone] input").setValue("+71234567891");
        $("[data-test-id=agreement]").click();
        $x("//span[text()='Забронировать']").click();
        $x("//span[contains (text (), 'Заказ на выбранную дату')]").shouldBe(appear);

    }


    @Test
    void shouldLatinName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Пермь");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys("24.02.2023");
        $("[data-test-id=name] input").setValue("Olga Brazhkina");
        $("[data-test-id=phone] input").setValue("+79181102009");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $x("//span[contains (text (), 'Имя и Фамилия')]").shouldBe(appear);
    }

    @Test
    void shouldNameWithDash() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Пермь");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys("24.02.2023");
        $("[data-test-id=name] input").setValue("Ольга-Бражкина");
        $("[data-test-id=phone] input").setValue("+79181102009");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $x ("//div[contains(text(), 'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $x ("//div[contains(text(), 'Встреча успешно забронирована на ')]").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldWithoutName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Пермь");
        $("span[data-test-id=date] input").doubleClick();
        $("span[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("span[data-test-id=date] input").setValue("24.02.2023");
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79181102009");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=name] .input__sub").shouldBe(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotValidPhone() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id=city] input").setValue ("Пермь");
        $("span[data-test-id=date] input").doubleClick();
        $("span[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("span[data-test-id=date] input").setValue("24.02.2023");
        $("span[data-test-id=name] input").setValue("Ольга Бражкина");
        $("span[data-test-id=phone] input").setValue("+7123456789");
        $("[data-test-id=agreement]").click();
        $x("//span[text()='Забронировать']").click();
        $("[data-test-id=phone]").shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }


    @Test
    void shouldWithoutPhohe() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id=city] input").setValue ("Пермь");
        $("span[data-test-id=date] input").doubleClick();
        $("span[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("span[data-test-id=date] input").setValue("24.02.2023");
        $("span[data-test-id=name] input").setValue("Ольга Бражкина");
        $("span[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $x("//span[text()='Забронировать']").click();
        $x ("//span[contains (text (), 'Поле обязательно')]").shouldBe(visible);

    }


    @Test
    void shouldNoCheckbox() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id=city] input").setValue ("Пермь");
        $("span[data-test-id=date] input").doubleClick();
        $("span[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("span[data-test-id=date] input").setValue("24.02.2023");
        $("span[data-test-id=name] input").setValue("Ольга Бражкина");
        $("span[data-test-id=phone] input").setValue("+71234567891");
        $x("//span[text()='Забронировать']").click();
        $x ("//span[text()='Я соглашаюсь с условиями обработки и использования моих персональных данных']").shouldBe(appear);

    }
}
