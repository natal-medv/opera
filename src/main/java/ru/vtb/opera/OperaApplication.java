package ru.vtb.opera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.vtb.opera.entities.Opera;
import ru.vtb.opera.model.OperaService;
import ru.vtb.opera.repositories.OperaRepository;

@SpringBootApplication
public class OperaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(OperaApplication.class, args);

        System.out.println("\n*** НАЧАЛО ***");
        OperaService operaService = ctx.getBean(OperaService.class);

        operaService.print();

        System.out.println("\n*** добавляем 'Ромео и Джульета'");
        operaService.add(new Opera("Ромео и Джульета", "романтика", 16, 100, 0));
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

        System.out.println("\n*** сдаем билет на 'name 1'");
        try {
            operaService.returnTicket("name 1");
            operaService.printByName("name 1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n*** КОНЕЦ ***");
    }
}
