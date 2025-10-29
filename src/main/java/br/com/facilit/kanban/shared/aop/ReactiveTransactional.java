package br.com.facilit.kanban.shared.aop;


import java.lang.annotation.*;

/**
 * Indica que o método deve ser executado dentro de uma transação reativa.
 *
 * <p>Essa anotação deve ser usada em conjunto com o aspecto {@link ReactiveTransactionalAspect}
 * para aplicar transações reativas automaticamente aos métodos anotados.</p>
 *
 * <p>Somente métodos que retornam {@link reactor.core.publisher.Mono} ou {@link reactor.core.publisher.Flux}
 * são suportados. Métodos com outros tipos de retorno lançarão uma exceção em tempo de execução.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 * @ReactiveTransactional
 * public Mono<Void> salvarEntidade(Entidade e) {
 *     return repository.save(e).then();
 * }
 * }</pre>
 *
 * @see ReactiveTransactionalAspect
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReactiveTransactional {
}
