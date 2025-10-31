package br.com.facilit.kanban.data;

import br.com.facilit.kanban.organizational.domain.dto.SecretariatDTO;
import io.github.kelari.atg.data.DataLoad;

import java.util.Map;

public class SecretariatCreateDataLoad400 implements DataLoad {
    @Override
    public Map<String, Object> load() {
        SecretariatDTO.Request request = new SecretariatDTO.Request(
                "Secretaria Financeira" +
                        "Secretaria Financeira" +
                        "Secretaria Financeira" +
                        "Secretaria Financeira" +
                        "Secretaria Financeira" +
                        "Secretaria Financeira" +
                        "Secretaria Financeira" +
                        "Secretaria Financeira" +
                        "Secretaria Financeira",
                "Responsável pelo setor financeiro"
        );

        return Map.of("request", request);
    }
}
