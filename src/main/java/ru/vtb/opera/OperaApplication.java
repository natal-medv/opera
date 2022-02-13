package ru.vtb.opera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.vtb.opera.entities.Opera;
import ru.vtb.opera.repositories.OperaRepository;

@SpringBootApplication
public class OperaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(OperaApplication.class, args);

        System.out.println("\n*** НАЧАЛО ***");
        OperaRepository operaRepository = ctx.getBean(OperaRepository.class);
        operaRepository.add(new Opera("Ромео и Джульета", "романтика", 16, 100, 0));
        operaRepository.print();

        System.out.println("\n*** получаем 'Ромео и Джульета'");
        Opera opera = operaRepository.getOperaByName("Ромео и Джульета");
        System.out.println(opera.toString());

        System.out.println("\n*** изменяем описание у 'name 1'");
        opera = operaRepository.getOperaByName("name 1");
        opera.setDescription("измененное описание");
        operaRepository.edit(opera);
        operaRepository.print();

        System.out.println("\n*** удаляем name 2");
        operaRepository.delete("name 2");
        operaRepository.print();

        System.out.println("\n*** печатаем инфу по name 3");
        operaRepository.printByName("name 3");

        System.out.println("\n*** покупаем билет на 'name 1'");
        try {
            operaRepository.buyTicket("name 1");
            operaRepository.printByName("name 1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n*** покупаем билет на 'name 1'");
        try {
            operaRepository.buyTicket("name 1");
            operaRepository.printByName("name 1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n*** сдаем билет на 'name 1'");
        try {
            operaRepository.returnTicket("name 1");
            operaRepository.printByName("name 1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n*** сдаем билет на 'name 1'");
        try {
            operaRepository.returnTicket("name 1");
            operaRepository.printByName("name 1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n*** КОНЕЦ ***");
    }


}
