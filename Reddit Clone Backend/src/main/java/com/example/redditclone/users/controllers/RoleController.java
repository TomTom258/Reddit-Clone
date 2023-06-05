package com.example.redditclone.users.controllers;

import com.example.redditclone.dtos.ErrorResponseDto;
import com.example.redditclone.dtos.OkResponseDto;
import com.example.redditclone.dtos.ResponseDto;
import com.example.redditclone.dtos.RoleDto;
import com.example.redditclone.users.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @PostMapping("/")
    public ResponseEntity<ResponseDto> grantRoleToUser(@RequestBody RoleDto roleDto) {
        try {
            roleService.grantRoleToUser(roleDto.getRole(), roleDto.getRoleGrantedToUsername());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        String response = "%s has been successfully granted to user %s.".formatted(roleDto.getRole(), roleDto.getRoleGrantedToUsername());
        return new ResponseEntity<>(new OkResponseDto(201, response), HttpStatus.CREATED);
    }
}
