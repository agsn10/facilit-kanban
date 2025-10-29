package br.com.facilit.kanban.project.domain.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa a entidade de persistência de um Projeto no Kanban.
 * Contém informações de identificação, status, prazos e indicadores
 * relacionados ao acompanhamento do ciclo de vida do projeto.
 *
 * <p>Utilizada pela camada de persistência dentro da Arquitetura Hexagonal,
 * mapeando diretamente a tabela {@code project} no banco de dados.</p>
 *
 * <p>Os campos de data/hora utilizam {@link Instant} para registros
 * relacionados a eventos, com controle em UTC.</p>
 *
 * @author Antonio Neto
 */
@Table("project")
public class ProjectPO implements Serializable {

    /** Identificador único do projeto no banco de dados (chave primária). */
    @Id
    private Long id;

    /** Nome identificador do projeto. */
    @Column("name")
    private String name;

    /** Status atual do projeto conforme fluxo de Kanban. */
    @Column("status")
    private String status;

    /** Identificador universal do projeto para comunicação externa segura. */
    @Column("uuid")
    private UUID uuid;

    /** Data e hora prevista para início do projeto (UTC). */
    @Column("expected_start")
    private Instant expectedStart;

    /** Data e hora prevista para término do projeto (UTC). */
    @Column("expected_thermal")
    private Instant expectedThermal;

    /** Data e hora real do início do projeto (UTC). */
    @Column("start_actual")
    private Instant startActual;

    /** Data e hora real de término do projeto (UTC). */
    @Column("thermal_actual")
    private Instant thermalActual;

    /** Quantidade de dias de atraso do projeto com base no prazo previsto. */
    @Column("days_late")
    private Integer daysLate;

    /** Data e hora de criação do registro no sistema. */
    @Column("created_at")
    private LocalDateTime createdAt;

    /** Data e hora da última atualização do registro. */
    @Column("updated_at")
    private LocalDateTime updatedAt;

    /** Percentual restante do tempo esperado para conclusão do projeto. */
    @Column("percentage_of_tim_remaining")
    private Double percentageOfTimeRemaining;

    @Column("secretariat_id")
    private Long secretariatId;

    public ProjectPO() {
    }

    public ProjectPO(Long id, String name, String status, Instant expectedStart, Instant expectedThermal, Instant startActual, Instant thermalActual, Integer daysLate, Double percentageOfTimeRemaining, Long secretariatId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.expectedStart = expectedStart;
        this.expectedThermal = expectedThermal;
        this.startActual = startActual;
        this.thermalActual = thermalActual;
        this.daysLate = daysLate;
        this.percentageOfTimeRemaining = percentageOfTimeRemaining;
        this.secretariatId = secretariatId;
    }

    public ProjectPO(String name, String status, Instant expectedStart, Instant expectedThermal, Instant startActual, Instant thermalActual, Integer daysLate, Double percentageOfTimeRemaining, Long secretariatId) {
        this.name = name;
        this.status = status;
        this.expectedStart = expectedStart;
        this.expectedThermal = expectedThermal;
        this.startActual = startActual;
        this.thermalActual = thermalActual;
        this.daysLate = daysLate;
        this.percentageOfTimeRemaining = percentageOfTimeRemaining;
        this.secretariatId = secretariatId;
    }

    // Getters / Setters

    /** @return identificador do projeto */
    public Long getId() {
        return id;
    }
    /** @param id define o identificador do projeto */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return nome do projeto */
    public String getName() {
        return name;
    }
    /** @param name define o nome do projeto */
    public void setName(String name) {
        this.name = name;
    }

    /** @return status atual do projeto */
    public String getStatus() {
        return status;
    }
    /** @param status define o status do projeto */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return UUID do projeto */
    public UUID getUuid() {
        return uuid;
    }
    /** @param uuid define o UUID do projeto */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /** @return data prevista de início */
    public Instant getExpectedStart() {
        return expectedStart;
    }
    /** @param expectedStart define a data prevista de início */
    public void setExpectedStart(Instant expectedStart) {
        this.expectedStart = expectedStart;
    }

    /** @return data prevista de término */
    public Instant getExpectedThermal() {
        return expectedThermal;
    }
    /** @param expectedThermal define a data prevista de término */
    public void setExpectedThermal(Instant expectedThermal) {
        this.expectedThermal = expectedThermal;
    }

    /** @return data real de início */
    public Instant getStartActual() {
        return startActual;
    }
    /** @param startActual define a data real de início */
    public void setStartActual(Instant startActual) {
        this.startActual = startActual;
    }

    /** @return data real de término */
    public Instant getThermalActual() {
        return thermalActual;
    }
    /** @param thermalActual define a data real de término */
    public void setThermalActual(Instant thermalActual) {
        this.thermalActual = thermalActual;
    }

    /** @return quantidade de dias de atraso do projeto */
    public Integer getDaysLate() {
        return daysLate;
    }
    /** @param daysLate define a quantidade de dias de atraso */
    public void setDaysLate(Integer daysLate) {
        this.daysLate = daysLate;
    }

    /** @return percentual de tempo restante */
    public Double getPercentageOfTimeRemaining() {
        return percentageOfTimeRemaining;
    }
    /** @param percentageOfTimeRemaining define o percentual de tempo restante */
    public void setPercentageOfTimeRemaining(Double percentageOfTimeRemaining) {
        this.percentageOfTimeRemaining = percentageOfTimeRemaining;
    }

    /** @return data de criação do registro */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    /** @param createdAt define a data de criação do registro */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /** @return data da última atualização do registro */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    /** @param updatedAt define a data da última atualização */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getSecretariatId() {
        return secretariatId;
    }

    public void setSecretariatId(Long secretariatId) {
        this.secretariatId = secretariatId;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ProjectPO projectPO = (ProjectPO) object;
        return Objects.equals(id, projectPO.id) && Objects.equals(name, projectPO.name) && Objects.equals(status, projectPO.status) && Objects.equals(uuid, projectPO.uuid) && Objects.equals(expectedStart, projectPO.expectedStart) && Objects.equals(expectedThermal, projectPO.expectedThermal) && Objects.equals(startActual, projectPO.startActual) && Objects.equals(thermalActual, projectPO.thermalActual) && Objects.equals(daysLate, projectPO.daysLate) && Objects.equals(createdAt, projectPO.createdAt) && Objects.equals(updatedAt, projectPO.updatedAt) && Objects.equals(percentageOfTimeRemaining, projectPO.percentageOfTimeRemaining) && Objects.equals(secretariatId, projectPO.secretariatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, uuid, expectedStart, expectedThermal, startActual, thermalActual, daysLate, createdAt, updatedAt, percentageOfTimeRemaining, secretariatId);
    }

    @Override
    public String toString() {
        return "ProjectPO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", uuid=" + uuid +
                ", expectedStart=" + expectedStart +
                ", expectedThermal=" + expectedThermal +
                ", startActual=" + startActual +
                ", thermalActual=" + thermalActual +
                ", daysLate=" + daysLate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", percentageOfTimeRemaining=" + percentageOfTimeRemaining +
                ", secretariatId=" + secretariatId +
                '}';
    }
}
