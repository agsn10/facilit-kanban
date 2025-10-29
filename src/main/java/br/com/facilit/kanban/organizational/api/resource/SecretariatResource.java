package br.com.facilit.kanban.organizational.api.resource;

import br.com.facilit.kanban.organizational.api.openapi.SecretariatOpenApi;
import br.com.facilit.kanban.organizational.application.ppi.SecretariatPort;
import br.com.facilit.kanban.organizational.domain.dto.SecretariatDTO;
import br.com.facilit.kanban.shared.aop.ReactiveTransactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Recurso REST responsável por expor os endpoints da entidade Secretaria.
 *
 * <p>Esta classe implementa a interface {@link SecretariatOpenApi} e utiliza
 * a porta de aplicação {@link SecretariatPort} para realizar operações
 * de criação, consulta, atualização e exclusão de Secretarias no sistema Kanban.</p>
 *
 * <p>Todos os métodos seguem o padrão reativo, retornando {@link Mono} e {@link Page}
 * para suportar operações não bloqueantes e consultas paginadas.</p>
 *
 * <p>Base da URL: {@code /api/secretariats}</p>
 **
 * @author Antonio Neto
 */
@RestController
@RequestMapping("/api/secretariats")
@RequiredArgsConstructor
public class SecretariatResource implements SecretariatOpenApi {

    private final SecretariatPort secretariatPort;

    /**
     * Cria uma nova Secretaria no sistema.
     *
     * @param request objeto contendo os dados necessários para criação
     * @return {@link Mono} contendo os dados da Secretaria criada
     */
    @PostMapping
    @ReactiveTransactional
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SecretariatDTO.Response> create(@Valid @RequestBody SecretariatDTO.Request request) {
        return secretariatPort.create(request);
    }

    /**
     * Lista todas as Secretarias cadastradas com suporte a paginação.
     *
     * @param pageable parâmetros de paginação e ordenação
     * @return {@link Mono} contendo uma {@link Page} com os dados das Secretarias
     *
     * API: GET /secretariats?page=0&size=10&sort=name,asc
     */
    @GetMapping
    public Mono<Page<SecretariatDTO.Response>> list(@PageableDefault(page = 0, size = 20, sort = "name") Pageable pageable) {
        return secretariatPort.list(pageable);
    }

    /**
     * Consulta uma Secretaria pelo seu identificador único (UUID).
     *
     * @param id identificador da Secretaria
     * @return {@link Mono} contendo a Secretaria encontrada, ou vazio se não existir
     */
    @GetMapping("/{id}")
    public Mono<SecretariatDTO.Response> find(@PathVariable UUID id) {
        return secretariatPort.find(id);
    }

    /**
     * Atualiza os dados de uma Secretaria existente.
     *
     * @param id identificador da Secretaria
     * @param request objeto com os novos dados para atualização
     * @return {@link Mono} contendo os dados atualizados da Secretaria
     */
    @PutMapping("/{id}")
    @ReactiveTransactional
    public Mono<SecretariatDTO.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody SecretariatDTO.Request request) {
        return secretariatPort.update(id, request);
    }

    /**
     * Remove uma Secretaria do sistema pelo seu identificador.
     *
     * @param id identificador da Secretaria
     * @return {@link Mono} vazio indicando sucesso na remoção
     */
    @DeleteMapping("/{id}")
    @ReactiveTransactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        return secretariatPort.delete(id);
    }
}
