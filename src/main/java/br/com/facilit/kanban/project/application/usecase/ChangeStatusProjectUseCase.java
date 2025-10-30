package br.com.facilit.kanban.project.application.usecase;

import br.com.facilit.kanban.project.application.command.ChangeStatusProjectCommand;
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
 * Caso de uso responsável por alterar o status de um projeto existente.
 *
 * <p>Esse caso de uso integra a camada de aplicação dentro da Arquitetura Hexagonal,
 * implementando a porta de entrada definida por {@link IUseCase}. Ele orquestra a
 * validação da existência do projeto, aplica a mudança de estado e devolve os
 * dados atualizados no formato definido pelo comando.</p>
 *
 * <p><strong>Fluxo do processo:</strong></p>
 * <ol>
 *     <li>Localiza o projeto pelo UUID informado</li>
 *     <li>Se não existir, lança {@link NotFoundResourceException}</li>
 *     <li>Atualiza apenas o status do projeto com base no input recebido</li>
 *     <li>Persiste a alteração no repositório reativo</li>
 *     <li>Converte a entidade atualizada para DTO de saída e retorna</li>
 * </ol>
 *
 * <p>Todos os passos são executados de forma reativa utilizando {@link Mono}.</p>
 *
 * @param input Objeto contendo o UUID do projeto e o novo status desejado
 * @return {@link Mono} contendo o DTO atualizado do projeto após salvar no banco
 *
 * @throws NotFoundResourceException caso o projeto não seja localizado pelo UUID
 *
 * @author Antonio Neto
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("changeStatusProjectUseCase")
public class ChangeStatusProjectUseCase implements IUseCase<ChangeStatusProjectCommand.Input, Mono<ChangeStatusProjectCommand.Output>> {

    private final ProjectRepository projectRepository;

    /**
     * Caso de uso responsável por alterar o status de um projeto.
     *
     * @param input dados necessários para alteração (id + novo status)
     * @return saída reativa com os dados atualizados do projeto
     */
    @Override
    public Mono<ChangeStatusProjectCommand.Output> execute(ChangeStatusProjectCommand.Input input) {
        log.info("Alterando status do projeto. UUID: {}, Novo Status: {}", input.uuid(), input.status());

        return projectRepository.findByUuid(input.uuid().toString())
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Projeto não encontrado para alteração de status. UUID: {}", input.uuid());
                    return Mono.error(new NotFoundResourceException("Projeto não encontrado"));
                }))
                .doOnNext(po -> log.info("Projeto encontrado: {}", po.getName()))
                .map(project -> ProjectMapper.ChangeStatus.INPUT_TO_PO.apply(project, input))
                .flatMap(projectRepository::save)
                .map(ProjectMapper.ChangeStatus.PO_TO_OUTPUT)
                .doOnSuccess(output -> log.info("Status do projeto atualizado com sucesso"))
                .doOnError(error -> log.error("Erro ao alterar status do projeto: {}", error.getMessage(), error));
    }
}
