import common.BaseClass;
import common.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Verify the Login Section")
public class LoginTest extends BaseClass {

    @Test
    @DisplayName("Enter valid UserId and Password")
    public void enterValidUserIdAndPassword() {
        WebElement userIdField = getDriver().findElement(By.name("uid"));
        userIdField.sendKeys(Configuration.EXPECTED_USERID);
        assertEquals(Configuration.EXPECTED_USERID, userIdField.getAttribute("value"), "Invalid userId");

        WebElement passwordField = getDriver().findElement(By.name("password"));
        passwordField.sendKeys(Configuration.EXPECTED_PASSWORD);
        assertEquals(Configuration.EXPECTED_PASSWORD, passwordField.getAttribute("value"), "Abc123");

        // Another way: password.submit()
        getDriver().findElement(By.name("btnLogin")).click();

        // Verify we are on the manager home page
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("marquee")));
        String expectedHeadingText = "Manger Id : " + Configuration.EXPECTED_USERID;
        WebElement heading = getDriver().findElement(By.cssSelector("tr.heading3 > td"));
        assertEquals(expectedHeadingText, heading.getText(), "Invalid heading text");

        assertEquals(Configuration.MANAGER_HOME_PAGE_TITLE, getDriver().getTitle(), "Invalid page title");
    }

    private static Stream<Arguments> provideArgumentForVerifyLogin() {
        return Stream.of(
                Arguments.of(Configuration.EXPECTED_USERID, Configuration.EXPECTED_PASSWORD,
                        "valid", Configuration.MANAGER_HOME_PAGE_TITLE),
                Arguments.of("TomSmith", Configuration.EXPECTED_PASSWORD,
                        "invalid", Configuration.HOME_PAGE_TITLE),
                Arguments.of(Configuration.EXPECTED_USERID, "Abc123",
                        "invalid", Configuration.HOME_PAGE_TITLE),
                Arguments.of("TomSmith", "invalid passowrd",
                        "invalid", Configuration.HOME_PAGE_TITLE)
        );
    }

    @DisplayName("Verifying login with both invalid and valid credentials")
    @ParameterizedTest(name = "{index} Login with username '{0}' and password '{1}' should be {2} login")
    @MethodSource("provideArgumentForVerifyLogin")
    public void verifyLogin(String username, String password, String expectedLoginResult, String expectedPageTitle) {
        getDriver().findElement(By.name("uid")).sendKeys(username);
        getDriver().findElement(By.name("password")).sendKeys(password);
        getDriver().findElement(By.name("btnLogin")).click();

        if (expectedLoginResult.equals("invalid")) {
            getWait().until(ExpectedConditions.alertIsPresent()).accept();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.barone")));
        }

        assertEquals(expectedPageTitle, getDriver().getTitle(), "Invalid page title");
    }

}