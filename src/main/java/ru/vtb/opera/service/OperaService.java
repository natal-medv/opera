package ru.vtb.opera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vtb.opera.aspect.EmailAnnotation;
import ru.vtb.opera.entities.Opera;
import ru.vtb.opera.repositories.OperaRepository;

import java.time.LocalDateTime;

@Service
public class OperaService {
    @Autowired
    OperaRepository operaRepository;

    @EmailAnnotation
    public void add(Opera opera) {
        operaRepository.add(opera);
    }

    public void edit(Opera opera) {
        operaRepository.edit(opera);
    }

    @EmailAnnotation
    public void changePlayDate(String name, LocalDateTime dateTime) {
        if (operaRepository.existOpera(name)) {
            Opera opera = operaRepository.getOperaByName(name);
            opera.setPlayDate(dateTime);
            operaRepository.edit(opera);
        } else {
            System.out.println("Данной оперы не существует");
        }
    }

    public Opera getOperaByName(String name) {
        if (operaRepository.existOpera(name)) {
            return operaRepository.getOperaByName(name);
        } else {
            return null;
        }
    }

    public void delete(String name) {
        if (operaRepository.existOpera(name)) {
            operaRepository.delete(name);
        } else {
            System.out.println("Данной оперы не существует");
        }
    }

    public void printByName(String name) {
        if (operaRepository.existOpera(name)) {
            operaRepository.printByName(name);
        } else {
            System.out.println("Данной оперы не существует");
        }
    }

    @EmailAnnotation
    public void buyTicket(String name) {
        Opera opera = getOperaByName(name);
        if (opera == null) {
            System.out.println("Данной оперы не существует");
        } else {
            if (opera.getAllTicketsCount() - opera.getBuyTicketsCount() > 0) {
                opera.setAllTicketsCount(opera.getAllTicketsCount() - 1);
                opera.setBuyTicketsCount(opera.getBuyTicketsCount() + 1);
                operaRepository.edit(opera);
            } else {
                throw new IllegalArgumentException("Билеты все распроданы");
            }
        }
    }

    public void returnTicket(String name) {
        Opera opera = getOperaByName(name);
        if (opera == null) {
            System.out.println("Данной оперы не существует");
        } else {
            if (opera.getBuyTicketsCount() - opera.getAllTicketsCount() > 0) {
                opera.setAllTicketsCount(opera.getAllTicketsCount() + 1);
                opera.setBuyTicketsCount(opera.getBuyTicketsCount() - 1);
                operaRepository.edit(opera);
            } else {
                throw new IllegalArgumentException("Все билеты уже сданы");
            }
        }
    }

    public void print() {
        for (Opera opera : operaRepository.getAllOperas()) {
            System.out.println(opera);
        }
    }

}
