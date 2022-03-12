package ru.vtb.opera.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.opera.aspect.EmailAnnotation;
import ru.vtb.opera.repositories.OperaRepository;
import ru.vtb.opera.entities.Opera;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
public class OperaService implements ApplicationContextAware {
    private ApplicationContext ctx;
    private final OperaRepository operaRepository;

    @Autowired
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
    @Transactional (
            propagation = REQUIRED,
            isolation = Isolation.REPEATABLE_READ,
            readOnly = false
    )
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

    @Transactional (
            propagation = REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false
    )
    public void returnTicket(Long id) {
        try {

            Opera opera = operaRepository.getForUpdate(id);
            System.out.println("объект получен");
            if (opera.getBuyTicketsCount() > 0) {
                opera.setBuyTicketsCount(opera.getBuyTicketsCount() - 1);
            } else {
                System.out.println("Все билеты уже сданы");
            }
            operaRepository.save(opera);
            System.out.println("объект записан");

        } catch (NullPointerException e) {
            System.out.println("Опера с id:" + id + " не найдена");
        } catch (DataAccessException e) {
            System.out.println("Объект уже был изменен!");
        }

//        Optional<Opera> opera = operaRepository.findById(id);
//
//        if (opera.isPresent()) {
//            if (opera.get().getBuyTicketsCount() > 0) {
//                opera.get().setBuyTicketsCount(opera.get().getBuyTicketsCount() - 1);
//                operaRepository.save(opera.get());
//            } else {
//                System.out.println("Все билеты уже сданы");
//            }
//        } else {
//            System.out.println("Данной оперы не существует");
//        }
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

    public void printById(Long id) {
        System.out.println(findById(id).get().toString());
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }
}
