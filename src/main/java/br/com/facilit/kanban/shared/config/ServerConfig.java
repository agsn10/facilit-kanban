package br.com.facilit.kanban.shared.config;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public WebServerFactoryCustomizer<NettyReactiveWebServerFactory> redirectCustomizer() {
        return factory -> factory.addServerCustomizers(httpServer ->
                httpServer.port(8080)
                        .handle((request, response) -> {
                            String host = request.requestHeaders().get("Host");
                            String location = "https://" + host + request.uri();
                            return response.status(HttpResponseStatus.PERMANENT_REDIRECT)
                                    .header("Location", location)
                                    .send();
                        })
        );
    }
}
