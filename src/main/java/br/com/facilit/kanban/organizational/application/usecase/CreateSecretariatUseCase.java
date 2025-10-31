package br.com.facilit.kanban.organizational.application.usecase;

import br.com.facilit.kanban.organizational.application.command.CreateSecretariatCommand;
import br.com.facilit.kanban.organizational.infra.repository.SecretariatRepository;
import br.com.facilit.kanban.organizational.mapping.SecretariatMapper;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso responsável por criar uma nova Secretaria no sistema Kanban.
 *
 * <p>Recebe um {@link CreateSecretariatCommand.Input}, converte para a entidade
 * persistente, salva no banco de dados reativo e retorna um
 * {@link CreateSecretariatCommand.Output} encapsulado em {@link Mono}.</p>
 *
 * <p>Logs estruturados são gerados para sucesso e erro durante o processo de criação.</p>
 *
 * <p>Esta classe implementa {@link IUseCase} seguindo o padrão da Arquitetura Hexagonal
 * (Ports & Adapters), sendo injetada como bean Spring com o Qualifier
 * {@code createSecretariatUseCase}.</p>
 *
 * @author Antonio Neto
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("createSecretariatUseCase")
public class CreateSecretariatUseCase implements IUseCase<CreateSecretariatCommand.Input, Mono<CreateSecretariatCommand.Output>> {

    /**
     * Repositório reativo para operações de persistência de Secretaria.
     */
    private final SecretariatRepository secretariatRepository;

    /**
     * Executa o caso de uso de criação de Secretaria.
     *
     * @param input DTO de entrada contendo os dados da Secretaria a ser criada
     * @return {@link Mono} contendo o DTO de saída com as informações da Secretaria criada
     */
    @Override
    public Mono<CreateSecretariatCommand.Output> execute(CreateSecretariatCommand.Input input) {
        log.info("Iniciando criação de Secretaria: {}", input);
        var secretariatPO = SecretariatMapper.Create.INPUT_TO_PO.apply(input);
        secretariatPO.setUuid(UUID.randomUUID());
        secretariatPO.setCreatedAt(LocalDateTime.now());
        return secretariatRepository.save(secretariatPO)
                .map(SecretariatMapper.Create.PO_TO_OUTPUT)
                .doOnSuccess(output -> log.info("Secretaria criada com sucesso: {}", output))
                .onErrorMap(error -> {
                    log.error("Erro ao criar secretaria: {}", error.getMessage(), error);
                    return new RuntimeException("Falha ao criar secretaria");
                });
    }
}