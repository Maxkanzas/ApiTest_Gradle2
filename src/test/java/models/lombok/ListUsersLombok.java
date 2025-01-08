package models.lombok;

import lombok.Data;

import java.util.List;

@Data
public class ListUsersLombok {
    int page, per_page, total, total_pages;
    List<DataUsers> data;
    Support support;
}
