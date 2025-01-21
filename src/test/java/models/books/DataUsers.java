package models.books;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataUsers {
    String username, password, userId, token, expires, isActive;
    @JsonProperty("created_date")
    String createdDate;
}
