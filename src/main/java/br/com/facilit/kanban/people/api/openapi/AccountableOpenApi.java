package br.com.facilit.kanban.people.api.openapi;

import br.com.facilit.kanban.people.domain.dto.AccountableDTO;
import br.com.facilit.kanban.shared.domain.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Tag(
        name = "Accountable API",
        description = "Operações relacionadas aos reponsaveis dos projetos"
)
public interface AccountableOpenApi {

    @Operation(
            summary = "Criar um novo responsável",
            description = "Cria um responsável no sistema garantindo que o email seja único.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do responsável a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AccountableDTO.Request.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Responsável criado com sucesso",
                            content = @Content(schema = @Schema(implementation = AccountableDTO.Response.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "409", description = "Email já cadastrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    public Mono<AccountableDTO.Response> create(AccountableDTO.Request request);

    @Operation(
            summary = "Listar responsáveis",
            description = "Lista todos os responsáveis cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de responsáveis",
                            content = @Content(schema = @Schema(implementation = AccountableDTO.Response.class)))
            }
    )
    public Mono<PageResponse<AccountableDTO.Response>> list(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "20") int size,
                                                            @RequestParam(defaultValue = "name") String sort);

    @Operation(
            summary = "Buscar responsável por ID",
            description = "Retorna os dados de um responsável pelo seu identificador único.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Responsável encontrado",
                            content = @Content(schema = @Schema(implementation = AccountableDTO.Response.class))),
                    @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
            }
    )
    public Mono<AccountableDTO.Response> findById(UUID id);

    @Operation(
            summary = "Atualizar responsável",
            description = "Atualiza os dados de um responsável existente pelo seu ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do responsável",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AccountableDTO.Request.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Responsável atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = AccountableDTO.Response.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
            }
    )
    public Mono<AccountableDTO.Response> update(UUID id, AccountableDTO.Request request);

    @Operation(
            summary = "Remover responsável",
            description = "Remove um responsável existente pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Responsável removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
            }
    )
    public Mono<Void> delete(UUID id);
}
