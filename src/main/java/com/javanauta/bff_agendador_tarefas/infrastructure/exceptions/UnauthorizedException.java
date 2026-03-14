package com.javanauta.bff_agendador_tarefas.infrastructure.exceptions;

import javax.naming.AuthenticationException;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String mensagem) {super(mensagem);}

    public UnauthorizedException(String mensagem, Throwable throwable){super(mensagem, throwable);}
}
