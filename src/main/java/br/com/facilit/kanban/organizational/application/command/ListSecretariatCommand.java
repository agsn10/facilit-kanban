package br.com.facilit.kanban.organizational.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Sort;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Comando responsável por representar a operação de listagem paginada
 * das Secretarias dentro do sistema Kanban, conforme princípios de
 * Arquitetura Hexagonal (Ports & Adapters).
 *
 * <p>Este comando define as estruturas de entrada e saída necessárias
 * para a execução do caso de uso de consulta paginada de Secretarias,
 * permitindo encapsular os parâmetros e o resultado obtido do banco.</p>
 *
 * @see ListSecretariatCommand.Input
 * @see ListSecretariatCommand.Output
 *
 * @author Antonio Neto
 */
public sealed interface ListSecretariatCommand extends Serializable
        permits ListSecretariatCommand.Input, ListSecretariatCommand.Output {

    /**
     * Estrutura que representa os parâmetros necessários para realização
     * de uma consulta paginada de Secretarias.
     *
     * @param pageNumber Número da página solicitada (0-based)
     * @param pageSize   Quantidade de registros por página
     * @param sort       Parâmetros de ordenação
     */
    @Schema(name = "ListSecretariatInput",
            description = "Parâmetros de entrada para consulta paginada de Secretarias")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Input(
            int pageNumber,
            int pageSize,
            Sort sort
    ) implements ListSecretariatCommand {}

    /**
     * Estrutura que representa o retorno da operação de consulta paginada
     * das Secretarias. Contém a lista de dados obtidos, bem como os metadados
     * de paginação.
     *
     * @param content        Lista de resultados mapeados para resposta
     * @param totalElements  Quantidade total de registros existentes
     * @param totalPages     Número total de páginas disponíveis
     */
    @Schema(name = "ListSecretariatOutput",
            description = "Resultado da consulta paginada de Secretarias")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Output(
            UUID uuid,
            String name,
            String description
    ) implements ListSecretariatCommand {}
}
