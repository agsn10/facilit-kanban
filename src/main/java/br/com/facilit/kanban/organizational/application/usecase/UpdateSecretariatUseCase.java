package br.com.facilit.kanban.organizational.application.usecase;

import br.com.facilit.kanban.organizational.application.command.UpdateSecretariatCommand;
import br.com.facilit.kanban.organizational.domain.po.SecretariatPO;
import br.com.facilit.kanban.organizational.mapping.SecretariatMapper;
import br.com.facilit.kanban.organizational.infra.repository.SecretariatRepository;
import br.com.facilit.kanban.shared.exception.NotFoundResourceException;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsável por atualizar os dados de uma Secretaria.
 *
 * <p>Fluxo:<br>
 * Verificar se a secretaria existe no banco<br>
 * Atualizar os campos permitidos<br>
 * Salvar e retornar os dados atualizados
 *
 * <p>Em caso de inexistência, retorna erro NotFoundResourceException
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("updateSecretariatUseCase")
public class UpdateSecretariatUseCase implements IUseCase<UpdateSecretariatCommand.Input, Mono<UpdateSecretariatCommand.Output>> {

    private final SecretariatRepository secretariatRepository;

    @Override
    public Mono<UpdateSecretariatCommand.Output> execute(UpdateSecretariatCommand.Input input) {
        log.info("Iniciando atualização da secretaria com UUID: {}", input.uuid());

        return secretariatRepository.findByUuid(input.uuid().toString())
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Secretaria não encontrada para UUID: {}", input.uuid());
                    return Mono.error(new NotFoundResourceException("Secretaria não encontrada."));
                }))
                .flatMap(existing -> {
                    log.info("Secretaria encontrada. Atualizando informações...");
                    SecretariatPO updated = SecretariatMapper.Update.INPUT_TO_PO.apply(input);
                    updated.setId(existing.getId()); // Garantir que o ID original seja mantido
                    return secretariatRepository.save(updated);
                })
                .map(saved -> {
                    log.info("Secretaria ID {} atualizada com sucesso!", saved.getId());
                    return SecretariatMapper.Update.PO_TO_OUTPUT.apply(saved);
                })
                .doOnError(error ->
                        log.error("Erro ao atualizar secretaria UUID {}: {}",
                                input.uuid(), error.getMessage(), error));
    }
}
