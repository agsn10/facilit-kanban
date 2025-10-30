package br.com.facilit.kanban.project.application.usecase;

import br.com.facilit.kanban.project.application.command.FindProjectCommand;
import br.com.facilit.kanban.project.infra.repository.ProjectRepository;
import br.com.facilit.kanban.project.mapping.ProjectMapper;
import br.com.facilit.kanban.shared.exception.NotFoundResourceException;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsável pela busca de um {@link br.com.facilit.kanban.project.domain.po.ProjectPO}
 * por meio de seu UUID.
 * <p>
 * Este use case:
 * <ul>
 *     <li>Valida o input recebido</li>
 *     <li>Consulta o repositório de forma reativa</li>
 *     <li>Realiza o mapeamento da entidade para DTO</li>
 *     <li>Retorna erro customizado caso o recurso não exista</li>
 * </ul>
 * É implementado seguindo o padrão de arquitetura hexagonal,
 * sendo um componente da camada de aplicação.
 * </p>
 *
 * @author Antonio Neto
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("findProjectUseCase")
public class FindProjectUseCase implements IUseCase<FindProjectCommand.Input, Mono<FindProjectCommand.Output>> {

    private final ProjectRepository projectRepository;

    /**
     * Executa o processo de busca de um Projeto no sistema através de seu UUID.
     *
     * @param input objeto contendo o UUID do Projeto a ser consultado
     * @return {@link Mono} contendo os dados do Projeto encontrados ou erro caso não exista
     */
    @Override
    public Mono<FindProjectCommand.Output> execute(FindProjectCommand.Input input) {
        log.info("Iniciando consulta de Project | uuid={}", input.uuid());

        return projectRepository.findByUuid(input.uuid().toString())
                .map(ProjectMapper.Find.PO_TO_OUTPUT)
                .doOnSuccess(output -> log.info("Project encontrado: {}", output))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Project não encontrado | uuid={}", input.uuid());
                    return Mono.error(new NotFoundResourceException(
                            "Responsável não encontrado para o UUID informado."
                    ));
                }))
                .onErrorMap(error -> {
                    log.error("Erro ao consultar Project: {}", error.getMessage(), error);
                    return error instanceof NotFoundResourceException
                            ? error
                            : new RuntimeException("Falha ao consultar Project");
                });
    }
}
