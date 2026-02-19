package com.javanauta.bff_agendador_tarefas.controller;


import com.javanauta.bff_agendador_tarefas.business.TarefasService;
import com.javanauta.bff_agendador_tarefas.business.dto.in.TarefasDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.out.TarefasDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import com.javanauta.bff_agendador_tarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
@Tag(name = "Tarefas" , description = "Cadastra tarefas de usuários")
public class TarefasController {
    private final TarefasService tarefasService;

    @PostMapping
    @Operation(summary = "Salvar tarefas de usuários", description = "Salvar uma nova tarefa")
    @ApiResponse(responseCode = "200" , description = "Tarefa salva com sucesso")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> gravarTarefas(@RequestBody TarefasDTORequest dto, @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(tarefasService.gravarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Buscar lista de tarefas de usuários por periodo", description = "Buscar tarefas cadastradas por periodo")
    @ApiResponse(responseCode = "200" , description = "Tarefas encontradas")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity<List<TarefasDTOResponse>> buscarListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(tarefasService.buscarTarefasAgendadaPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Buscar lista de tarefas de usuário por email", description = "Buscar tarefas cadastradas por email")
    @ApiResponse(responseCode = "200" , description = "Tarefas encontradas")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity<List<TarefasDTOResponse>> buscarTarefasPorEmail(@RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefasService.buscarTarefasPorEmail(token));
    }

    @DeleteMapping
    @Operation(summary = "Deleta tarefas por id", description = "Deleta tarefas cadastradas por Id")
    @ApiResponse(responseCode = "200" , description = "Tarefas deletadas")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id,
                                                  @RequestHeader(name = "Authorization", required = false) String token) {
        tarefasService.deletaTarefaPorId(id,token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Alterar status de tarefas", description = "Alterar status das tarefas cadastradas")
    @ApiResponse(responseCode = "200" , description = "Status da tarefa alterado")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> alterarStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                                                       @RequestParam("id") String id,
                                                                       @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefasService.alterarStatus(status, id,token));
    }

    @PutMapping
    @Operation(summary = "Alterar dados de tarefas", description = "Alterar dados das tarefas cadastradas")
    @ApiResponse(responseCode = "200" , description = "Tarefas alteradas")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> updateTarefas(@RequestBody TarefasDTORequest dto,
                                                            @RequestParam("id")String id,
                                                            @RequestHeader(name = "Authorization", required = false) String token){

    return ResponseEntity.ok(tarefasService.updateTarefas(dto, id, token));
    }
}
