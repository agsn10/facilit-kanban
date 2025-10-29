package br.com.facilit.kanban.project.application.ppi;

import br.com.facilit.kanban.project.domain.dto.ProjectDTO;
import br.com.facilit.kanban.project.domain.enums.StatusProject;
import br.com.facilit.kanban.shared.domain.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Interface que define as portas do domínio para operações com Projetos.
 * Segue o padrão Port/Adapter (Hexagonal Architecture), abstraindo
 * a implementação da camada de infraestrutura.
 */
public interface ProjectPort {

    /**
     * Cria um novo projeto no sistema Kanban.
     *
     * @param request DTO contendo os dados do projeto a ser criado
     * @return {@link Mono} contendo o {@link ProjectDTO.Response} do projeto criado
     */
    Mono<ProjectDTO.Response> create(ProjectDTO.Request request);

    /**
     * Lista os projetos cadastrados de forma paginada.
     *
     * @param pageable objeto {@link Pageable} contendo número da página, tamanho da página e ordenação
     * @return {@link Mono} contendo uma {@link Page} de {@link ProjectDTO.Response} com os projetos da página solicitada
     */
    Mono<PageResponse<ProjectDTO.Response>> list(Pageable pageable);

    /**
     * Busca um projeto pelo seu identificador único (UUID).
     *
     * @param id UUID do projeto a ser buscado
     * @return {@link Mono} contendo o {@link ProjectDTO.Response} correspondente, ou vazio caso não encontrado
     */
    Mono<ProjectDTO.Response> find(UUID id);

    /**
     * Atualiza um projeto existente com novos dados.
     *
     * @param id UUID do projeto a ser atualizado
     * @param request DTO contendo os novos dados do projeto
     * @return {@link Mono} contendo o {@link ProjectDTO.Response} atualizado
     */
    Mono<ProjectDTO.Response> update(UUID id, ProjectDTO.Request request);

    /**
     * Altera o status de um projeto existente.
     *
     * @param id UUID do projeto a ser alterado
     * @param status novo {@link StatusProject} a ser aplicado
     * @return {@link Mono} contendo o {@link ProjectDTO.Response} atualizado com o novo status
     */
    Mono<ProjectDTO.Response> changeStatus(UUID id, StatusProject status);
}