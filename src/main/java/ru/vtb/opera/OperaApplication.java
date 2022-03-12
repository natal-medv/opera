package ru.vtb.opera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import ru.vtb.opera.entities.Opera;
import ru.vtb.opera.service.OperaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

@SpringBootApplication
@PropertySource("classpath:email.properties")
public class OperaApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(OperaApplication.class, args);

        OperaService operaService = ctx.getBean(OperaService.class);

        System.out.println("*** BEGIN ***");

        operaService.print();

/*
        System.out.println("\n*** добавление");
        operaService.addNew(new Opera("Кармен", "Жорж Бизе", LocalDateTime.of(2022, 11, 4, 11, 00),  16, 100, 0));
        operaService.print();

        System.out.println("\n*** ищем оперу по имени");
        List<Opera> operaByName = operaService.findByName("Лебединое озеро");
        operaByName.forEach(System.out::println);

        System.out.println("\n*** ищем оперу по like");
        List<Opera> operaLikeName = operaService.findLikeName("%а%");
        operaLikeName.forEach(System.out::println);

        System.out.println("\n*** ищем оперу по like 2");
        List<Opera> operaLikeNameDesc= operaService.findLikeNameDesc("%а%", "%пе%");
        operaLikeNameDesc.forEach(System.out::println);

        System.out.println("\n*** изменяем playDate по id");
        System.out.println(operaService.findById(1L));
        operaService.changePlayDate(1L, LocalDateTime.of(2022, 7, 8, 21, 45));
        System.out.println(operaService.findById(1L));

        System.out.println("\n*** добавление test 1");
        Opera operaTest = operaService.save(new Opera("test name", "test desc", LocalDateTime.of(2022, 04, 1, 11, 20),  16, 100, 0));
        operaService.print();
        System.out.println("***");
        operaService.delete(operaTest);
        operaService.print();

        System.out.println("\n*** добавление test 2");
        operaTest = operaService.save(new Opera("test 2 name", "test desc", LocalDateTime.of(2022, 04, 1, 11, 20),  16, 100, 0));
        operaService.print();
        System.out.println("***");
        operaService.deleteById(operaTest.getId());
        operaService.print();

        System.out.println("\n*** покупаем билет на id:2");
        operaService.print();
        operaService.buyTicket(2L);
        System.out.println("***");
        operaService.print();
*/

//        new Thread(()->{
//            System.out.println("\n*** сдаем билет на id:1");
//            operaService.printById(1L);
//            try {
//                operaService.returnTicket(1L);
//            } catch (Exception e) {
//                System.out.println("thread 1. Wait");
//                System.out.println(e.getMessage());
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//                System.out.println("thread 1. repeat");
//                operaService.returnTicket(1L);
//            }
//            operaService.printById(1L);
//        }).start();
//
//        new Thread(()->{
//            System.out.println("\n*** сдаем билет на id:1");
//            operaService.printById(1L);
//            try {
//                operaService.returnTicket(1L);
//            } catch (Exception e) {
//                System.out.println("thread 2. Wait");
//                System.out.println(e.getMessage());
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//                System.out.println("thread 2. repeat");
//                operaService.returnTicket(1L);
//            }
//            operaService.printById(1L);
//        }).start();


        System.out.println("\n*** сдаем билет на id:1");
        operaService.printById(1L);
        operaService.returnTicket(1L);
        operaService.printById(1L);

        System.out.println("*** END ***");
    }

    void returnTicket(Long id) {

    }
}
