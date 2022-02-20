package ru.vtb.opera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import ru.vtb.opera.entities.Opera;
import ru.vtb.opera.service.OperaEmailService;
import ru.vtb.opera.service.OperaService;

import java.time.LocalDateTime;

@SpringBootApplication
@PropertySource("classpath:email.properties")
public class OperaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(OperaApplication.class, args);

        System.out.println("\n*** НАЧАЛО ***");
        OperaService operaService = ctx.getBean(OperaService.class);

        operaService.print();

        System.out.println("\n*** добавляем 'Ромео и Джульета'");
        operaService.add(new Opera("Ромео и Джульета", "романтика", LocalDateTime.of(2022, 04, 1, 11, 20),  16, 100, 0));
        operaService.print();

        String operaName = "name 5";
        System.out.println("\n*** изменяем описание у '" + operaName + "'");
        Opera opera = operaService.getOperaByName(operaName);
        if (opera != null) {
            opera.setDescription("измененное описание");
            operaService.edit(opera);
            operaService.print();
        } else {
            System.out.println("Оперы с именем '" + operaName + "' не существует");
        }

        System.out.println("\n*** удаляем name 2");
        operaService.delete("name 2");
        operaService.print();

        System.out.println("\n*** печатаем инфу по name 3");
        operaService.printByName("name 3");

        System.out.println("\n*** покупаем билет на 'name 1'");
        try {
            operaService.buyTicket("name 1");
            operaService.printByName("name 1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n*** сдаем билет на 'name 1'");
        try {
            operaService.returnTicket("name 1");
            operaService.printByName("name 1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        operaName = "name 1";
        System.out.println("\n*** меняем дату и время премьеры у '" + operaName + "'");
        operaService.printByName(operaName);
        operaService.changePlayDate(operaName, LocalDateTime.of(2022, 7, 8, 21, 45));
        operaService.printByName(operaName);

        System.out.println("\n*** КОНЕЦ ***");
    }
}
