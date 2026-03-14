package com.javanauta.bff_agendador_tarefas.infrastructure.exceptions;

public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String mensagem) {
        super(mensagem);
    }
    public IllegalArgumentException(String mensagem, Throwable throwable){
        super(mensagem,throwable);
    }
}
