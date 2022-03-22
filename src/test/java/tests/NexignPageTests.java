package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import helpers.AllureAttachments;
import helpers.DriverUtils;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UI-автотесты сайта компании Nexign")
public class NexignPageTests extends TestBase {

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

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Проверка наличия логотипа в хедере")
    @Link(value = "Testing URL", url = "https://nexign.com/ru")
    void logoVisibleTest() {
        step("Открываем страницу 'https://nexign.com/ru'", () -> open("https://nexign.com/ru"));
        step("Проверяем наличие изображения логотипа", () -> {
            $(".header__logo").$("img").should(Condition.exist);
            URL logoUrl = new URL($(".header__logo").$("img").getAttribute("src"));
            AllureAttachments.addSvgFromUrl("Logo SVG file", logoUrl);
        });
        step("Проверяем видимость изображения логотипа", () -> $(".header__logo").$("img").shouldBe(Condition.visible));
    }

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Проверка выбора страны в форме")
    @Link(value = "Testing URL", url = "https://nexign.com/ru")
    void webformCountrySelectTest() {
        step("Открываем страницу 'https://nexign.com/ru'", () -> open("https://nexign.com/ru"));
        step("Выбираем в списке страну 'Россия'", () -> $("#edit-country").selectOptionByValue("Россия"));
        step("Проверяем значение данного поля", () -> $("#edit-country").sibling(0)
                .$("[role=\"textbox\"]").shouldHave(Condition.text("Россия")));
    }

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Проверка ошибки отправки пустой формы")
    @Link(value = "Testing URL", url = "https://nexign.com/ru")
    void emptyWebformTest() {
        step("Открываем страницу 'https://nexign.com/ru'", () -> open("https://nexign.com/ru"));
        step("", () -> $("[value=\"Отправить\"]").scrollTo().click());
        step("", () -> {
            SelenideElement massageList = $(".messages__list").$("li");
            massageList.shouldHave(Condition.text("Поле \"Имя\" обязательно для заполнения."));
            massageList.sibling(0).shouldHave(Condition.text("Поле \"Фамилия\" обязательно для заполнения."));
            massageList.sibling(1).shouldHave(Condition.text("Поле \"Страна\" обязательно для заполнения."));
            massageList.sibling(2).shouldHave(Condition.text("Поле \"Компания\" обязательно для заполнения."));
            massageList.sibling(3).shouldHave(Condition.text("Поле \"Должность\" обязательно для заполнения."));
            massageList.sibling(4).shouldHave(Condition.text("Поле \"Рабочий email\" обязательно для заполнения."));
            massageList.sibling(5).shouldHave(Condition.text("Поле \"Телефон\" обязательно для заполнения."));
            massageList.sibling(6).shouldHave(Condition.text("Поле \"Даю согласие на обработку персональных данных\" обязательно для заполнения"));
        });
    }

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Проверка поиска публикаций пресс-центра")
    @Link(value = "Testing URL", url = "https://nexign.com/ru")
    void articleSearchTest() {
        step("Открываем страницу 'https://nexign.com/ru'", () -> open("https://nexign.com/ru"));
        step("Нажимаем на иконку поиска", () -> $(".header__search").click());
        step("Вводим текст в поле поиска", () -> $("[placeholder=\"Поиск\"]").setValue("30 лет Nexign").pressEnter());
        step("Переходим по первой ссылке", () -> $(".line-group-search").click());
        step("Проеряем, что такой текст есть на странице", () -> $(".page-content").shouldHave(Condition.text("30 лет Nexign")));
    }
}
