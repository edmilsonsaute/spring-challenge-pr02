package com.challenge.project02.features.user.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.project02.features.user.DTOs.UserRequestDTO;
import com.challenge.project02.features.user.DTOs.UserResponseDTO;
import com.challenge.project02.features.user.DTOs.UserUpdateDTO;
import com.challenge.project02.features.user.entities.Role;
import com.challenge.project02.features.user.entities.RoleName;
import com.challenge.project02.features.user.entities.User;
import com.challenge.project02.features.user.repositories.RoleRepository;
import com.challenge.project02.features.user.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        Role role = roleRepository.findByName(RoleName.REGULAR).orElseThrow(() -> new RuntimeException("Role REGULAR nao encontrada"));

        User user = new User();
        user.setUsername(userRequestDTO.username());
        user.setEmail(userRequestDTO.email());
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getUsername(),
                user.getEmail(), user.getCreatedAt());

        return userResponseDTO;
    }

    public List<UserResponseDTO> listarUsuarios(){

        List<User> lista = userRepository.findAll();

        if(lista == null){
            new RuntimeException("Sem dados");
        }

        List<UserResponseDTO> usuarios = new ArrayList<>();

        for(User user : lista){
            usuarios.add(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt()));
        }

        return usuarios;
    }

    public UserResponseDTO obterUsuarioViaId(UUID id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());

        return userResponseDTO;
    }

    @Transactional
    public void apagarUsuario(UUID id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        userRepository.delete(user);;
    }

    @Transactional
    public UserResponseDTO atualizarUser(UUID id,UserUpdateDTO userUpdateDTO){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        if(userUpdateDTO.email() != null){
            user.setEmail(userUpdateDTO.email());
        }

        if(userUpdateDTO.username() != null){
            user.setUsername(userUpdateDTO.username());
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());

        userRepository.save(user);

        return userResponseDTO;
    }
}
