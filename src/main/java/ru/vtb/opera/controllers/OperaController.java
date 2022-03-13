package ru.vtb.opera.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vtb.opera.controllers.dto.OperaDto;
import ru.vtb.opera.model.Opera;
import ru.vtb.opera.repositories.entities.OperaEntity;
import ru.vtb.opera.service.OperaService;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/operas")
public class OperaController {

    private OperaService operaService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public OperaController(OperaService operaService) {
        this.operaService = operaService;
    }

    @GetMapping("/all")
    public Collection<OperaDto> findAll() {
        List<Opera> operas = operaService.findAll();
        return operas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    public Collection<OperaDto> findLikeName(@RequestParam String name) {
        List<Opera> operas = operaService.findLikeName(name);
        return operas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OperaDto get(@PathVariable("id") Long id) {
        return convertToDto(operaService.findById(id));
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperaDto> createOpera(@RequestBody OperaDto operaDto) {
        Opera operaRequest = convertToDomain(operaDto);
        Opera opera = operaService.addNew(operaRequest);
        OperaDto operaDtoResponse = convertToDto(opera);
        return ResponseEntity.ok().body(operaDtoResponse);
    }

    @PutMapping("/changeDate")
    public ResponseEntity<OperaDto> changeDate(@RequestBody OperaDto operaDto) {
        Opera opera = operaService.changePlayDate(operaDto.getId(), operaDto.getPlayDate());
        OperaDto operaDtoResponse = convertToDto(opera);
        return ResponseEntity.ok().body(operaDtoResponse);
    }

    @PutMapping("/buyTicket")
    public String buyTicket (@RequestParam Long id) {
        return operaService.buyTicket(id);
    }

    @PutMapping("/returnTicket")
    public String returnTicket (@RequestParam Long id) {
        return operaService.returnTicket(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        operaService.deleteById(id);
    }

    private OperaDto convertToDto(Opera opera) {
        OperaDto operaDto = modelMapper.map(opera, OperaDto.class);
        return operaDto;
    }

    private Opera convertToDomain(OperaDto operaDto) {
        Opera opera = modelMapper.map(operaDto, Opera.class);
        return opera;
    }
}
