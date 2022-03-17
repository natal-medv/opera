package ru.vtb.opera.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.vtb.opera.controllers.dto.OperaDto;
import ru.vtb.opera.controllers.dto.UserDto;
import ru.vtb.opera.model.ERole;
import ru.vtb.opera.model.Opera;
import ru.vtb.opera.repositories.RoleRepository;
import ru.vtb.opera.repositories.UserRepository;
import ru.vtb.opera.repositories.entities.RoleEntity;
import ru.vtb.opera.repositories.entities.UserEntity;
import ru.vtb.opera.service.OperaService;
import ru.vtb.opera.service.UserDetailsImpl;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/operas")
public class OperaController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    private OperaService operaService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public OperaController(OperaService operaService) {
        this.operaService = operaService;
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDto> authenticateUser(@RequestBody UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getName(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
        Set<String> roles = new HashSet<String>(userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()));
        userDto.setRoles(roles);
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: username is already taken");
        }

        Set<String> strRoles = userDto.getRoles();
        Set<RoleEntity> roles = new HashSet<>();
        if (strRoles == null) {
            Optional<RoleEntity> roleEntity = roleRepository.findByName(ERole.ROLE_USER);
            roles.add(roleEntity.get());
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Optional<RoleEntity> roleAdminEntity = roleRepository.findByName(ERole.ROLE_ADMIN);
                        roles.add(roleAdminEntity.get());
                        break;
                    default:
                        Optional<RoleEntity> roleUserEntity = roleRepository.findByName(ERole.ROLE_USER);
                        roles.add(roleUserEntity.get());
                }
            });
        }

        UserEntity userEntity = new UserEntity(
                userDto.getName(),
                encoder.encode(userDto.getPassword()),
                roles);
        userRepository.save(userEntity);
        return ResponseEntity.ok("User registered successfully");
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
    public String buyTicket(@RequestParam Long id) {
        return operaService.buyTicket(id);
    }

    @PutMapping("/returnTicket")
    public String returnTicket(@RequestParam Long id) {
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
