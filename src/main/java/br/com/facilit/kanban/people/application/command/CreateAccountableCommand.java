package br.com.facilit.kanban.people.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Comando responsável pela criação de um responsável.
 * Contém a estrutura de entrada (Input) necessária para a operação
 * e o retorno esperado após a conclusão da criação (Output).
 *
 * @author Antonio Neto
 */
public sealed interface CreateAccountableCommand extends Serializable
        permits CreateAccountableCommand.Input, CreateAccountableCommand.Output {

    /**
     * Dados necessários para criar um novo projeto.
     * Representa uma ação do cliente para solicitar criação.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Input(
             String name,
             String email,
             String role,
             Long secretariatId
    ) implements CreateAccountableCommand {}

    /**
     * Dados retornados após a criação bem-sucedida de um projeto.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Output(
            UUID uuid,
            String name
    ) implements CreateAccountableCommand {}
}
