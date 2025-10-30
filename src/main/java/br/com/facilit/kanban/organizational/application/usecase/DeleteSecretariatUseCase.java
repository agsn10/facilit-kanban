package br.com.facilit.kanban.organizational.application.usecase;

import br.com.facilit.kanban.organizational.application.command.DeleteSecretariatCommand;
import br.com.facilit.kanban.organizational.infra.repository.SecretariatRepository;
import br.com.facilit.kanban.shared.exception.NotFoundResourceException;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsável por excluir uma Secretaria da base de dados.
 *
 * <p>Este caso de uso identifica a Secretaria por UUID, validando sua
 * existência antes de realizar a exclusão. Logs estruturados são
 * gerados para acompanhamento do fluxo e tratamento de falhas.</p>
 *
 * <p>Implementa a interface {@link IUseCase}, seguindo o padrão
 * da Arquitetura Hexagonal (Ports & Adapters).</p>
 *
 * @author Antonio Neto
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("deleteSecretariatUseCase")
public class DeleteSecretariatUseCase implements IUseCase<DeleteSecretariatCommand.Input, Mono<Void>> {

    private final SecretariatRepository secretariatRepository;

    /**
     * Executa a exclusão de uma Secretaria com base no UUID informado.
     *
     * @param input dados contendo o UUID da Secretaria
     * @return {@link Mono<Void>} vazio apenas para sinalizar conclusão
     */
    @Override
    public Mono<Void> execute(DeleteSecretariatCommand.Input input) {

        log.info("Iniciando exclusão da Secretaria com UUID {}", input.uuid());

        return secretariatRepository.findByUuid(input.uuid().toString())
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Secretaria não encontrada para o UUID: {}", input.uuid());
                    return Mono.error(new NotFoundResourceException("Secretaria não encontrada"));
                }))
                .flatMap(secretariat -> secretariatRepository.deleteById(secretariat.getId()))
                .doOnSuccess(v -> log.info("Secretaria excluída com sucesso: {}", input.uuid()))
                .onErrorMap(error -> {
                    log.error("Erro ao excluir Secretaria: {}", error.getMessage(), error);
                    return new RuntimeException("Falha ao excluir Secretaria");
                });
    }
}

