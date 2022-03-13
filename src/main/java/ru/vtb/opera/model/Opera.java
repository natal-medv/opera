package ru.vtb.opera.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Opera {
    private Long id;
    String name;
    String description;
    LocalDateTime playDate;
    Integer ageCategory;
    Integer allTicketsCount;
    Integer buyTicketsCount;
}
