package ru.vtb.opera.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperaDto {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private LocalDateTime playDate;
    @JsonProperty
    private Integer ageCategory;
    @JsonProperty
    private Integer allTicketsCount;
    @JsonProperty
    private Integer buyTicketsCount;
}
