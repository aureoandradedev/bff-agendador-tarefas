package com.javanauta.bff_agendador_tarefas.infrastructure.exceptions;

public class BusinessExcepiton extends RuntimeException {
    public BusinessExcepiton(String mensagem) {
        super(mensagem);
    }

    public BusinessExcepiton(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}
