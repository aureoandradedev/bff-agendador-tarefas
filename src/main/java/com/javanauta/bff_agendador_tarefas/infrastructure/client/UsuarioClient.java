package com.javanauta.bff_agendador_tarefas.infrastructure.client;

import com.javanauta.bff_agendador_tarefas.business.dto.in.EnderecoDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.LoginDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.TelefoneDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.UsuarioDTORequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient (name = "usuario", url = "${usuario.url}")

public interface UsuarioClient {
    @GetMapping ("/usuario")
    UsuarioDTORequest buscaUsuarioPorEmail (@RequestParam("email") String email,
                                            @RequestHeader("Authorization")String token);


    @PostMapping
    UsuarioDTORequest salvaUsuario (@RequestBody UsuarioDTORequest usuarioDTO);

    @PostMapping("/login")
    String login(@RequestBody LoginDTORequest usuarioDTO);

    @DeleteMapping("/{email}")
    void deletaUsuarioPorEmail (@PathVariable String email,
                                @RequestHeader ("Authorization") String token);

    @PutMapping
    UsuarioDTORequest atualizaDadosUsuario (@RequestBody UsuarioDTORequest dto,
                                            @RequestHeader ("Authorization") String token);


    @PutMapping("/endereco")
    EnderecoDTORequest atualizaEndereco(@RequestBody EnderecoDTORequest dto, @RequestParam ("id")Long id,
                                        @RequestHeader ("Authorization") String token);

    @PutMapping("/telefone")
    TelefoneDTORequest atualizaTelefone (@RequestBody TelefoneDTORequest dto, @RequestParam ("id") Long id,
                                         @RequestHeader ("Authorization") String token);

    @PostMapping ("/endereco")
    EnderecoDTORequest cadastraEndereco (@RequestBody EnderecoDTORequest dto,
                                         @RequestHeader("Authorization") String token);

    @PostMapping ("/telefone")
    TelefoneDTORequest cadastraTelefone (@RequestBody TelefoneDTORequest dto,
                                         @RequestHeader ("Authorization") String token);

}