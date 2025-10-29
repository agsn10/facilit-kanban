package br.com.facilit.kanban.organizational.application.usecase;

import br.com.facilit.kanban.organizational.application.command.ListSecretariatCommand;
import br.com.facilit.kanban.organizational.domain.po.SecretariatPO;
import br.com.facilit.kanban.organizational.mapping.SecretariatMapper;
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
 * Caso de uso responsável por realizar a consulta paginada de Secretarias.
 *
 * <p>Essa classe utiliza o {@link R2dbcEntityTemplate} para construir dinamicamente a query
 * com paginação, ordenação e contagem total de registros na base de dados.</p>
 *
 * <p>O retorno segue o contrato definido no comando {@link ListSecretariatCommand.Output},
 * contendo os registros paginados, quantidade total de elementos e número de páginas.</p>
 *
 * @author Antonio Neto
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("listSecretariatUseCase")
public class ListSecretariatUseCase implements IUseCase<ListSecretariatCommand.Input, Mono<Page<ListSecretariatCommand.Output>>> {

    private final R2dbcEntityTemplate template;

    /**
     * Executa a consulta de Secretarias com paginação.
     *
     * @param input {@link ListSecretariatCommand.Input} contendo número da página,
     *              tamanho da página e ordenação.
     * @return {@link Mono} contendo o {@link ListSecretariatCommand.Output}
     */
    @Override
    public Mono<Page<ListSecretariatCommand.Output>> execute(ListSecretariatCommand.Input input) {

        log.info("Iniciando consulta paginada de Secretarias | page={}, size={}",
                input.pageNumber(), input.pageSize());

        var pageable = SecretariatMapper.List.INPUT_TO_PO.apply(input);

        var query = Query.empty()
                .sort(pageable.getSort())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        Mono<List<SecretariatPO>> results = template.select(SecretariatPO.class)
                .matching(query)
                .all()
                .collectList()
                .doOnSuccess(list -> log.info("Itens retornados da consulta: {}", list.size()));

        Mono<Long> count = template.count(Query.empty(), SecretariatPO.class)
                .doOnSuccess(total -> log.info("Total de registros na base: {}", total));

        return Mono.zip(results, count)
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()))
                .map(SecretariatMapper.List.PAGE_PO_TO_PAGE_OUTPUT)
                .doOnSuccess(output -> log.info("Consulta de Secretarias finalizada com sucesso"))
                .onErrorMap(error -> {
                    log.error("Erro ao consultar Secretarias: {}", error.getMessage(), error);
                    return new RuntimeException("Erro ao consultar Secretarias");
                });
    }
}
