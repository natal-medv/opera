package ru.vtb.opera.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Opera {
    String name;
    String description;
    int ageCategory;
    int allTicketsCount;
    int buyTicketsCount = 0;

    public String toString() {
        return "Наименование:" + name
                + "; Описание:" + description
                + "; Категория:" + ageCategory
                + "; Кол-во мест:" + allTicketsCount
                + "; Куплено билетов:" + buyTicketsCount;
    }
}
