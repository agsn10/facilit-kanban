package br.com.facilit.kanban.people.api.resource;

import br.com.facilit.kanban.people.api.openapi.AccountableOpenApi;
import br.com.facilit.kanban.people.application.ppi.AccountablePort;
import br.com.facilit.kanban.people.domain.dto.AccountableDTO;
import br.com.facilit.kanban.shared.aop.ReactiveTransactional;
import br.com.facilit.kanban.shared.domain.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Recurso REST responsável pelo gerenciamento dos responsáveis (Accountables)
 * dentro do sistema Kanban.
 *
 * <p>Oferece operações CRUD de forma reativa utilizando Project Reactor
 * e segue o contrato definido na interface {@link AccountableOpenApi}.</p>
 *
 * @author Antonio Neto
 */
@RestController
@RequestMapping("/api/accountables")
@RequiredArgsConstructor
public class AccountableResource implements AccountableOpenApi {

    private final AccountablePort accountablePort;

    /**
     * Cria um novo responsável no sistema.
     *
     * @param request objeto contendo os dados para criação do responsável
     * @return um {@link Mono} emitindo o DTO de resposta do responsável criado
     */
    @PostMapping
    @ReactiveTransactional
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountableDTO.Response> create(@Valid @RequestBody AccountableDTO.Request request) {
        return accountablePort.create(request);
    }

    /**
     * Lista de forma paginada todos os responsáveis cadastrados.
     *
     * @param pageable informações de paginação e ordenação
     * @return um {@link Mono} contendo a página de responsáveis
     */
    @GetMapping
    public Mono<PageResponse<AccountableDTO.Response>> list(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "20") int size,
                                                            @RequestParam(defaultValue = "name") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return accountablePort.list(pageable);
    }

    /**
     * Recupera um responsável pelo seu identificador único.
     *
     * @param id identificador do responsável
     * @return um {@link Mono} emitindo o responsável correspondente,
     *         ou erro caso não seja encontrado
     */
    @GetMapping("/{id}")
    public Mono<AccountableDTO.Response> findById(@PathVariable UUID id) {
        return accountablePort.findById(id);
    }

    /**
     * Atualiza os dados de um responsável existente no sistema.
     *
     * @param id identificador do responsável
     * @param request objeto contendo os dados atualizados
     * @return um {@link Mono} emitindo o DTO de resposta atualizado
     */
    @PutMapping("/{id}")
    @ReactiveTransactional
    public Mono<AccountableDTO.Response> update(@PathVariable UUID id, @Valid @RequestBody AccountableDTO.Request request) {
        return accountablePort.update(id, request);
    }

    /**
     * Remove um responsável do sistema.
     *
     * @param id identificador do responsável
     * @return um {@link Mono} vazio com status HTTP 204 caso a remoção seja bem-sucedida
     */
    @DeleteMapping("/{id}")
    @ReactiveTransactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        return accountablePort.delete(id);
    }
}