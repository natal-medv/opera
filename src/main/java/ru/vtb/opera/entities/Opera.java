package ru.vtb.opera.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "opera")
public class Opera {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "play_date")
    LocalDateTime playDate;
    @Column(name = "age_category")
    Integer ageCategory;
    @Column(name = "all_tickets_count")
    Integer allTicketsCount;
    @Column(name = "buy_tickets_count")
    Integer buyTicketsCount;

    @Version
    private Integer version;

    public Opera() {
    }

    public Opera(String name, String description, LocalDateTime playDate, Integer ageCategory, Integer allTicketsCount, Integer buyTicketsCount) {
        this.name = name;
        this.description = description;
        this.playDate = playDate;
        this.ageCategory = ageCategory;
        this.allTicketsCount = allTicketsCount;
        this.buyTicketsCount = buyTicketsCount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPlayDate() {
        return playDate;
    }

    public void setPlayDate(LocalDateTime playDate) {
        this.playDate = playDate;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(int ageCategory) {
        this.ageCategory = ageCategory;
    }

    public int getAllTicketsCount() {
        return allTicketsCount;
    }

    public void setAllTicketsCount(int allTicketsCount) {
        this.allTicketsCount = allTicketsCount;
    }

    public int getBuyTicketsCount() {
        return buyTicketsCount;
    }

    public void setBuyTicketsCount(int buyTicketsCount) {
        this.buyTicketsCount = buyTicketsCount;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return "id:" + id
                + "; Наименование:" + name
                + "; Описание:" + description
                + "; Дата премьеры:" + playDate.format(formatter)
                + "; Категория:" + ageCategory + "+"
                + "; Кол-во мест:" + allTicketsCount
                + "; Куплено билетов:" + buyTicketsCount;
    }
}
