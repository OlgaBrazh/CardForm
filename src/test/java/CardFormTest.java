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
        $("span[data-test-id=date] input").setValue("21.02.2023");
        $("span[data-test-id=name] input").setValue("Ольга Бражкина");
        $("span[data-test-id=phone] input").setValue("+71234567891");
        $("[data-test-id=agreement]").click();
        $x("//span[text()='Забронировать']").click();
        $x ("//div[contains(text(), 'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $x ("//div[contains(text(), 'Встреча успешно забронирована на ')]").shouldBe(visible, Duration.ofSeconds(15));

    }
}
