package br.com.facilit.kanban.people.mapping;

import br.com.facilit.kanban.people.application.command.*;
import br.com.facilit.kanban.people.domain.dto.AccountableDTO;
import br.com.facilit.kanban.people.domain.po.AccountablePO;
import br.com.facilit.kanban.shared.domain.dto.PageResponse;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Classe de mapeamento entre DTOs, comandos e entidades do domínio Accountable.
 *
 * <p>Contém classes internas estáticas para cada operação CRUD (Create, List, Find, Update, Delete),
 * fornecendo funções puras (Function/BiFunction) para transformar dados entre camadas da aplicação.</p>
 *
 * <p>Seguindo princípios da Arquitetura Hexagonal, os mappers mantêm a separação entre
 * camadas de apresentação, aplicação e domínio.</p>
 *
 * @author Antonio Neto
 */
public final class AccountableMapper {

    private AccountableMapper(){}

    /**
     * Mapeamentos para criação de Accountable.
     */
    public static final class Create{

        private Create(){}

        /** Converte DTO de requisição para Input do comando de criação. */
        public static final Function<AccountableDTO.Request, CreateAccountableCommand.Input> REQUEST_TO_INPUT =
                request -> (request == null) ? null : new CreateAccountableCommand.Input(
                        request.name(),
                        request.email(),
                        request.role(),
                        request.secretariatId()
                );

        /** Converte Output do comando de criação para DTO de resposta. */
        public static final Function<CreateAccountableCommand.Output, AccountableDTO.Response> OUTPUT_TO_RESPONSE =
                output -> (output == null) ? null : new AccountableDTO.Response(
                        output.name(),
                        null,
                        null,
                        output.uuid(),
                        null
                );

        /** Converte Input do comando para entidade persistente (PO). */
        public static final Function<CreateAccountableCommand.Input, AccountablePO> INPUT_TO_PO =
                input -> (input == null) ? null : new AccountablePO(
                        input.name(),
                        input.email(),
                        input.role(),
                        input.secretariatId()
                );

        /** Converte entidade persistente para Output do comando de criação. */
        public static final Function<AccountablePO, CreateAccountableCommand.Output> PO_TO_OUTPUT =
                po -> (po == null) ? null : new CreateAccountableCommand.Output(
                        po.getUuid(),
                        po.getName()
                );
    }

    /**
     * Mapeamentos para listagem paginada de Accountable.
     */
    public static final class List{

        private List(){}

        /** Converte Input do comando de listagem para Pageable do Spring Data. */
        public static final Function<ListAccountableCommand.Input, Pageable> INPUT_TO_PO =
                input -> (input == null) ? null : PageRequest.of(input.pageNumber(), input.pageSize(), input.sort());

        /** Converte Pageable de requisição para Input do comando de listagem. */
        public static final Function<Pageable, ListAccountableCommand.Input> REQUEST_TO_INPUT =
                request -> {
                    if (request == null) return null;
                    return new ListAccountableCommand.Input(
                            request.getPageNumber(),
                            request.getPageSize(),
                            request.getSort() != null ? request.getSort() :
                                    Sort.by("name").ascending().and(Sort.by("createdAt").descending())
                    );
                };

        /** Converte Page de PO para Page de Output do comando de listagem. */
        public static final Function<Page<AccountablePO>, Page<ListAccountableCommand.Output>> PAGE_PO_TO_PAGE_OUTPUT =
                pagePO -> {
                    java.util.List<ListAccountableCommand.Output> output = pagePO.getContent()
                            .stream()
                            .map(po -> new ListAccountableCommand.Output(
                                    po.getUuid(),
                                    po.getName(),
                                    po.getEmail(),
                                    po.getRole(),
                                    po.getSecretariatId()))
                            .toList();

                    return new PageImpl<>(
                            output,
                            Pageable.unpaged(), // já está paginado no output
                            pagePO.getTotalElements()
                    );
                };

        /** Converte Page de Output para Page de DTO de resposta. */
        public static final Function<Page<ListAccountableCommand.Output>, PageResponse<AccountableDTO.Response>> PAGE_OUTPUT_TO_PAGE_RESPONSE =
                output -> {
                    java.util.List<AccountableDTO.Response> responses = output.getContent()
                            .stream()
                            .map(out -> new AccountableDTO.Response(
                                    out.name(),
                                    out.email(),
                                    out.role(),
                                    out.uuid(),
                                    out.secretariatId()))
                            .toList();

                    return new PageResponse<AccountableDTO.Response>(
                            responses,
                            output.getNumber(),
                            output.getSize(),
                            output.getTotalElements(),
                            output.getTotalPages()
                    );
                };
    }

    /**
     * Mapeamentos para consulta de um único Accountable.
     */
    public static final class Find{

        private Find(){}

        /** Converte UUID de requisição para Input do comando de busca. */
        public static final Function<UUID, FindAccountableCommand.Input> REQUEST_TO_INPUT =
                uuid -> (uuid == null) ? null : new FindAccountableCommand.Input(uuid);

        /** Converte Output do comando de busca para DTO de resposta. */
        public static final Function<FindAccountableCommand.Output, AccountableDTO.Response> OUTPUT_TO_RESPONSE =
                output -> (output == null) ? null : new AccountableDTO.Response(
                        output.name(),
                        null,
                        output.role(),
                        output.uuid(),
                        null
                );

        /** Converte PO para Output do comando de busca. */
        public static final Function<AccountablePO, FindAccountableCommand.Output> PO_TO_OUTPUT =
                po -> (po == null) ? null : new FindAccountableCommand.Output(
                        po.getUuid(),
                        po.getName(),
                        po.getRole()
                );
    }

    /**
     * Mapeamentos para atualização de Accountable.
     */
    public static final class Update{

        private Update(){}

        /** Converte UUID e DTO de requisição para Input do comando de atualização. */
        public static final BiFunction<UUID, AccountableDTO.Request, UpdateAccountableCommand.Input> REQUEST_TO_INPUT =
                (uuid, request) -> (uuid == null) ? null : new UpdateAccountableCommand.Input(
                        uuid,
                        request.name(),
                        request.email(),
                        request.role(),
                        request.secretariatId()
                );

        /** Converte Output do comando de atualização para DTO de resposta. */
        public static final Function<UpdateAccountableCommand.Output, AccountableDTO.Response> OUTPUT_TO_RESPONSE =
                output -> (output == null) ? null : new AccountableDTO.Response(
                        output.name(),
                        output.email(),
                        output.role(),
                        output.uuid(),
                        output.secretariatId()
                );

        /** Converte PO para Output do comando de atualização. */
        public static final Function<AccountablePO, UpdateAccountableCommand.Output> PO_TO_OUTPUT =
                po -> (po == null) ? null : new UpdateAccountableCommand.Output(
                        po.getUuid(),
                        po.getName(),
                        po.getEmail(),
                        po.getRole(),
                        po.getSecretariatId()
                );
    }

    /**
     * Mapeamentos para exclusão de Accountable.
     */
    public static final class Delete{

        private Delete(){}

        /** Converte UUID de requisição para Input do comando de exclusão. */
        public static final Function<UUID, DeleteAccountableCommand.Input> REQUEST_TO_INPUT =
                uuid -> (uuid == null) ? null : new DeleteAccountableCommand.Input(uuid);
    }
}
