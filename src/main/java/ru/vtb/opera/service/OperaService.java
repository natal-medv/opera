package ru.vtb.opera.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.opera.aspect.EmailAnnotation;
import ru.vtb.opera.controllers.dto.OperaDto;
import ru.vtb.opera.model.Opera;
import ru.vtb.opera.repositories.OperaRepository;
import ru.vtb.opera.repositories.entities.OperaEntity;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
public class OperaService implements ApplicationContextAware {
    private ApplicationContext ctx;
    private final OperaRepository operaRepository;
    private ModelMapper modelMapper;

    @Autowired
    public OperaService(OperaRepository operaRepository, ModelMapper modelMapper) {
        this.operaRepository = operaRepository;
        this.modelMapper = modelMapper;
    }

    public List<Opera> findAll() {
        return operaRepository.findAll().stream()
                .map(operaEntity -> modelMapper.map(operaEntity, Opera.class))
                .collect(Collectors.toList());
    }

    public List<Opera> findLikeName(String name) {
        return operaRepository.findLikeName(name).stream()
                .map(operaEntity -> modelMapper.map(operaEntity, Opera.class))
                .collect(Collectors.toList());
    }

    public Opera findById(Long id) {
        if (operaRepository.findById(id).isPresent()) {
            return modelMapper.map(operaRepository.findById(id).get(), Opera.class);
        } else {
            return null;
        }
    }

    public OperaEntity save(OperaEntity opera) {
        return operaRepository.save(opera);
    }

//    @EmailAnnotation
    public Opera addNew(Opera opera) {
        OperaEntity operaEntity = modelMapper.map(opera, OperaEntity.class);
        return modelMapper.map(operaRepository.save(operaEntity), Opera.class);
    }

    //    @EmailAnnotation
    public Opera changePlayDate(Long id, LocalDateTime dateTime) {
        Optional<OperaEntity> opera = operaRepository.findById(id);

        if (opera.isPresent()) {
            opera.get().setPlayDate(dateTime);
            return modelMapper.map(operaRepository.save(opera.get()), Opera.class);
        } else {
            System.out.println("Данной оперы не существует");
            return null;
        }
    }

//    @EmailAnnotation
    @Transactional (
            propagation = REQUIRED,
            isolation = Isolation.REPEATABLE_READ,
            readOnly = false
    )
    public String buyTicket(Long id) {
        Optional<OperaEntity> opera = operaRepository.findById(id);

        if (opera.isPresent()) {
            if (opera.get().getAllTicketsCount() - opera.get().getBuyTicketsCount() > 0) {
                opera.get().setBuyTicketsCount(opera.get().getBuyTicketsCount() + 1);
                operaRepository.save(opera.get());
            } else {
                return "Билеты все распроданы";
            }
        } else {
            return "Данной оперы не существует";
        }

        return null;
    }

    @Transactional (
            propagation = REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false
    )
    public String returnTicket(Long id) {
        try {

            OperaEntity opera = operaRepository.getForUpdate(id);
            if (opera.getBuyTicketsCount() > 0) {
                opera.setBuyTicketsCount(opera.getBuyTicketsCount() - 1);
            } else {
                return "Все билеты уже сданы";
            }
            operaRepository.save(opera);
        } catch (NullPointerException e) {
            return "Опера с id:" + id + " не найдена";
        } catch (DataAccessException e) {
            return "Объект уже был изменен!";
        }

        return null;
    }

    public void deleteById(Long id) {
        operaRepository.deleteById(id);
    }

    public void print() {
        operaRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).forEach(System.out::println);
    }

    public void printById(Long id) {
        System.out.println(findById(id).toString());
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }

}
