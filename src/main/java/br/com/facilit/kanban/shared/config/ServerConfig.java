package br.com.facilit.kanban.shared.config;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
public class ServerConfig {

    /*@Bean
    public WebServerFactoryCustomizer<NettyReactiveWebServerFactory> redirectCustomizer() {
        return factory -> factory.addServerCustomizers(httpServer ->
                httpServer.port(8043)
                        .handle((request, response) -> {
                            String host = request.requestHeaders().get("Host");
                            String location = "https://" + host + request.uri();
                            return response.status(HttpResponseStatus.PERMANENT_REDIRECT)
                                    .header("Location", location)
                                    .send();
                        })
        );
    } */

    /**
     * Ignorar verificação SSL (apenas em dev/teste, nunca em produção)
     * */
    @Bean
    public WebClient webClient(WebClient.Builder builder) throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        HttpClient httpClient = HttpClient.create()
                .secure(t -> t.sslContext(sslContext));

        return builder.clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }
}
