package br.com.facilit.kanban.project.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Comando responsável por listar os projetos.
 * Contém a estrutura de entrada (Input) necessária para a operação
 * e o retorno esperado após a conclusão da criação (Output).
 *
 * @author Antonio Neto
 */
public sealed interface ListProjectCommand extends Serializable
        permits ListProjectCommand.Input, ListProjectCommand.Output {

    /**
     * Dados necessários para criar um novo projeto.
     * Representa uma ação do cliente para solicitar criação.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Input(
            int pageNumber,
            int pageSize,
            Sort sort
    ) implements ListProjectCommand {}

    /**
     * Dados retornados após a criação bem-sucedida de um projeto.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Output(
            String name,
            String status,
            UUID uuid,
            Instant expectedStart,
            Instant expectedEnd,
            Instant startActual,
            Instant endActual,
            Integer daysLate,
            Double percentageOfTimeRemaining
    ) implements ListProjectCommand {}
}
