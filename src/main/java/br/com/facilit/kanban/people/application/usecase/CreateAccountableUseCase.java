package br.com.facilit.kanban.people.application.usecase;

import br.com.facilit.kanban.people.application.command.CreateAccountableCommand;
import br.com.facilit.kanban.people.domain.po.AccountablePO;
import br.com.facilit.kanban.people.infra.repository.AccountableRepository;
import br.com.facilit.kanban.people.mapping.AccountableMapper;
import br.com.facilit.kanban.shared.exception.ClientAlreadyExistsException;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso responsável pela criação de um novo responsável (Accountable) no sistema Kanban.
 *
 * <p>Este caso de uso segue a arquitetura hexagonal (Ports & Adapters), sendo injetado
 * como bean Spring com o Qualifier {@code createAccountableUseCase}.</p>
 *
 * <p>Fluxo do caso de uso:
 * <ol>
 *     <li>Verifica se o e-mail fornecido já está registrado no sistema;</li>
 *     <li>Se não existir, converte o {@link CreateAccountableCommand.Input} em {@link AccountablePO};</li>
 *     <li>Gera um UUID para o novo responsável;</li>
 *     <li>Persiste a entidade reativa no banco de dados através do {@link AccountableRepository};</li>
 *     <li>Converte a entidade salva em {@link CreateAccountableCommand.Output} e retorna em {@link Mono}.</li>
 * </ol>
 * </p>
 *
 * <p>Exceções:
 * <ul>
 *     <li>{@link ClientAlreadyExistsException} é lançada caso o e-mail já esteja cadastrado;</li>
 *     <li>Qualquer erro durante a persistência é propagado como erro do {@link Mono}.</li>
 * </ul>
 * </p>
 *
 * <p>Logs estruturados são gerados em cada etapa para rastreabilidade e auditoria.</p>
 *
 * @author Antonio Neto
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("createAccountableUseCase")
public class CreateAccountableUseCase implements IUseCase<CreateAccountableCommand.Input, Mono<CreateAccountableCommand.Output>> {

    private final AccountableRepository accountableRepository;

    @Override
    public Mono<CreateAccountableCommand.Output> execute(CreateAccountableCommand.Input input) {
        log.info("Iniciando criação de responsável: {}", input);

        return accountableRepository.existsByEmailIgnoreCase(input.email())
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Tentativa de cadastro com e-mail já registrado: {}", input.email());
                        return Mono.error(new ClientAlreadyExistsException("E-mail já registrado no sistema"));
                    }
                    AccountablePO po = AccountableMapper.Create.INPUT_TO_PO.apply(input);
                    po.setUuid(UUID.randomUUID());
                    return accountableRepository.save(po)
                            .map(AccountableMapper.Create.PO_TO_OUTPUT)
                            .doOnSuccess(saved ->
                                    log.info("Responsável criado com sucesso! UUID: {}", saved.uuid()))
                            .doOnError(error ->
                                    log.error("Erro ao criar responsável {}: {}", input.email(), error.getMessage(), error));
                });
    }
}
