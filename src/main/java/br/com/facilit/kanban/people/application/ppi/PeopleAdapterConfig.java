package br.com.facilit.kanban.people.application.ppi;

import br.com.facilit.kanban.people.application.command.*;
import br.com.facilit.kanban.people.domain.dto.AccountableDTO;
import br.com.facilit.kanban.people.mapping.AccountableMapper;
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
 * Configuração responsável por instanciar o adaptador para a porta {@link AccountablePort},
 * conectando os métodos da camada de Resource aos casos de uso correspondentes
 * relacionados à gestão de responsáveis (Accountables) no sistema Kanban.
 *
 * <p>Esta classe implementa o padrão da Arquitetura Hexagonal (Ports & Adapters), atuando como Adapter que:
 * <ul>
 *   <li>Converte {@link AccountableDTO.Request} em comandos da aplicação</li>
 *   <li>Executa os casos de uso via {@link IUseCase}</li>
 *   <li>Mapeia os resultados para {@link AccountableDTO.Response}</li>
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
public class PeopleAdapterConfig {

    /**
     * Cria a implementação do {@link AccountablePort} conectando os casos de uso
     * de criação, listagem, consulta, atualização e exclusão de Accountables.
     *
     * @param createAccountable caso de uso para criação de Accountable
     * @param listAccountable caso de uso para listagem de Accountables
     * @param findAccountable caso de uso para consulta de Accountable
     * @param updateAccountable caso de uso para atualização de Accountable
     * @param deleteAccountable caso de uso para exclusão de Accountable
     * @return implementação de {@link AccountablePort} que delega para os casos de uso
     */
    @Bean("accountableAdpter")
    public AccountablePort accountableAdpter(
            @Qualifier("createAccountableUseCase")
            IUseCase<CreateAccountableCommand.Input, Mono<CreateAccountableCommand.Output>> createAccountable,
            @Qualifier("listAccountableUseCase")
            IUseCase<ListAccountableCommand.Input, Mono<Page<ListAccountableCommand.Output>>> listAccountable,
            @Qualifier("findAccountableUseCase")
            IUseCase<FindAccountableCommand.Input, Mono<FindAccountableCommand.Output>> findAccountable,
            @Qualifier("updateAccountableUseCase")
            IUseCase<UpdateAccountableCommand.Input, Mono<UpdateAccountableCommand.Output>> updateAccountable,
            @Qualifier("deleteAccountableUseCase")
            IUseCase<DeleteAccountableCommand.Input, Mono<Void>> deleteAccountable
    ) {
        return new AccountablePort() {

            @Override
            public Mono<AccountableDTO.Response> create(AccountableDTO.Request request) {
                CreateAccountableCommand.Input input = AccountableMapper.Create.REQUEST_TO_INPUT.apply(request);
                return createAccountable.execute(input).map(AccountableMapper.Create.OUTPUT_TO_RESPONSE);
            }

            @Override
            public Mono<Page<AccountableDTO.Response>> list(Pageable pageable) {
                ListAccountableCommand.Input input = AccountableMapper.List.REQUEST_TO_INPUT.apply(pageable);
                return listAccountable.execute(input).map(AccountableMapper.List.PAGE_OUTPUT_TO_PAGE_RESPONSE);
            }

            @Override
            public Mono<AccountableDTO.Response> findById(UUID id) {
                FindAccountableCommand.Input input = AccountableMapper.Find.REQUEST_TO_INPUT.apply(id);
                return findAccountable.execute(input).map(AccountableMapper.Find.OUTPUT_TO_RESPONSE);
            }

            @Override
            public Mono<AccountableDTO.Response> update(UUID id, AccountableDTO.Request request) {
                UpdateAccountableCommand.Input input = AccountableMapper.Update.REQUEST_TO_INPUT.apply(id, request);
                return updateAccountable.execute(input).map(AccountableMapper.Update.OUTPUT_TO_RESPONSE);
            }

            @Override
            public Mono<Void> delete(UUID id) {
                DeleteAccountableCommand.Input input = AccountableMapper.Delete.REQUEST_TO_INPUT.apply(id);
                return deleteAccountable.execute(input);
            }
        };
    }
}
