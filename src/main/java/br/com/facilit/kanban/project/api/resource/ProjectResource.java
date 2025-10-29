package br.com.facilit.kanban.project.api.resource;

import br.com.facilit.kanban.project.api.openapi.ProjectOpenApi;
import br.com.facilit.kanban.project.application.ppi.ProjectPort;
import br.com.facilit.kanban.project.domain.dto.ProjectDTO;
import br.com.facilit.kanban.project.domain.enums.StatusProject;
import br.com.facilit.kanban.shared.aop.ReactiveTransactional;
import br.com.facilit.kanban.shared.domain.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Recurso REST para gerenciamento de projetos.
 * <p>
 * Esta classe fornece endpoints para criar, listar, consultar, atualizar e alterar o status de projetos
 * em um sistema Kanban. Todos os endpoints retornam tipos reativos {@link Mono} ou {@link Flux} do
 * ProjectDTO.
 * </p>
 *
 * @author Antonio Neto
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectResource implements ProjectOpenApi {

    private final ProjectPort projectPort;

    /**
     * Cria um novo projeto com base nos dados enviados.
     *
     * @param request objeto contendo os dados necessários para criar o projeto.
     * @return {@link Mono} contendo o projeto criado no formato {@link ProjectDTO.Response}.
     */
    @PostMapping
    @ReactiveTransactional
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProjectDTO.Response> create(@Valid @RequestBody ProjectDTO.Request request) {
        return projectPort.create(request);
    }

    /**
     * Lista os projetos cadastrados de forma paginada.
     *
     * <p>O endpoint permite paginação usando {@link Pageable}, retornando um {@link Mono} com
     * uma {@link org.springframework.data.domain.Page} de {@link ProjectDTO.Response}.</p>
     *
     * <p>Parâmetros de paginação (page, size, sort) podem ser passados na query string:
     * <pre>
     * GET /projects?page=0&size=20&sort=name,asc
     * </pre>
     * </p>
     *
     * @param pageable objeto {@link Pageable} contendo número da página, tamanho da página e ordenação
     * @return {@link Mono} contendo uma {@link org.springframework.data.domain.Page} de {@link ProjectDTO.Response}
     */
    @GetMapping
    public Mono<PageResponse<ProjectDTO.Response>> list(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size,
                                                        @RequestParam(defaultValue = "name") String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return projectPort.list(pageable);
    }


    /**
     * Busca um projeto existente pelo seu identificador único.
     *
     * @param id identificador único do projeto.
     * @return {@link Mono} com o projeto encontrado ou vazio caso não exista.
     */
    @GetMapping("/{id}")
    public Mono<ProjectDTO.Response> find(@PathVariable UUID id) {
        return projectPort.find(id);
    }

    /**
     * Atualiza os dados de um projeto existente.
     *
     * @param id identificador único do projeto que será atualizado.
     * @param request objeto contendo os novos dados do projeto.
     * @return {@link Mono} contendo o projeto atualizado no formato {@link ProjectDTO.Response}.
     */
    @PutMapping("/{id}")
    @ReactiveTransactional
    public Mono<ProjectDTO.Response> update(@PathVariable UUID id, @RequestBody ProjectDTO.Request request) {
        return projectPort.update(id, request);
    }

    /**
     * Atualiza apenas o status de um projeto existente.
     *
     * @param id identificador único do projeto.
     * @param status novo status a ser aplicado ao projeto.
     * @return {@link Mono} contendo o projeto após alteração de status.
     */
    @PatchMapping("/{id}/status")
    @ReactiveTransactional
    public Mono<ProjectDTO.Response> changeStatus(@PathVariable UUID id, @RequestParam StatusProject status) {
        return projectPort.changeStatus(id, status);
    }
}