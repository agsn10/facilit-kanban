package br.com.facilit.kanban.people.application.usecase;

import br.com.facilit.kanban.people.application.command.FindAccountableCommand;
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
 * Caso de uso responsável por consultar um Responsável (Accountable) pelo UUID.
 *
 * <p>Consulta o repositório reativo e converte o resultado para o DTO de saída,
 * encapsulando o retorno em um {@link Mono}. Logs são registrados para monitoramento.</p>
 *
 * <p>Fluxo:<br>
 * 🔹 Recebe o UUID como entrada<br>
 * 🔹 Consulta o repositório reativo para encontrar o responsável<br>
 * 🔹 Se encontrado, converte a entidade para DTO de saída<br>
 * 🔹 Se não encontrado, lança {@link NotFoundResourceException}<br>
 * 🔹 Retorna o resultado encapsulado em um {@link Mono}
 *
 * <p>Este caso de uso segue os princípios da Arquitetura Hexagonal,
 * implementando a porta de entrada ({@link IUseCase}).</p>
 *
 * @author Antonio Neto
 *
 * */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("findAccountableUseCase")
public class FindAccountableUseCase implements IUseCase<FindAccountableCommand.Input, Mono<FindAccountableCommand.Output>> {

    private final AccountableRepository accountableRepository;

    @Override
    public Mono<FindAccountableCommand.Output> execute(FindAccountableCommand.Input input) {
        log.info("Iniciando consulta de Accountable | uuid={}", input.uuid());

        return accountableRepository.findByUuid(input.uuid().toString())
                .map(AccountableMapper.Find.PO_TO_OUTPUT)
                .doOnSuccess(output -> log.info("Accountable encontrado: {}", output))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Accountable não encontrado | uuid={}", input.uuid());
                    return Mono.error(new NotFoundResourceException("Responsável não encontrado para o UUID informado."));
                }))
                .onErrorMap(error -> {
                    log.error("Erro ao consultar Accountable: {}", error.getMessage(), error);
                    return error instanceof NotFoundResourceException
                            ? error
                            : new RuntimeException("Falha ao consultar Accountable");
                });
    }
}
