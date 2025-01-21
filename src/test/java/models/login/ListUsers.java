package models.login;

import lombok.Data;

import java.util.List;

@Data
public class ListUsers {
    int page, per_page, total, total_pages;
    List<Credentials> data;
    Support support;
}
