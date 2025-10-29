package br.com.facilit.kanban.organizational.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

/**
 * Interface base para Data Transfer Objects (DTOs) relacionados à entidade Secretária
 * dentro do fluxo Kanban do sistema. Define os contratos compartilhados entre
 * objetos de entrada (Request) e objetos de saída (Response).
 *
 * @see SecretariatDTO.Request
 * @see SecretariatDTO.Response
 */
@Schema(
        name = "SecretariatDTO",
        description = "DTO base para operações com a Secretária no Kanban."
)
public sealed interface SecretariatDTO extends Serializable
        permits SecretariatDTO.Request, SecretariatDTO.Response {

    /**
     * Representa os dados necessários para criar ou atualizar uma Secretária.
     */
    @Schema(
            name = "SecretariatRequest",
            description = "Payload de entrada para criação e atualização da Secretária."
    )
    record Request(

            @Schema(description = "Nome da Secretária", example = "Secretaria Financeira", required = true)
            @NotBlank(message = "O nome da secretária não pode estar em branco")
            @Size(max = 100, message = "O nome da secretária deve ter no máximo 100 caracteres")
            String name,

            @Schema(description = "Descrição da Secretária", example = "Responsável pelo setor financeiro", required = false)
            @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
            String description

    ) implements SecretariatDTO {}

    /**
     * Contém as informações retornadas pela API ao consultar uma Secretária.
     */
    @Schema(
            name = "SecretariatResponse",
            description = "Informações de retorno sobre a Secretária."
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Response(
            @Schema(description = "Identificador universal da Secretária", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID uuid,

            @Schema(description = "Nome da Secretária", example = "Secretaria Financeira")
            String name,

            @Schema(description = "Descrição da Secretária", example = "Responsável pelo setor financeiro")
            String description

    ) implements SecretariatDTO {}
}