package br.com.facilit.kanban.project.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Comando responsável pela criação de um projeto.
 * Contém a estrutura de entrada (Input) necessária para a operação
 * e o retorno esperado após a conclusão da criação (Output).
 *
 * @author Antonio Neto
 */
public sealed interface CreateProjectCommand extends Serializable
        permits CreateProjectCommand.Input, CreateProjectCommand.Output {

    /**
     * Dados necessários para criar um novo projeto.
     * Representa uma ação do cliente para solicitar criação.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Input(
            String name,
            String status,
            UUID uuid,
            Instant expectedStart,
            Instant expectedEnd,
            Instant startActual,
            Instant endActual,
            Integer daysLate,
            Double percentageOfTimeRemaining,
            Long secretariatId
    ) implements CreateProjectCommand {}

    /**
     * Dados retornados após a criação bem-sucedida de um projeto.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Output(
            String name,
            String status,
            UUID uuid
    ) implements CreateProjectCommand {}
}
