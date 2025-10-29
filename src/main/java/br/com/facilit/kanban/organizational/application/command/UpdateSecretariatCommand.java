package br.com.facilit.kanban.organizational.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.UUID;

/**
 * Comando responsável pela atualização de uma Secretaria existente.
 *
 * <p>Este comando faz parte da Application Layer da Arquitetura Hexagonal
 * e representa a estrutura dos dados recebidos (Input) e enviados (Output)
 * no caso de uso {@code UpdateSecretariatUseCase}.</p>
 *
 * <ul>
 *     <li><b>Input:</b> Dados necessários para atualizar a Secretaria, incluindo o UUID para identificação</li>
 *     <li><b>Output:</b> Dados retornados da Secretaria após a atualização</li>
 * </ul>
 *
 * <p>Este comando garante que apenas o identificador único (UUID) e os campos
 * modificáveis sejam enviados para atualização.</p>
 *
 * @author Antonio Neto
 */
public sealed interface UpdateSecretariatCommand extends Serializable
        permits UpdateSecretariatCommand.Input, UpdateSecretariatCommand.Output {

    /**
     * Estrutura de dados de entrada para atualização de Secretaria.
     *
     * @param uuid Identificador único da Secretaria a ser atualizada
     * @param name Novo nome da Secretaria
     * @param description Nova descrição da Secretaria
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Input(
            UUID uuid,
            String name,
            String description
    ) implements UpdateSecretariatCommand {}

    /**
     * Estrutura de dados de saída após atualização da Secretaria.
     *
     * <p>Contém os principais atributos da Secretaria atualizada.</p>
     *
     * @param uuid Identificador único da Secretaria
     * @param name Nome atualizado da Secretaria
     * @param description Descrição atualizada da Secretaria
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Output(
            UUID uuid,
            String name,
            String description
    ) implements UpdateSecretariatCommand {}
}
