package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;



public class LoginPage {
    private static final SelenideElement loginField = $("[data-test-id=login] input");
    private static final SelenideElement passwordField = $("[data-test-id=password] input");
    private static final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public void ensureErrorNotification() {
        errorNotification.shouldBe(visible);
    }

    public static VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void cleanFields() {
        loginField.doubleClick();
        loginField.sendKeys(Keys.BACK_SPACE);
        passwordField.doubleClick();
        passwordField.sendKeys(Keys.BACK_SPACE);
    }
    public void getSystemBlocked() {
        errorNotification.shouldHave(Condition.text("Доступ заблокирован!")).shouldBe(Condition.visible);
    }
    public void notifButtonClick(){
        errorNotification.click();
    }
}
