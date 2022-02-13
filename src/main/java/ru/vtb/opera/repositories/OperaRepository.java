package ru.vtb.opera.repositories;

import org.springframework.stereotype.Component;
import ru.vtb.opera.entities.Opera;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class OperaRepository {
    private List<Opera> operas;

    @PostConstruct
    public void init() {
        operas = new ArrayList<>();
        operas.add(new Opera("name 1", "descr 1", 6, 1, 0));
        operas.add(new Opera("name 2", "descr 2", 12, 40, 0));
        operas.add(new Opera("name 3", "descr 3", 18, 25, 0));
    }

    public void add(Opera opera) {
        operas.add(opera);
    }

    public void edit(Opera opera) {
        operas.set(operas.indexOf(getOperaByName(opera.getName())), opera);
    }

    public void delete(String name) {
        operas.remove(operas.indexOf(getOperaByName(name)));
    }

    public Opera getOperaByName(String name) {
        return operas.stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }

    public void buyTicket(String name) {
        Opera opera = getOperaByName(name);

        if (opera.getAllTicketsCount() - opera.getBuyTicketsCount() > 0) {
            opera.setAllTicketsCount(opera.getAllTicketsCount() - 1);
            opera.setBuyTicketsCount(opera.getBuyTicketsCount() + 1);
        } else {
            throw new IllegalArgumentException("Билеты все распроданы");
        }
    }

    public void returnTicket(String name) {
        Opera opera = getOperaByName(name);

        if (opera.getBuyTicketsCount() - opera.getAllTicketsCount() > 0) {
            opera.setAllTicketsCount(opera.getAllTicketsCount() + 1);
            opera.setBuyTicketsCount(opera.getBuyTicketsCount() - 1);
        } else {
            throw new IllegalArgumentException("Все билеты уже сданы");
        }
    }

    public void print() {
        for (Opera opera : operas) {
            System.out.println(opera);
        }
    }

    public void printByName(String name) {
        System.out.println(getOperaByName(name));
    }

}
