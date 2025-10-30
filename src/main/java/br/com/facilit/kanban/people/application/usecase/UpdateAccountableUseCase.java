package br.com.facilit.kanban.people.application.usecase;

import br.com.facilit.kanban.people.application.command.UpdateAccountableCommand;
import br.com.facilit.kanban.people.infra.repository.AccountableRepository;
import br.com.facilit.kanban.people.mapping.AccountableMapper;
import br.com.facilit.kanban.shared.exception.NotFoundResourceException;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsável pela atualização de um responsável (Accountable).
 *
 * <p>Fluxo:<br>
 * Verifica se o responsável existe pelo UUID<br>
 * Atualiza os dados da entidade com os valores do Input<br>
 * Persiste a entidade atualizada no repositório reativo<br>
 * Converte a entidade para DTO de saída<br>
 * Retorna o resultado encapsulado em {@link Mono}<br>
 * Caso não encontrado, lança {@link NotFoundResourceException}
 *
 * <p>Segue os princípios da Arquitetura Hexagonal, implementando a porta de entrada {@link IUseCase}.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("updateAccountableUseCase")
public class UpdateAccountableUseCase implements IUseCase<UpdateAccountableCommand.Input, Mono<UpdateAccountableCommand.Output>> {

    private final AccountableRepository accountableRepository;

    @Override
    public Mono<UpdateAccountableCommand.Output> execute(UpdateAccountableCommand.Input input) {
        log.info("Iniciando atualização do responsável | UUID: {}", input.uuid());

        return accountableRepository.findByUuid(input.uuid().toString())
                .switchIfEmpty(Mono.error(new NotFoundResourceException("Responsável não encontrado | UUID: " + input.uuid())))
                .flatMap(existingPO -> {
                    // Atualiza campos
                    existingPO.setName(input.name());
                    existingPO.setEmail(input.email());
                    existingPO.setRole(input.role());

                    return accountableRepository.save(existingPO);
                })
                .map(AccountableMapper.Update.PO_TO_OUTPUT)
                .doOnSuccess(output -> log.info("Responsável atualizado com sucesso | UUID: {}", output.uuid()))
                .doOnError(error -> log.error("Erro ao atualizar responsável | UUID: {} | {}", input.uuid(), error.getMessage(), error));
    }
}
