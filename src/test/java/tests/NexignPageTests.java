package tests;

import com.codeborne.selenide.Condition;
import helpers.DriverUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URL;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UI-автотесты сайта компании Nexign")
public class NexignPageTests extends TestBase {

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Проверяем наличие логотоипа в хедере")
    @Link(value = "Testing URL", url = "https://nexign.com/ru")
    void FillFormTest() {
        step("Открываем страницу 'https://nexign.com/ru'", () -> open("https://nexign.com/ru"));
        step("Проверяем наличие изображения логотипа", () -> {
            $(".header__logo").$("img").should(Condition.exist);
            URL logoUrl = new URL($(".header__logo").$("img").getAttribute("src"));
            try (InputStream is = logoUrl.openConnection().getInputStream()) {
                Allure.addAttachment("Logo SVG file","image/svg+xml", is, "svg");
            }
        });
        step("Проверяем видимость изображения логотипа", () -> $(".header__logo").$("img").shouldBe(Condition.visible));
    }

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Журнал консоли страницы не должен содержать ошибок")
    @Link(value = "Testing URL", url = "https://nexign.com/ru")
    void consoleShouldNotHaveErrorsTest() {
        step("Открываем страницу 'https://nexign.com/ru'", () -> open("https://nexign.com/ru"));
        step("Журнал консоли не должен содержать текст 'SEVERE'", () -> {
            String consoleLogs = DriverUtils.getConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }
}
