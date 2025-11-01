package br.com.facilit.kanban.shared.config;

import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração responsável por ajustar a coleta de métricas da aplicação.
 * <p>
 * Esta classe define filtros para excluir determinadas URIs do registro de métricas
 * no Micrometer/Prometheus, evitando poluição com métricas irrelevantes,
 * como endpoints de documentação Swagger, recursos estáticos e favicon.
 * </p>
 *
 * <p>
 * As métricas expostas via Actuator em <code>/actuator/prometheus</code>
 * serão filtradas para não incluir os seguintes endpoints:
 * <ul>
 *   <li><b>/swagger*</b> — interface Swagger UI</li>
 *   <li><b>/v3/api-docs*</b> — documentação OpenAPI</li>
 *   <li><b>/webjars*</b> — recursos estáticos do Swagger</li>
 *   <li><b>/favicon*</b> — ícone padrão do navegador</li>
 *   <li><b>/</b> — endpoint raiz</li>
 * </ul>
 * </p>
 *
 * <p>
 * Essa configuração ajuda a manter o Prometheus e Grafana focados apenas nas métricas
 * relevantes da API principal, como endpoints de negócio.
 * </p>
 *
 * @author Antonio Neto
 * @since 1.0
 */
@Configuration
public class MetricsConfig {

    /**
     * Cria um {@link MeterFilter} que impede a exportação de métricas
     * associadas a endpoints estáticos e da documentação Swagger.
     *
     * @return um {@link MeterFilter} configurado para negar métricas irrelevantes
     */
    @Bean
    public MeterFilter excludeSwaggerAndStatic() {
        return MeterFilter.deny(id -> {
            String uri = id.getTag("uri");
            if (uri == null) return false;

            return uri.startsWith("/swagger")
                    || uri.startsWith("/v3/api-docs")
                    || uri.startsWith("/webjars")
                    || uri.startsWith("/favicon")
                    || uri.equals("/");
        });
    }
}
