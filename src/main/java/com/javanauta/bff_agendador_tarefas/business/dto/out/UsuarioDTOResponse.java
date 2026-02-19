package com.javanauta.bff_agendador_tarefas.business.dto.out;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UsuarioDTOResponse {

    private String nome;
    private String senha;
    private String email;
    private List<EnderecoDTOResponse> enderecos;
    private List <TelefoneDTOResponse> telefones;

}
