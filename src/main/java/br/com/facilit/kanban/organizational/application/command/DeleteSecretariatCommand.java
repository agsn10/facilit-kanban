package br.com.facilit.kanban.organizational.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.UUID;

/**
 * Comando responsável pelo fluxo de exclusão de uma Secretaria na aplicação.
 *
 * <p>Este comando faz parte da Application Layer da Arquitetura Hexagonal
 * e representa a estrutura dos dados recebidos (Input) e enviados (Output)
 * no caso de uso {@code DeleteSecretariatUseCase}.</p>
 *
 * <ul>
 *     <li><b>Input:</b> Dados necessários para identificar a Secretaria a ser excluída</li>
 *     <li><b>Output:</b> Dados retornados após a exclusão bem-sucedida</li>
 * </ul>
 *
 * <p>Este comando garante que apenas o identificador único (UUID) seja necessário
 * para excluir a Secretaria.</p>
 *
 * @author Antonio Neto
 */
public sealed interface DeleteSecretariatCommand extends Serializable
        permits DeleteSecretariatCommand.Input, DeleteSecretariatCommand.Output {

    /**
     * Estrutura de dados de entrada para exclusão de Secretaria.
     *
     * @param uuid Identificador único da Secretaria a ser excluída
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Input(
            UUID uuid
    ) implements DeleteSecretariatCommand {}

    /**
     * Estrutura de dados de saída após exclusão de Secretaria.
     *
     * <p>Retorna o UUID da Secretaria que foi removida, garantindo rastreabilidade
     * e confirmação da operação.</p>
     *
     * @param uuid Identificador único da Secretaria excluída
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Output(
            UUID uuid
    ) implements DeleteSecretariatCommand {}
}
