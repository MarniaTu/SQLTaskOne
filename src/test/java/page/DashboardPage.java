package page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id='dashboard']");

    public void verifyDashboardPageIsVisible() {
        heading.shouldHave(text(" Личный кабинет")).shouldBe(visible, Duration.ofSeconds(25));
    }
}
