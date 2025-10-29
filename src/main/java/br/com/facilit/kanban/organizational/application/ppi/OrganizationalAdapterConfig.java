package br.com.facilit.kanban.organizational.application.ppi;

import br.com.facilit.kanban.organizational.application.command.*;
import br.com.facilit.kanban.organizational.domain.dto.SecretariatDTO;
import br.com.facilit.kanban.organizational.mapping.SecretariatMapper;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Configuração responsável por instanciar o adaptador para a porta {@link SecretariatPort},
 * conectando os métodos da camada de Resource aos casos de uso correspondentes.
 *
 * <p>Esta classe implementa o padrão da Arquitetura Hexagonal (Ports & Adapters),
 * atuando como Adapter que:
 * <ul>
 *   <li>Converte {@link SecretariatDTO.Request} em comandos da aplicação</li>
 *   <li>Executa os casos de uso via {@link IUseCase}</li>
 *   <li>Mapeia os resultados para {@link SecretariatDTO.Response}</li>
 * </ul>
 * </p>
 *
 * <p>Permite que diferentes implementações dos casos de uso sejam injetadas sem
 * alterar a lógica de roteamento dos endpoints REST.</p>
 *
 * <p>Todos os métodos seguem o padrão reativo utilizando {@link Mono} e {@link Page}.</p>
 *
 * @author Antonio Neto
 */
@Lazy
@Configuration
public class OrganizationalAdapterConfig {

    /**
     * Cria a implementação do {@link SecretariatPort} conectando os casos de uso
     * de criação, listagem, consulta, atualização e exclusão de Secretarias.
     *
     * @param createSecretariat caso de uso para criação de Secretaria
     * @param listSecretariat caso de uso para listagem de Secretarias
     * @param findSecretariat caso de uso para consulta de Secretaria
     * @param updateSecretariat caso de uso para atualização de Secretaria
     * @param deleteSecretariat caso de uso para exclusão de Secretaria
     * @return implementação de {@link SecretariatPort} que delega para os casos de uso
     */
    @Bean("secretariatAdpter")
    public SecretariatPort secretariatAdpter(
            @Qualifier("createSecretariatUseCase")
            IUseCase<CreateSecretariatCommand.Input, Mono<CreateSecretariatCommand.Output>> createSecretariat,
            @Qualifier("listSecretariatUseCase")
            IUseCase<ListSecretariatCommand.Input, Mono<Page<ListSecretariatCommand.Output>>> listSecretariat,
            @Qualifier("findSecretariatUseCase")
            IUseCase<FindSecretariatCommand.Input, Mono<FindSecretariatCommand.Output>> findSecretariat,
            @Qualifier("updateSecretariatUseCase")
            IUseCase<UpdateSecretariatCommand.Input, Mono<UpdateSecretariatCommand.Output>> updateSecretariat,
            @Qualifier("deleteSecretariatUseCase")
            IUseCase<DeleteSecretariatCommand.Input, Mono<Void>> deleteSecretariat) {

        return new SecretariatPort() {

            @Override
            public Mono<SecretariatDTO.Response> create(SecretariatDTO.Request request) {
                var input = SecretariatMapper.Create.REQUEST_TO_INPUT.apply(request);
                return createSecretariat.execute(input).map(SecretariatMapper.Create.OUTPUT_TO_RESPONSE);
            }

            @Override
            public Mono<Page<SecretariatDTO.Response>> list(Pageable pageable) {
                var input = SecretariatMapper.List.REQUEST_TO_INPUT.apply(pageable);
                return listSecretariat.execute(input).map(SecretariatMapper.List.PAGE_OUTPUT_TO_PAGE_RESPONSE);
            }

            @Override
            public Mono<SecretariatDTO.Response> find(UUID id) {
                var input = SecretariatMapper.Find.REQUEST_TO_INPUT.apply(id);
                return findSecretariat.execute(input).map(SecretariatMapper.Find.OUTPUT_TO_RESPONSE);
            }

            @Override
            public Mono<SecretariatDTO.Response> update(UUID id, SecretariatDTO.Request request) {
                var input = SecretariatMapper.Update.REQUEST_TO_INPUT.apply(id, request);
                return updateSecretariat.execute(input).map(SecretariatMapper.Update.OUTPUT_TO_RESPONSE);
            }

            @Override
            public Mono<Void> delete(UUID id) {
                var input = SecretariatMapper.Delete.REQUEST_TO_INPUT.apply(id);
                return deleteSecretariat.execute(input);
            }
        };
    }
}
