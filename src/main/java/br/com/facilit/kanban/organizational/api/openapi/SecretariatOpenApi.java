package br.com.facilit.kanban.organizational.api.openapi;

import br.com.facilit.kanban.organizational.domain.dto.SecretariatDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Interface de especificação OpenAPI para os endpoints relacionados à entidade Secretaria.
 *
 * <p>Define os contratos expostos via REST no contexto da gestão organizacional.
 * Todos os retornos seguem o padrão reativo com {@link Mono}.</p>
 *
 * <p>A paginação é realizada utilizando {@link Pageable}.</p>
 *
 * @author Antonio
 */
@Tag(name = "Secretariat API", description = "Gerenciamento de Secretarias da organização")
public interface SecretariatOpenApi {

    /**
     * Cria uma nova Secretaria no sistema.
     *
     * @param request objeto contendo os dados para criação de uma nova Secretaria
     * @return {@link Mono} contendo os dados da Secretaria criada
     */
    @Operation(
            summary = "Cria uma nova Secretaria",
            description = "Adiciona uma nova Secretaria ao sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Secretaria criada com sucesso",
                            content = @Content(schema = @Schema(implementation = SecretariatDTO.Response.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    Mono<SecretariatDTO.Response> create(
            @Parameter(description = "Dados da nova Secretaria")
            SecretariatDTO.Request request
    );

    /**
     * Lista todas as Secretarias cadastradas, utilizando paginação.
     *
     * @param pageable parâmetros de paginação e ordenação
     * @return {@link Mono} contendo uma página com os dados de Secretarias
     */
    @Operation(
            summary = "Lista Secretarias",
            description = "Retorna todas as Secretarias cadastradas de forma paginada",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista retornada com sucesso",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    Mono<Page<SecretariatDTO.Response>> list(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            Pageable pageable
    );

    /**
     * Consulta uma Secretaria utilizando o seu identificador único.
     *
     * @param id identificador único da Secretaria (UUID)
     * @return {@link Mono} contendo os dados da Secretaria encontrada, ou vazio se não existir
     */
    @Operation(
            summary = "Busca Secretaria por ID",
            description = "Localiza uma Secretaria pelo seu UUID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Secretaria encontrada",
                            content = @Content(schema = @Schema(implementation = SecretariatDTO.Response.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Secretaria não encontrada")
            }
    )
    Mono<SecretariatDTO.Response> find(
            @Parameter(description = "UUID da Secretaria", required = true)
            UUID id
    );

    /**
     * Atualiza as informações de uma Secretaria existente.
     *
     * @param id identificador único da Secretaria
     * @param request objeto contendo os novos dados atualizados
     * @return {@link Mono} contendo os dados atualizados da Secretaria
     */
    @Operation(
            summary = "Atualiza uma Secretaria",
            description = "Atualiza as informações de uma Secretaria já existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Secretaria não encontrada"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
            }
    )
    Mono<SecretariatDTO.Response> update(
            @Parameter(description = "UUID da Secretaria", required = true)
            UUID id,

            @Parameter(description = "Novos dados da Secretaria")
            SecretariatDTO.Request request
    );

    /**
     * Exclui uma Secretaria do sistema com base no seu identificador.
     *
     * @param id identificador único da Secretaria
     * @return {@link Mono} vazio indicando sucesso na remoção
     */
    @Operation(
            summary = "Remove uma Secretaria",
            description = "Exclui a Secretaria pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Secretaria não encontrada")
            }
    )
    Mono<Void> delete(
            @Parameter(description = "UUID da Secretaria", required = true)
            UUID id
    );
}
