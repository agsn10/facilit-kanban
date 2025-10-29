package br.com.facilit.kanban.organizational.application.usecase;

import br.com.facilit.kanban.organizational.application.command.FindSecretariatCommand;
import br.com.facilit.kanban.organizational.infra.repository.SecretariatRepository;
import br.com.facilit.kanban.organizational.mapping.SecretariatMapper;
import br.com.facilit.kanban.shared.exception.NotFoundResourceException;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsável por consultar uma Secretaria a partir do UUID.
 *
 * <p>Este caso de uso utiliza o repositório reativo para buscar o registro na base
 * e retorna um DTO de saída conforme definido em {@link FindSecretariatCommand.Output}.</p>
 *
 * <p>Segue o padrão da Arquitetura Hexagonal (Ports & Adapters) e é registrado
 * como Bean Spring com o Qualifier {@code findSecretariatUseCase}.</p>
 *
 * @author Antonio Neto
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("findSecretariatUseCase")
public class FindSecretariatUseCase implements IUseCase<FindSecretariatCommand.Input, Mono<FindSecretariatCommand.Output>> {

    /**
     * Repositório reativo responsável pelas operações de consulta de Secretarias.
     */
    private final SecretariatRepository secretariatRepository;

    /**
     * Executa o caso de uso para localizar uma Secretaria pelo UUID.
     *
     * @param input DTO contendo o UUID da Secretaria buscada
     * @return {@link Mono} encapsulando o DTO de retorno, ou erro caso não encontrada
     */
    @Override
    public Mono<FindSecretariatCommand.Output> execute(FindSecretariatCommand.Input input) {

        log.info("Consultando Secretaria por UUID: {}", input.uuid());

        return secretariatRepository.findByUuid(input.uuid())
                .map(SecretariatMapper.Find.PO_TO_OUTPUT)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Nenhuma Secretaria encontrada para UUID: {}", input.uuid());
                    return Mono.error(new NotFoundResourceException("Secretaria não encontrada"));
                }))
                .doOnSuccess(output -> log.info("Secretaria encontrada: {}", output))
                .onErrorMap(error -> {
                    log.error("Erro ao buscar Secretaria: {}", error.getMessage(), error);
                    return new RuntimeException("Falha na consulta de Secretaria");
                });
    }
}