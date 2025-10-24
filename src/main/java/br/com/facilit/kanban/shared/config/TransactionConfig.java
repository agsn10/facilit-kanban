package br.com.facilit.kanban.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

/**
 * Configuração responsável por registrar o bean {@link TransactionalOperator},
 * que é utilizado para aplicar transações em fluxos reativos (Mono/Flux).
 *
 * <p>Esse operador é utilizado em conjunto com o {@link ReactiveTransactionManager}
 * e permite que operações reativas sejam executadas de forma transacional
 * em ambientes que usam R2DBC ou outros recursos reativos suportados.</p>
 *
 * <p>Este bean é essencial quando se deseja aplicar transações de forma
 * programática ou através de aspectos, como no caso da anotação
 * {@code @ReactiveTransactional}.</p>
 */
@Configuration
public class TransactionConfig {

    /**
     * Cria e registra um bean do tipo {@link TransactionalOperator}, que
     * permite aplicar transações em fluxos reativos (Mono/Flux).
     *
     * @param txManager o gerenciador de transações reativas, geralmente configurado
     *                  automaticamente pelo Spring com base no datasource reativo
     * @return o operador transacional reativo
     */
    @Bean
    public TransactionalOperator transactionalOperator(ReactiveTransactionManager txManager) {
        return TransactionalOperator.create(txManager);
    }
}