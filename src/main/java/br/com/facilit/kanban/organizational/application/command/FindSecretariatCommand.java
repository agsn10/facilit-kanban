package br.com.facilit.kanban.organizational.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.UUID;

/**
 * Comando responsável pelo fluxo de consulta de uma Secretaria pelo seu UUID.
 *
 * <p>Este comando faz parte da Application Layer da Arquitetura Hexagonal
 * e representa a estrutura dos dados recebidos (Input) e enviados (Output)
 * no caso de uso {@code FindSecretariatUseCase}.</p>
 *
 * <ul>
 *     <li><b>Input:</b> Dados necessários para identificar a Secretaria a ser consultada</li>
 *     <li><b>Output:</b> Dados retornados da Secretaria encontrada</li>
 * </ul>
 *
 * <p>Este comando garante que apenas o identificador único (UUID) seja necessário
 * para buscar a Secretaria.</p>
 *
 * @author Antonio Neto
 */
public sealed interface FindSecretariatCommand extends Serializable
        permits FindSecretariatCommand.Input, FindSecretariatCommand.Output {

    /**
     * Estrutura de dados de entrada para consulta de Secretaria.
     *
     * @param uuid Identificador único da Secretaria a ser buscada
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Input(
            UUID uuid
    ) implements FindSecretariatCommand {}

    /**
     * Estrutura de dados de saída da consulta de Secretaria.
     *
     * <p>Contém os principais atributos da Secretaria encontrada.</p>
     *
     * @param uuid Identificador único da Secretaria
     * @param name Nome da Secretaria
     * @param description Descrição da Secretaria
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Output(
            UUID uuid,
            String name,
            String description
    ) implements FindSecretariatCommand {}
}
