package br.com.facilit.kanban.organizational.application.ppi;

import br.com.facilit.kanban.organizational.domain.dto.SecretariatDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Porta da aplicação responsável por definir as operações de caso de uso
 * relacionadas à gestão de Secretarias no sistema Kanban.
 *
 * <p>Esta interface representa um contrato da camada de aplicação
 * dentro da Arquitetura Hexagonal (Ports & Adapters), permitindo que
 * diferentes implementações (adapters) possam fornecer o comportamento
 * previsto pelos casos de uso para o domínio organizacional.</p>
 *
 * <p>Todos os métodos seguem o padrão reativo utilizando {@link Mono}
 * e {@link Page} para operações de consulta paginadas.</p>
 *
 * @author Antonio Neto
 */
public interface SecretariatPort {

    /**
     * Cria uma nova Secretaria no sistema.
     *
     * @param request objeto contendo os dados necessários para a criação da Secretaria
     * @return {@link Mono} contendo os dados da Secretaria criada
     */
    Mono<SecretariatDTO.Response> create(SecretariatDTO.Request request);

    /**
     * Lista todas as Secretarias cadastradas, utilizando paginação.
     *
     * @param pageable parâmetros de paginação e ordenação
     * @return {@link Mono} contendo uma {@link Page} com os dados das Secretarias
     */
    Mono<Page<SecretariatDTO.Response>> list(Pageable pageable);

    /**
     * Consulta uma Secretaria pelo seu identificador único (UUID).
     *
     * @param id identificador único da Secretaria
     * @return {@link Mono} contendo a Secretaria encontrada, ou vazio se não existir
     */
    Mono<SecretariatDTO.Response> find(UUID id);

    /**
     * Atualiza os dados de uma Secretaria existente.
     *
     * @param id identificador único da Secretaria
     * @param request objeto contendo os novos dados para atualização
     * @return {@link Mono} contendo os dados atualizados da Secretaria
     */
    Mono<SecretariatDTO.Response> update(UUID id, SecretariatDTO.Request request);

    /**
     * Remove uma Secretaria do sistema pelo seu identificador.
     *
     * @param id identificador único da Secretaria
     * @return {@link Mono} vazio indicando sucesso na remoção
     */
    Mono<Void> delete(UUID id);
}
