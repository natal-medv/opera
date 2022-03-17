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
    private String name;
    private String description;
    private LocalDateTime playDate;
    private Integer ageCategory;
    private Integer allTicketsCount;
    private Integer buyTicketsCount;
}
