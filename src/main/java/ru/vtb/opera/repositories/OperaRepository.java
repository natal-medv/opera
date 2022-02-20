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
        operas = new ArrayList<>();  // !!! переделать на HashList или TreeList
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

    public boolean existOpera(String name) {
        long cnt = operas.stream().filter(o -> o.getName().equals(name)).count();
        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Opera getOperaByName(String name) {
        return operas.stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }

    public List<Opera> getAllOperas() {
        return operas;
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
