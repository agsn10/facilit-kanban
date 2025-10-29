package br.com.facilit.kanban.project.infra.repository;

import br.com.facilit.kanban.project.domain.po.ProjectPO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repositório reativo responsável pelo acesso e manipulação de dados da entidade
 * {@link ProjectPO} no banco.
 *
 * <p>Esta interface faz parte da camada de infraestrutura da Arquitetura Hexagonal,
 * implementando uma porta secundária para persistência utilizando o módulo
 * reativo do Spring Data.</p>
 *
 * <p>As operações retornam {@link Mono}, garantindo um fluxo assíncrono e não bloqueante.</p>
 *
 * <h3>Consultas customizadas</h3>
 * <ul>
 *     <li>{@link #findByUuid(UUID)} — Recupera um projeto através do seu UUID.</li>
 * </ul>
 */
@Repository
public interface ProjectRepository extends ReactiveCrudRepository<ProjectPO, Long> {

    /**
     * Busca um projeto com base no seu identificador público (UUID).
     *
     * @param uuid identificador único do projeto
     * @return {@link Mono} contendo o projeto encontrado, ou vazio caso não exista
     */
    @Query("SELECT * FROM project WHERE uuid = :uuid")
    Mono<ProjectPO> findByUuid(UUID uuid);
}
