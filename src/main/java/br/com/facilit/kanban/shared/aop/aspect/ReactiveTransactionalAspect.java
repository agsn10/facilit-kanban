package br.com.facilit.kanban.shared.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Aspecto responsável por aplicar transações reativas em métodos
 * anotados com {@link br.com.facilit.kanban.shared.aop.ReactiveTransactional}.
 *
 * <p>Este aspecto utiliza um {@link TransactionalOperator} para garantir
 * rollback automático em caso de erro dentro do pipeline reativo.</p>
 *
 * <p>Suporta os seguintes tipos de retorno:
 * <ul>
 *     <li>{@code Mono<T>}</li>
 *     <li>{@code Mono<Void>}</li>
 *     <li>{@code Flux<T>}</li>
 * </ul></p>
 *
 * <p>Em caso de retorno inválido, uma {@link IllegalStateException}
 * será lançada.</p>
 */
@Slf4j
@Aspect
@Component
public class ReactiveTransactionalAspect {

    private final TransactionalOperator txOperator;

    /**
     * Construtor para injetar o operador transacional reativo.
     *
     * @param txOperator operador transacional usado para controle transacional
     */
    public ReactiveTransactionalAspect(TransactionalOperator txOperator) {
        this.txOperator = txOperator;
    }

    /**
     * Intercepta métodos anotados com {@link br.com.facilit.kanban.shared.aop.ReactiveTransactional} e aplica
     * transação reativa.
     *
     * <p>Logs estruturados são gerados para facilitar rastreamento e auditoria.</p>
     *
     * @param pjp ponto de execução interceptado
     * @return pipeline reativo envolvido em transação
     * @throws Throwable caso ocorra falha antes de criar o pipeline
     */
    @Around("@annotation(ReactiveTransactional)")
    public Object applyTransaction(ProceedingJoinPoint pjp) throws Throwable {
        String txId = UUID.randomUUID().toString();
        log.info("🔄 Iniciando transação reativa | txId={}", txId);

        Object result = pjp.proceed();

        if (result instanceof Mono<?> monoResult) {
            return monoResult
                    .doOnSubscribe(s -> log.info("📌 Executando Mono | txId={}", txId))
                    .doOnError(err -> log.error("❌ Rollback Mono | txId={} | motivo={}", txId, err.getMessage()))
                    .doOnSuccess(v -> log.info("✅ Commit Mono | txId={}", txId))
                    .as(txOperator::transactional);
        }

        if (result instanceof Flux<?> fluxResult) {
            return fluxResult
                    .doOnSubscribe(s -> log.info("📌 Executando Flux | txId={}", txId))
                    .doOnError(err -> log.error("❌ Rollback Flux | txId={} | motivo={}", txId, err.getMessage()))
                    .doOnComplete(() -> log.info("✅ Commit Flux | txId={}", txId))
                    .as(txOperator::transactional);
        }

        throw new IllegalStateException("@ReactiveTransactional só pode ser usado com Mono ou Flux.");
    }
}
