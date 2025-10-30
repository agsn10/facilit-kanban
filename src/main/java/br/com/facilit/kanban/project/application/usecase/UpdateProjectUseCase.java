package br.com.facilit.kanban.project.application.usecase;

import br.com.facilit.kanban.project.application.command.UpdateProjectCommand;
import br.com.facilit.kanban.project.domain.po.ProjectPO;
import br.com.facilit.kanban.project.infra.repository.ProjectRepository;
import br.com.facilit.kanban.project.mapping.ProjectMapper;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Caso de uso responsável por atualizar os dados de um projeto existente.
 *
 * <p>Fluxo:<br>
 * Busca o projeto pelo UUID<br>
 * Atualiza os campos com os dados do Input<br>
 * Persiste as alterações no repositório reativo<br>
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
@Qualifier("updateProjectUseCase")
public class UpdateProjectUseCase implements IUseCase<UpdateProjectCommand.Input, Mono<UpdateProjectCommand.Output>> {

    private final ProjectRepository projectRepository;

    @Override
    public Mono<UpdateProjectCommand.Output> execute(UpdateProjectCommand.Input input) {
        log.info("Iniciando atualização do projeto: {}", input);

        return projectRepository.findByUuid(input.uuid().toString())
                .switchIfEmpty(Mono.error(new RuntimeException("Projeto não encontrado")))
                .flatMap(existingProject -> {
                    ProjectPO projectPO = ProjectMapper.Update.INPUT_TO_PO.apply(input);
                    projectPO.setId(existingProject.getId());
                    projectPO.setUpdatedAt(LocalDateTime.now());
                    return projectRepository.save(existingProject);
                })
                .map(ProjectMapper.Update.PO_TO_OUTPUT)
                .doOnSuccess(output -> log.info("Projeto atualizado com sucesso: {}", output))
                .onErrorMap(error -> {
                    log.error("Erro ao atualizar projeto: {}", error.getMessage(), error);
                    return new RuntimeException("Falha ao atualizar projeto");
                });
    }
}
