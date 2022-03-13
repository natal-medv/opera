package ru.vtb.opera.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vtb.opera.controllers.dto.ExceptionDto;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public ExceptionDto handler(Exception err) {
        return new ExceptionDto(23, "Упс, что-то пошло не так: " + err.getMessage());
    }
}
