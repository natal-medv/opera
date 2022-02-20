package ru.vtb.opera.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Data
public class Opera {
    String name;
    String description;
    LocalDateTime playDate;
    int ageCategory;
    int allTicketsCount;
    int buyTicketsCount = 0;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return "Наименование:" + name
                + "; Описание:" + description
                + "; Дата премьеры:" + playDate.format(formatter)
                + "; Категория:" + ageCategory
                + "; Кол-во мест:" + allTicketsCount
                + "; Куплено билетов:" + buyTicketsCount;
    }
}
