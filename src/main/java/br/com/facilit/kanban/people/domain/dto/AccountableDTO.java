package br.com.facilit.kanban.people.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;

/**
 * Interface base para Data Transfer Objects (DTOs) relacionados à entidade Responsável
 * dentro do fluxo Kanban do sistema. Define os contratos compartilhados entre
 * objetos de entrada (Request) e objetos de saída (Response).
 *
 * @see AccountableDTO.Request
 * @see AccountableDTO.Response
 */
@Schema(
        name = "AccountableDTO",
        description = "DTO base para operações com o Responsável no Kanban."
)
public sealed interface AccountableDTO extends Serializable
        permits AccountableDTO.Request, AccountableDTO.Response {

    /**
     * Representa os dados necessários para criar ou atualizar um responsável.
     *
     * @param name  Nome do responsável
     * @param email E-mail institucional ou de acesso ao sistema
     * @param role  Papel ou função dentro do projeto Kanban
     */
    @Schema(
            name = "AccountableRequest",
            description = "Payload de entrada para criação e atualização de responsáveis."
    )
    record Request(

            @NotBlank(message = "O nome é obrigatório")
            @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
            @Schema(description = "Nome do responsável", example = "João da Silva", required = true)
            String name,

            @NotBlank(message = "O e-mail é obrigatório")
            @Email(message = "E-mail inválido")
            @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres")
            @Schema(description = "E-mail institucional ou de acesso ao sistema",
                    example = "joao.silva@empresa.com", required = true)
            String email,

            @NotBlank(message = "O papel é obrigatório")
            @Size(max = 50, message = "O papel deve ter no máximo 50 caracteres")
            @Schema(description = "Papel ou função dentro do projeto Kanban",
                    example = "Product Owner", required = true)
            String role,

            Long secretariatId

    ) implements AccountableDTO {}

    /**
     * Representa os dados retornados pela API ao consultar um responsável.
     *
     * @param name  Nome do responsável
     * @param email E-mail institucional do responsável
     * @param role  Papel ou função desempenhada
     * @param uuid  Identificador universal do responsável
     */
    @Schema(
            name = "AccountableResponse",
            description = "Informações de retorno sobre o Responsável."
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Response(

            @Schema(description = "Nome do responsável", example = "João da Silva")
            String name,

            @Schema(description = "E-mail institucional do responsável",
                    example = "joao.silva@empresa.com")
            String email,

            @Schema(description = "Papel desempenhado no projeto",
                    example = "Product Owner")
            String role,

            @Schema(description = "Identificador universal do responsável",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "550e8400-e29b-41d4-a716-446655440000")
            UUID uuid,

            Long secretariatId

    ) implements AccountableDTO {}
}
