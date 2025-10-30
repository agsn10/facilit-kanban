package br.com.facilit.kanban.people.application.usecase;

import br.com.facilit.kanban.people.application.command.DeleteAccountableCommand;
import br.com.facilit.kanban.people.infra.repository.AccountableRepository;
import br.com.facilit.kanban.shared.exception.NotFoundResourceException;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsável pela exclusão de um responsável (Accountable) no sistema Kanban.
 *
 * <p>Este caso de uso segue a Arquitetura Hexagonal (Ports & Adapters) e é registrado
 * como bean Spring com o Qualifier {@code deleteAccountableUseCase}.</p>
 *
 * <p>Fluxo do caso de uso:
 * <ol>
 *     <li>Verifica se o responsável existe no sistema pelo {@code UUID} fornecido;</li>
 *     <li>Caso não exista, lança {@link NotFoundResourceException};</li>
 *     <li>Se existir, remove o registro do repositório reativo ({@link AccountableRepository});</li>
 *     <li>Retorna {@link Mono<Void>} indicando sucesso ou propagando o erro;</li>
 * </ol>
 * </p>
 *
 * <p>Logs estruturados são gerados em cada etapa para auditoria e rastreabilidade:
 * <ul>
 *     <li>Início da operação</li>
 *     <li>Sucesso da exclusão</li>
 *     <li>Erros durante a operação</li>
 * </ul>
 * </p>
 *
 * @author Antonio Neto
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("deleteAccountableUseCase")
public class DeleteAccountableUseCase implements IUseCase<DeleteAccountableCommand.Input, Mono<Void>> {

    private final AccountableRepository accountableRepository;

    @Override
    public Mono<Void> execute(DeleteAccountableCommand.Input input) {
        log.info("Iniciando exclusão do responsável | UUID: {}", input.uuid());

        return accountableRepository.findByUuid(input.uuid().toString())
                .switchIfEmpty(Mono.error(
                        new NotFoundResourceException("Responsável não encontrado | UUID: " + input.uuid())))
                .flatMap(existingPO -> accountableRepository.delete(existingPO))
                .doOnSuccess(v -> log.info("Responsável excluído com sucesso | UUID: {}", input.uuid()))
                .doOnError(error -> log.error(
                        "Erro ao excluir responsável | UUID: {} | {}",
                        input.uuid(), error.getMessage(), error));
    }
}
