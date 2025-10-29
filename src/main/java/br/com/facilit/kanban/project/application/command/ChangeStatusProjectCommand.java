package br.com.facilit.kanban.project.application.command;

import br.com.facilit.kanban.project.domain.enums.StatusProject;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Comando responsável por alterar status do projeto.
 * Contém a estrutura de entrada (Input) necessária para a operação
 * e o retorno esperado após a conclusão da criação (Output).
 *
 * @author Antonio Neto
 */
public sealed interface ChangeStatusProjectCommand extends Serializable
        permits ChangeStatusProjectCommand.Input, ChangeStatusProjectCommand.Output {

    /**
     * Dados necessários para criar um novo projeto.
     * Representa uma ação do cliente para solicitar criação.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Input(
            UUID uuid,
            StatusProject status
    ) implements ChangeStatusProjectCommand {}

    /**
     * Dados retornados após a criação bem-sucedida de um projeto.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Output(
            String name,
            String status,
            UUID uuid
    ) implements ChangeStatusProjectCommand {}
}
