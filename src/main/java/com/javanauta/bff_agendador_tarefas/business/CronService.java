package com.javanauta.bff_agendador_tarefas.business;

import com.javanauta.bff_agendador_tarefas.business.dto.in.LoginDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.out.TarefasDTOResponse;
import com.javanauta.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronService {
    private final TarefasService tarefasService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;
    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    public void buscarTarefasProximaHora() {
        String token = login(converterParaRequestDTO());
        log.info("Iniciada a busca de tarefas");
        LocalDateTime horaAtual = LocalDateTime.now().plusHours(1);
        LocalDateTime horaFuturaMaisCinco = LocalDateTime.now().plusHours(1).plusMinutes(5);

        List<TarefasDTOResponse> listaTarefas = tarefasService.buscarTarefasAgendadaPorPeriodo(horaAtual, horaFuturaMaisCinco, token);
        log.info("Tarefas encontradas" + listaTarefas);
        listaTarefas.forEach(tarefa ->
        {
            emailService.enviaEmail(tarefa);
            log.info("Email enviado para o usuario" + tarefa.getEmailUsuario());
            tarefasService.alterarStatus(StatusNotificacaoEnum.NOTIFICADO, tarefa.getId(), token)
            ;
        });
        log.info("Finalizada a busca de notificação de tarefas");
    }

    public String login(LoginDTORequest dto) {
        return usuarioService.loginUsuario(dto);
    }

    public LoginDTORequest converterParaRequestDTO() {
        return LoginDTORequest.builder()
                .email(email)
                .senha(senha)
                .build();
    }
}
