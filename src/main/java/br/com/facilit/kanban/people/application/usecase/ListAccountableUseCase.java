package br.com.facilit.kanban.people.application.usecase;

import br.com.facilit.kanban.people.application.command.ListAccountableCommand;
import br.com.facilit.kanban.people.domain.po.AccountablePO;
import br.com.facilit.kanban.people.mapping.AccountableMapper;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Caso de uso responsável pela listagem paginada de Responsáveis (Accountable).
 *
 * Este use case:
 * <ul>
 *     <li>Recebe parâmetros de paginação via Input</li>
 *     <li>Executa uma consulta reativa no banco usando R2dbcEntityTemplate</li>
 *     <li>Aplica ordenação e paginação dinâmica</li>
 *     <li>Retorna um {@link Mono} contendo {@link Page} com os resultados</li>
 * </ul>
 *
 * A conversão entre PO e Output é realizada pelo {@link AccountableMapper}.
 *
 * @author Antonio Neto
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("listAccountableUseCase")
public class ListAccountableUseCase implements IUseCase<ListAccountableCommand.Input, Mono<Page<ListAccountableCommand.Output>>> {

    /** Template para execução de operações reativas com o banco via R2DBC. */
    private final R2dbcEntityTemplate template;

    /**
     * Executa a listagem paginada de responsáveis.
     *
     * @param input parâmetros de paginação e ordenação
     * @return página reativa contendo os responsáveis encontrados
     *
     * @throws RuntimeException caso ocorra algum erro na consulta
     */
    @Override
    public Mono<Page<ListAccountableCommand.Output>> execute(ListAccountableCommand.Input input) {
        log.info("Iniciando consulta paginada de Responsáveis | page={}, size={}",
                input.pageNumber(), input.pageSize());

        var pageable = AccountableMapper.List.INPUT_TO_PO.apply(input);

        var query = Query.empty()
                .sort(pageable.getSort())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        Mono<List<AccountablePO>> results = template.select(AccountablePO.class)
                .matching(query)
                .all()
                .collectList()
                .doOnSuccess(list -> log.info("Itens retornados da consulta: {}", list.size()));

        Mono<Long> count = template.count(Query.empty(), AccountablePO.class)
                .doOnSuccess(total -> log.info("Total de registros na base: {}", total));

        return Mono.zip(results, count)
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()))
                .map(AccountableMapper.List.PAGE_PO_TO_PAGE_OUTPUT)
                .doOnSuccess(output -> log.info("Consulta de Responsáveis finalizada com sucesso"))
                .onErrorMap(error -> {
                    log.error("Erro ao consultar Responsáveis: {}", error.getMessage(), error);
                    return new RuntimeException("Erro ao consultar Responsáveis");
                });
    }
}
