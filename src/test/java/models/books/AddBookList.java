package models.books;

import lombok.Data;

import java.util.List;

@Data
public class AddBookList {
        String userId;
        List<Isbn> collectionOfIsbns;
}
