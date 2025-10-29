package br.com.facilit.kanban.organizational.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.UUID;

/**
 * Comando responsável pelo fluxo de criação de uma Secretaria na aplicação.
 *
 * <p>Este comando faz parte da Application Layer da Arquitetura Hexagonal
 * e representa a estrutura dos dados recebidos (Input) e enviados (Output)
 * no caso de uso {@code CreateSecretariatUseCase}.</p>
 *
 * <ul>
 *     <li><b>Input:</b> Dados necessários para criar uma nova Secretaria</li>
 *     <li><b>Output:</b> Dados retornados após a criação bem-sucedida</li>
 * </ul>
 *
 * @author Antonio Neto
 */
public sealed interface CreateSecretariatCommand extends Serializable
        permits CreateSecretariatCommand.Input, CreateSecretariatCommand.Output {

    /**
     * Estrutura de dados de entrada para criação de Secretaria.
     *
     * <p>Representa os atributos mínimos exigidos pela regra de negócio
     * para persistência do registro.</p>
     *
     * @param name        Nome da Secretaria (obrigatório)
     * @param description Descrição da Secretaria (opcional)
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Input(
            String name,
            String description
    ) implements CreateSecretariatCommand {}

    /**
     * Estrutura de dados de saída para criação de Secretaria.
     *
     * <p>Retorna o identificador único de negócio (UUID) juntamente com
     * os dados cadastrados, assegurando a rastreabilidade do recurso.</p>
     *
     * @param uuid        Identificador único da Secretaria
     * @param name        Nome da Secretaria cadastrada
     * @param description Descrição da Secretaria cadastrada
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Output(
            UUID uuid,
            String name,
            String description
    ) implements CreateSecretariatCommand {}
}
