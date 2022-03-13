package ru.vtb.opera.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionDto {
    @JsonProperty
    private Integer errorCode;
    @JsonProperty
    private String message;
}
