package br.com.facilit.kanban.project.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Comando responsável por atualizar os projetos.
 * Contém a estrutura de entrada (Input) necessária para a operação
 * e o retorno esperado após a conclusão da criação (Output).
 *
 * @author Antonio Neto
 */
public sealed interface UpdateProjectCommand extends Serializable
        permits UpdateProjectCommand.Input, UpdateProjectCommand.Output {

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
            Instant expectedThermal,
            Instant expectedEnd,
            Instant startActual,
            Instant endActual,
            Instant thermalActual,
            Integer daysLate,
            Double percentageOfTimeRemaining,
            Long secretariatId
    ) implements UpdateProjectCommand {}

    /**
     * Dados retornados após a criação bem-sucedida de um projeto.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Output(
            String name,
            String status,
            UUID uuid
    ) implements UpdateProjectCommand {}
}
