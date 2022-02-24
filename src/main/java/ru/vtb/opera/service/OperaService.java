package ru.vtb.opera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vtb.opera.aspect.EmailAnnotation;
import ru.vtb.opera.repositories.OperaRepository;
import ru.vtb.opera.entities.Opera;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperaService {

    @Autowired
    private final OperaRepository operaRepository;

    public OperaService(OperaRepository operaRepository) {
        this.operaRepository = operaRepository;
    }

    public List<Opera> findAll() {
        return operaRepository.findAll();
    }

    public List<Opera> findLikeName(String name) {
        return operaRepository.findLikeName(name);
    }

    public List<Opera> findLikeNameDesc(String name, String desc) {
        return operaRepository.findLikeNameDesc(name, desc);
    }

    public List<Opera> findByName(String name) {
        return operaRepository.findByName(name);
    }

    public Optional<Opera> findById(Long id) {
        return operaRepository.findById(id);
    }

    public Opera save(Opera opera) {
        return operaRepository.save(opera);
    }

    @EmailAnnotation
    public Opera addNew(Opera opera) {
        return operaRepository.save(opera);
    }

    @EmailAnnotation
    public void changePlayDate(Long id, LocalDateTime dateTime) {
        Optional<Opera> opera = operaRepository.findById(id);

        if (opera.isPresent()) {
            opera.get().setPlayDate(dateTime);
            operaRepository.save(opera.get());
        } else {
            System.out.println("Данной оперы не существует");
        }
    }

    @EmailAnnotation
    public void buyTicket(Long id) {
        Optional<Opera> opera = operaRepository.findById(id);

        if (opera.isPresent()) {
            if (opera.get().getAllTicketsCount() - opera.get().getBuyTicketsCount() > 0) {
                opera.get().setBuyTicketsCount(opera.get().getBuyTicketsCount() + 1);
                operaRepository.save(opera.get());
            } else {
                System.out.println("Билеты все распроданы");
            }
        } else {
            System.out.println("Данной оперы не существует");
        }
    }

    public void returnTicket(Long id) {
        Optional<Opera> opera = operaRepository.findById(id);

        if (opera.isPresent()) {
            if (opera.get().getBuyTicketsCount() > 0) {
                opera.get().setBuyTicketsCount(opera.get().getBuyTicketsCount() - 1);
                operaRepository.save(opera.get());
            } else {
                System.out.println("Все билеты уже сданы");
            }
        } else {
            System.out.println("Данной оперы не существует");
        }
    }

    public void delete(Opera opera) {
        operaRepository.delete(opera);
    }

    public void deleteById(Long id) {
        operaRepository.deleteById(id);
    }

    public void print() {
        operaRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).forEach(System.out::println);
    }

}
