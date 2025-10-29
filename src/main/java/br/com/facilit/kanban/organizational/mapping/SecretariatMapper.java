package br.com.facilit.kanban.organizational.mapping;

import br.com.facilit.kanban.organizational.application.command.*;
import br.com.facilit.kanban.organizational.domain.dto.SecretariatDTO;
import br.com.facilit.kanban.organizational.domain.po.SecretariatPO;
import org.springframework.data.domain.*;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Mapper responsável por realizar a conversão entre:
 * <ul>
 *     <li>DTOs ⇄ Commands</li>
 *     <li>Commands ⇄ Persistent Objects (PO)</li>
 *     <li>Pageable e resultados paginados</li>
 * </ul>
 *
 * É organizado por casos de uso (Create, List, Find, Update e Delete),
 * mantendo a separação das regras de mapeamento conforme o contexto.
 *
 * @author Antonio Neto
 */
public final class SecretariatMapper {

    private SecretariatMapper() { }

    /**
     * Conversões dedicadas ao caso de uso de criação de Secretaria.
     */
    public static final class Create {

        private Create() {}

        /**
         * Converte um DTO de request para input do caso de uso.
         */
        public static final Function<SecretariatDTO.Request, CreateSecretariatCommand.Input> REQUEST_TO_INPUT =
                request -> (request == null) ? null : new CreateSecretariatCommand.Input(
                        request.name(),
                        request.description()
                );

        /**
         * Converte a saída do caso de uso para DTO de resposta.
         */
        public static final Function<CreateSecretariatCommand.Output, SecretariatDTO.Response> OUTPUT_TO_RESPONSE =
                output -> (output == null) ? null : new SecretariatDTO.Response(
                        output.uuid(),
                        output.name(),
                        output.description()
                );

        /**
         * Converte o input para entidade de persistência (PO).
         */
        public static final Function<CreateSecretariatCommand.Input, SecretariatPO> INPUT_TO_PO =
                input -> (input == null) ? null : new SecretariatPO(
                        input.name(),
                        input.description()
                );

        /**
         * Converte entidade de persistência (PO) para saída do caso de uso.
         */
        public static final Function<SecretariatPO, CreateSecretariatCommand.Output> PO_TO_OUTPUT =
                po -> (po == null) ? null : new CreateSecretariatCommand.Output(
                        po.getUuid(),
                        po.getName(),
                        po.getDescription()
                );
    }

    /**
     * Conversões dedicadas ao caso de uso de listagem paginada de Secretarias.
     */
    public static final class List {

        private List() {}

        /**
         * Converte o pageable da requisição para input do caso de uso.
         * Aplica ordenação padrão caso nenhuma seja informada.
         */
        public static final Function<Pageable, ListSecretariatCommand.Input> REQUEST_TO_INPUT =
                request -> {
                    if (request == null) return null;
                    return new ListSecretariatCommand.Input(
                            request.getPageNumber(),
                            request.getPageSize(),
                            request.getSort() != null ? request.getSort() :
                                    Sort.by("name").ascending().and(Sort.by("createdAt").descending())
                    );
                };

        /**
         * Converte o input do caso de uso para pageable do Spring Data.
         */
        public static final Function<ListSecretariatCommand.Input, Pageable> INPUT_TO_PO =
                input -> (input == null) ? null : PageRequest.of(input.pageNumber(), input.pageSize(), input.sort());

        /**
         * Converte uma entidade de persistência (PO) para DTO de resposta.
         */
        public static final Function<SecretariatPO, SecretariatDTO.Response> PO_TO_OUTPUT =
                po -> (po == null) ? null : new SecretariatDTO.Response(
                        po.getUuid(),
                        po.getName(),
                        po.getDescription()
                );

        /**
         * Converte uma página de PO para página de output do caso de uso.
         */
        public static final Function<Page<SecretariatPO>, Page<ListSecretariatCommand.Output>> PAGE_PO_TO_PAGE_OUTPUT =
                pagePO -> {
                    @SuppressWarnings("unchecked")
                    java.util.List<ListSecretariatCommand.Output> output = pagePO.getContent()
                            .stream()
                            .map(po -> new ListSecretariatCommand.Output(po.getUuid(), po.getName(), po.getDescription()))
                            .toList();

                    return new PageImpl<>(
                            output,
                            Pageable.unpaged(),
                            pagePO.getTotalElements()
                    );
                };

        /**
         * Converte uma página de output do caso de uso para página de resposta DTO.
         */
        public static final Function<Page<ListSecretariatCommand.Output>, Page<SecretariatDTO.Response>> PAGE_OUTPUT_TO_PAGE_RESPONSE =
                pageOutput -> {
                    @SuppressWarnings("unchecked")
                    java.util.List<SecretariatDTO.Response> responses = pageOutput.getContent()
                            .stream()
                            .map(out -> new SecretariatDTO.Response(out.uuid(), out.name(), out.description()))
                            .toList();

                    return new PageImpl<>(
                            responses,
                            Pageable.unpaged(),
                            pageOutput.getTotalElements()
                    );
                };
    }

    /**
     * Conversões dedicadas ao caso de uso de consulta por UUID.
     */
    public static final class Find {

        private Find() { }

        public static final Function<UUID, FindSecretariatCommand.Input> REQUEST_TO_INPUT =
                uuid -> (uuid == null) ? null : new FindSecretariatCommand.Input(uuid);

        public static final Function<FindSecretariatCommand.Output, SecretariatDTO.Response> OUTPUT_TO_RESPONSE =
                output -> (output == null) ? null : new SecretariatDTO.Response(
                        output.uuid(),
                        output.name(),
                        output.description()
                );

        public static final Function<SecretariatPO, FindSecretariatCommand.Output> PO_TO_OUTPUT =
                po -> (po == null) ? null : new FindSecretariatCommand.Output(
                        po.getUuid(),
                        po.getName(),
                        po.getDescription()
                );
    }

    /**
     * Conversões dedicadas ao caso de uso de remoção de Secretaria.
     */
    public static final class Delete {

        private Delete() { }

        public static final Function<UUID, DeleteSecretariatCommand.Input> REQUEST_TO_INPUT =
                uuid -> (uuid == null) ? null : new DeleteSecretariatCommand.Input(uuid);
    }

    /**
     * Conversões dedicadas ao caso de uso de atualização de Secretaria.
     */
    public static final class Update {

        private Update() { }

        public static final BiFunction<UUID, SecretariatDTO.Request, UpdateSecretariatCommand.Input> REQUEST_TO_INPUT =
                (uuid, request) -> (uuid == null) ? null : new UpdateSecretariatCommand.Input(
                        uuid, request.name(), request.description()
                );

        public static final Function<UpdateSecretariatCommand.Output, SecretariatDTO.Response> OUTPUT_TO_RESPONSE =
                output -> (output == null) ? null : new SecretariatDTO.Response(
                        output.uuid(),
                        output.name(),
                        output.description()
                );

        public static final Function<UpdateSecretariatCommand.Input, SecretariatPO> INPUT_TO_PO =
                input -> (input == null) ? null : new SecretariatPO(
                        input.uuid(),
                        input.name(),
                        input.description()
                );

        public static final Function<SecretariatPO, UpdateSecretariatCommand.Output> PO_TO_OUTPUT =
                po -> (po == null) ? null : new UpdateSecretariatCommand.Output(
                        po.getUuid(),
                        po.getName(),
                        po.getDescription()
                );
    }

}
