package br.com.facilit.kanban.project.application.usecase;

import br.com.facilit.kanban.project.application.command.ListProjectCommand;
import br.com.facilit.kanban.project.domain.po.ProjectPO;
import br.com.facilit.kanban.project.mapping.ProjectMapper;
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
 * Caso de uso responsável pela listagem paginada de projetos.
 *
 * <p>Esse caso de uso implementa a porta {@link IUseCase} dentro da arquitetura hexagonal.
 * Ele executa uma consulta paginada reativa utilizando {@link R2dbcEntityTemplate}, aplicando
 * filtros de paginação e ordenação definidos no comando de entrada.</p>
 *
 * <p><strong>Fluxo do processo:</strong></p>
 * <ol>
 *     <li>Recupera as informações de paginação do {@link ListProjectCommand.Input}</li>
 *     <li>Consulta os registros com base em offset, limit e sort</li>
 *     <li>Executa uma segunda consulta reativa para totalizar os registros</li>
 *     <li>Combina os resultados e total no formato {@link Page}</li>
 *     <li>Converte os dados da camada de persistência para DTOs com o {@link ProjectMapper}</li>
 * </ol>
 *
 * @see ListProjectCommand.Input
 * @see ListProjectCommand.Output
 * @see ProjectMapper.List
 *
 * @author Antonio Neto
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("listProjectUseCase")
public class ListProjectUseCase implements IUseCase<ListProjectCommand.Input, Mono<Page<ListProjectCommand.Output>>> {

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Page<ListProjectCommand.Output>> execute(ListProjectCommand.Input input) {
        log.info("Iniciando consulta paginada de Projetos | page={}, size={}",
                input.pageNumber(), input.pageSize());

        var pageable = ProjectMapper.List.INPUT_TO_PO.apply(input);

        var query = Query.empty()
                .sort(pageable.getSort())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        Mono<List<ProjectPO>> results = template.select(ProjectPO.class)
                .matching(query)
                .all()
                .collectList()
                .doOnSuccess(list -> log.info("Itens retornados da consulta: {}", list.size()));

        Mono<Long> count = template.count(Query.empty(), ProjectPO.class)
                .doOnSuccess(total -> log.info("Total de registros na base: {}", total));

        return Mono.zip(results, count)
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()))
                .map(ProjectMapper.List.PAGE_PO_TO_PAGE_OUTPUT)
                .doOnSuccess(output -> log.info("Consulta de Projetos finalizada com sucesso"))
                .onErrorMap(error -> {
                    log.error("Erro ao consultar Projetos: {}", error.getMessage(), error);
                    return new RuntimeException("Erro ao consultar Projetos");
                });
    }
}
