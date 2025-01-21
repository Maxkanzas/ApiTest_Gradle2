package pages;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProfilePage {
    public void openPageProfile() {
        open("/profile");
    }
    public void verifyTableContainsText(String text) {
        $(".ReactTable").shouldHave(text(text));
    }
}
