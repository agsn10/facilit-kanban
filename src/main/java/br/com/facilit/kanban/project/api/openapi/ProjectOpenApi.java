package br.com.facilit.kanban.project.api.openapi;

import br.com.facilit.kanban.project.domain.dto.ProjectDTO;
import br.com.facilit.kanban.project.domain.enums.StatusProject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Tag(
        name = "Projeto API",
        description = "Operações relacionadas aos projetos"
)
public interface ProjectOpenApi {

    /**
     * Cria um novo projeto.
     *
     * @param request dados do projeto a ser criado
     * @return projeto criado
     */
    @Operation(
            summary = "Cria um novo projeto",
            description = "Endpoint para criar um projeto no sistema Kanban.",
            requestBody = @RequestBody(
                    description = "Dados do projeto a ser criado",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProjectDTO.Request.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso",
                            content = @Content(schema = @Schema(implementation = ProjectDTO.Response.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    public Mono<ProjectDTO.Response> create(
            @Parameter(description = "Dados do projeto a ser criado") ProjectDTO.Request request
    );

    /**
     * Lista todos os projetos.
     *
     * @return lista de projetos
     */
    @Operation(
            summary = "Lista todos os projetos",
            description = "Retorna todos os projetos cadastrados no sistema Kanban.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de projetos",
                            content = @Content(schema = @Schema(implementation = ProjectDTO.Response.class)))
            }
    )
    Mono<Page<ProjectDTO.Response>> list(Pageable pageable);

    /**
     * Busca um projeto pelo ID.
     *
     * @param id identificador único do projeto
     * @return projeto encontrado
     */
    @Operation(
            summary = "Busca projeto por ID",
            description = "Retorna os dados de um projeto específico pelo seu identificador único.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Projeto encontrado",
                            content = @Content(schema = @Schema(implementation = ProjectDTO.Response.class))),
                    @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
            }
    )
    public Mono<ProjectDTO.Response> find(
            @Parameter(description = "ID do projeto") UUID id
    );

    /**
     * Atualiza um projeto existente.
     *
     * @param id identificador único do projeto
     * @param request novos dados do projeto
     * @return projeto atualizado
     */
    @Operation(
            summary = "Atualiza um projeto",
            description = "Atualiza os dados de um projeto existente pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Projeto atualizado",
                            content = @Content(schema = @Schema(implementation = ProjectDTO.Response.class))),
                    @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public Mono<ProjectDTO.Response> update(
            @Parameter(description = "ID do projeto") UUID id,
            @Parameter(description = "Novos dados do projeto") ProjectDTO.Request request
    );

    /**
     * Altera o status de um projeto.
     *
     * @param id identificador único do projeto
     * @param status novo status do projeto
     * @return projeto com status atualizado
     */
    @Operation(
            summary = "Altera o status de um projeto",
            description = "Permite atualizar apenas o status de um projeto existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status do projeto atualizado",
                            content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
            }
    )
    public Mono<ProjectDTO.Response> changeStatus(
            @Parameter(description = "ID do projeto") UUID id,
            @Parameter(description = "Novo status do projeto") StatusProject status
    );
}