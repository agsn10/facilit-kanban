package br.com.facilit.kanban.people.infra.repository;

import br.com.facilit.kanban.people.domain.po.AccountablePO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repositório reativo para gerenciamento da entidade {@link AccountablePO}.
 *
 * <p>Fornece operações CRUD reativas e métodos adicionais de consulta
 * específicos do domínio de responsáveis (Accountable).</p>
 *
 * <p>Seguindo a Arquitetura Hexagonal, este repositório atua como
 * porta de saída para persistência.</p>
 *
 * @author Antonio Neto
 */
@Repository
public interface AccountableRepository extends ReactiveCrudRepository<AccountablePO, Long> {

    /**
     * Verifica se já existe um responsável com o e-mail informado,
     * ignorando diferenças de caixa (case insensitive).
     *
     * @param email e-mail a ser verificado
     * @return {@link Mono} com {@code true} se o e-mail já existe, {@code false} caso contrário
     */
    Mono<Boolean> existsByEmailIgnoreCase(String email);

    /**
     * Busca um responsável pelo seu UUID.
     *
     * <p>Utiliza uma query SQL explícita para localizar o registro na tabela <code>accountable</code>.</p>
     *
     * @param uuid UUID do responsável
     * @return {@link Mono} com o {@link AccountablePO} correspondente, ou {@link Mono#empty()} caso não exista
     */
    @Query("SELECT * FROM accountable WHERE uuid = :uuid")
    Mono<AccountablePO> findByUuid(String uuid);
}
