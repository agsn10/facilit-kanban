package br.com.facilit.kanban.project.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Interface base para Data Transfer Objects (DTOs) relacionados à entidade Projeto
 * dentro do fluxo Kanban do sistema. Define os contratos compartilhados entre
 * objetos de entrada (Request) e objetos de saída (Response).
 *
 * @see ProjectDTO.Request
 * @see ProjectDTO.Response
 */
@Schema(
        name = "ProjectDTO",
        description = "DTO base para operações com Projeto no Kanban."
)
public sealed interface ProjectDTO extends Serializable permits ProjectDTO.Request, ProjectDTO.Response {

    /**
     * Representa os dados necessários para criação ou atualização de um projeto.
     * Contém informações de datas previstas e reais, status, prazos e indicadores
     * de desempenho do projeto no Kanban.
     */
    @Schema(
            name = "ProjectRequest",
            description = "Payload de entrada para criação e atualização de projetos."
    )
    record Request(

            @NotBlank(message = "O nome do projeto é obrigatório")
            @Size(max = 200, message = "O nome do projeto deve ter no máximo 200 caracteres")
            @Schema(description = "Nome do projeto.", example = "Sistema de Gestão Agrícola")
            String name,

            @NotBlank(message = "O status do projeto é obrigatório")
            @Size(max = 50, message = "O status deve ter no máximo 50 caracteres")
            @Schema(description = "Status do projeto conforme o fluxo Kanban.", example = "TODO")
            String status,

            @NotNull(message = "A data prevista de início é obrigatória")
            @Schema(description = "Data/hora prevista para início do projeto (UTC).", example = "2025-01-01T00:00:00Z")
            Instant expectedStart,

            Instant expectedThermal,

            @NotNull(message = "A data prevista de término é obrigatória")
            @Schema(description = "Data/hora prevista para término do projeto (UTC).", example = "2025-03-01T00:00:00Z")
            Instant expectedEnd,

            @Schema(description = "Data/hora real que o projeto iniciou (UTC).", example = "2025-01-05T10:30:00Z")
            Instant startActual,

            @Schema(description = "Data/hora real que o projeto terminou (UTC).", example = "2025-03-02T18:45:00Z")
            Instant endActual,

            Instant thermalActual,

            @PositiveOrZero(message = "O número de dias em atraso deve ser zero ou positivo")
            @Schema(description = "Número de dias de atraso do projeto.", example = "2")
            Integer daysLate,

            @PositiveOrZero(message = "O percentual de tempo restante deve ser zero ou positivo")
            @Schema(description = "Percentual do tempo restante até o prazo final do projeto.", example = "25.7")
            Double percentageOfTimeRemaining,

            Long secretariatId

    ) implements ProjectDTO {}

    /**
     * Contém as informações retornadas ao cliente após consultas a um projeto.
     * Representa uma visão resumida contendo os dados principais para exibição.
     */
    @Schema(
            name = "ProjectResponse",
            description = "Informações de retorno sobre o Projeto."
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Response(

            @Schema(description = "UUID do projeto.", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID uuid,

            @Schema(description = "Nome do projeto.", example = "Sistema de Gestão Agrícola")
            String name,

            @Schema(description = "Status atual do projeto no Kanban.", example = "IN_PROGRESS")
            String status,

            @NotNull(message = "A data prevista de início é obrigatória")
            @Schema(description = "Data/hora prevista para início do projeto (UTC).", example = "2025-01-01T00:00:00Z")
            Instant expectedStart,

            @NotNull(message = "A data prevista de término é obrigatória")
            @Schema(description = "Data/hora prevista para término do projeto (UTC).", example = "2025-03-01T00:00:00Z")
            Instant expectedThermal,

            @Schema(description = "Data/hora real que o projeto iniciou (UTC).", example = "2025-01-05T10:30:00Z")
            Instant startActual,

            @Schema(description = "Data/hora real que o projeto terminou (UTC).", example = "2025-03-02T18:45:00Z")
            Instant thermalActual,

            @PositiveOrZero(message = "O número de dias em atraso deve ser zero ou positivo")
            @Schema(description = "Número de dias de atraso do projeto.", example = "2")
            Integer daysLate,

            @PositiveOrZero(message = "O percentual de tempo restante deve ser zero ou positivo")
            @Schema(description = "Percentual do tempo restante até o prazo final do projeto.", example = "25.7")
            Double percentageOfTimeRemaining,

            Long secretariatId


    ) implements ProjectDTO {}
}
