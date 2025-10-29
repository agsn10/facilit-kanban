package br.com.facilit.kanban.project.application.usecase;

import br.com.facilit.kanban.project.application.command.CreateProjectCommand;
import br.com.facilit.kanban.project.domain.po.ProjectPO;
import br.com.facilit.kanban.project.infra.repository.ProjectRepository;
import br.com.facilit.kanban.project.mapping.ProjectMapper;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso responsável pela criação de um novo projeto.
 *
 * <p>Fluxo:<br>
 * Converte Input para entidade persistente<br>
 * Persiste o registro no repositório reativo<br>
 * Retorna DTO de saída encapsulado em {@link Mono}<br>
 * Logs de sucesso e erro são gerados para rastreabilidade</p>
 *
 * <p>Implementa a porta de entrada {@link IUseCase} seguindo a Arquitetura Hexagonal.</p>
 *
 * @author Antonio Neto
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("createProjectUseCase")
public class CreateProjectUseCase implements IUseCase<CreateProjectCommand.Input, Mono<CreateProjectCommand.Output>> {

    private final ProjectRepository projectRepository;

    @Override
    public Mono<CreateProjectCommand.Output> execute(CreateProjectCommand.Input input) {
        log.info("Iniciando criação de projeto: {}", input);

        ProjectPO projectPO = ProjectMapper.Create.INPUT_TO_PO.apply(input);
        projectPO.setUuid(UUID.randomUUID());

        return projectRepository.save(projectPO)
                .map(ProjectMapper.Create.PO_TO_OUTPUT)
                .doOnSuccess(output -> log.info("Projeto criado com sucesso: {}", output))
                .onErrorMap(error -> {
                    log.error("Erro ao criar projeto: {}", error.getMessage(), error);
                    return new RuntimeException("Falha ao criar projeto");
                });
    }
}