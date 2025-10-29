package br.com.facilit.kanban.people.application.ppi;

import br.com.facilit.kanban.people.domain.dto.AccountableDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Porta da aplicação responsável por definir as operações de caso de uso
 * relacionadas à gestão de responsáveis (Accountables) no sistema Kanban.
 *
 * <p>Esta interface representa um contrato da camada de aplicação
 * dentro da Arquitetura Hexagonal (Ports & Adapters), permitindo que
 * diferentes implementações (adapters) possam fornecer o comportamento
 * previsto pelos casos de uso para o domínio de pessoas.</p>
 *
 * @author Antonio Neto
 */
public interface AccountablePort {

    /**
     * Cria um novo responsável no sistema.
     *
     * @param request dados necessários para criação
     * @return um {@link Mono} emitindo os dados do responsável criado
     */
    Mono<AccountableDTO.Response> create(AccountableDTO.Request request);

    /**
     * Lista responsáveis de forma paginada e ordenada.
     *
     * @param pageable parâmetros de paginação e ordenação
     * @return um {@link Mono} contendo a página de responsáveis
     */
    Mono<Page<AccountableDTO.Response>> list(Pageable pageable);

    public Mono<AccountableDTO.Response> findById(UUID id);

    /**
     * Atualiza os dados de um responsável existente.
     *
     * @param id identificador do responsável a ser atualizado
     * @param request dados atualizados
     * @return um {@link Mono} emitindo os dados do responsável atualizados
     */
    Mono<AccountableDTO.Response> update(UUID id, AccountableDTO.Request request);

    /**
     * Exclui um responsável com base no identificador informado.
     *
     * @param id identificador do responsável
     * @return um {@link Mono} vazio quando a remoção for bem-sucedida
     */
    Mono<Void> delete(UUID id);
}