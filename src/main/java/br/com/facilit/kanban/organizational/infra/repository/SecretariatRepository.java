package br.com.facilit.kanban.organizational.infra.repository;

import br.com.facilit.kanban.organizational.domain.po.SecretariatPO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repositório reativo para gerenciamento da entidade {@link SecretariatPO}.
 *
 * <p>Fornece operações CRUD reativas padrão através de {@link ReactiveCrudRepository}
 * e métodos adicionais específicos do domínio de Secretarias.</p>
 *
 * <p>Seguindo a Arquitetura Hexagonal, este repositório atua como
 * porta de saída para persistência de Secretarias.</p>
 *
 * @author Antonio Neto
 */
@Repository
public interface SecretariatRepository extends ReactiveCrudRepository<SecretariatPO, Long> {

    /**
     * Busca todas as secretarias com paginação e ordenação por nome ascendente.
     *
     * @param offset índice inicial da página
     * @param limit  quantidade máxima de registros a serem retornados
     * @return {@link Flux} de {@link SecretariatPO} contendo os registros da página
     */
    @Query("SELECT * FROM secretariat ORDER BY name ASC LIMIT :limit OFFSET :offset")
    Flux<SecretariatPO> findAllWithPagination(long offset, long limit);

    /**
     * Conta o total de registros existentes na tabela de Secretarias.
     *
     * @return {@link Mono} com o total de registros
     */
    @Query("SELECT COUNT(*) FROM secretariat")
    Mono<Long> countAll();

    /**
     * Busca uma Secretaria pelo seu UUID.
     *
     * @param uuid UUID da Secretaria
     * @return {@link Mono} com o {@link SecretariatPO} correspondente, ou {@link Mono#empty()} caso não exista
     */
    @Query("SELECT * FROM secretariat WHERE uuid = :uuid")
    Mono<SecretariatPO> findByUuid(UUID uuid);
}
