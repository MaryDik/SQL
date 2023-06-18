package test;


import data.DataHelper;
import data.SQLHelper;


import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanDatabase;


public class LoginTest {

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    void shouldLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblyty();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void randomUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.ensureErrorNotification();
    }

    @Test
    void loginAttemptWithWrongPasswordBlock() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getIncorrectPassword();
        for (int i = 0; i < 3; i++) {
            loginPage.validLogin(authInfo);
            loginPage.ensureErrorNotification();
            loginPage.notifButtonClick();
            loginPage.cleanFields();
        }
        loginPage.validLogin(authInfo);
        loginPage.getSystemBlocked();
    }

}


