package tests.demoqabookstore;

import api.BookApiSteps;
import models.books.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static io.qameta.allure.Allure.step;

public class CollectionTests extends TestBase {
    ProfilePage profilePage = new ProfilePage();
    BookApiSteps bookApiSteps = new BookApiSteps();
    AddBookList addBookList = new AddBookList();
    DataUsers dataUsers = new DataUsers();

    @Test
    @DisplayName("Авторизация, удаление всех книг из корзины и добавление новых, UI проверка книг в профиле")
    public void addBookToCollectionDeletedAllBooksTest() {
        step("Удаление всех книг из корзины", () -> {
            bookApiSteps.deleteAllBooks(dataUsers);
        });
        step("Добавление новых книг в корзину", () -> {
            bookApiSteps.addBook(dataUsers, addBookList);
        });
        step("Проверяем наличие добавленной книги в корзине", () -> {
            profilePage.openPageProfile();
            profilePage.verifyTableContainsText("Speaking JavaScript");
        });
    }

    @Test
    @DisplayName("Авторизация, удаление всех книг, добавление новых, UI проверка книг в профиле, удаление одной любой книги")
    public void addBookToCollectionwithDeleteOneBookTest() {
        step("Удаление всех книг из корзины", () -> {
            bookApiSteps.deleteAllBooks(dataUsers);
        });
        step("Добавление новых книг в корзину", () -> {
            bookApiSteps.addBook(dataUsers, addBookList);
        });
        step("Проверяем наличие добавленной книги в корзине", () -> {
            profilePage.openPageProfile();
            profilePage.verifyTableContainsText("Programming JavaScript Applications");
        });
        step("Удаление одной книги из корзины", () -> {
            bookApiSteps.deleteBook(dataUsers, "Programming JavaScript Applications");
        });

    }
}
