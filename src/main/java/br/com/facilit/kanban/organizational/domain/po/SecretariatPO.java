package br.com.facilit.kanban.organizational.domain.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Persistence Object (PO) responsável pela representação da entidade Secretaria
 * no banco de dados relacional.
 *
 * <p>Utilizado pela camada de persistência dentro da Arquitetura Hexagonal,
 * mantendo o mapeamento direto para a tabela <b>secretariat</b>.</p>
 *
 * <p>Contém meta-informações importantes como datas de criação e atualização,
 * além do identificador universal {@link UUID} para integração com diferentes serviços.</p>
 */
@Table("secretariat")
public class SecretariatPO implements Serializable {

    /** Identificador único interno da secretaria (chave primária). */
    @Id
    private Long id;

    /** Identificador universal da secretaria para comunicação externa e segura. */
    @Column("uuid")
    private UUID uuid;

    /** Nome oficial da secretaria. */
    @Column("name")
    private String name;

    /** Descrição funcional ou administrativa da secretaria. */
    @Column("description")
    private String description;

    /** Data e hora de criação do registro no sistema. */
    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    /** Data e hora da última atualização do registro. */
    @Column("updated_at")
    private LocalDateTime updatedAt;

    public SecretariatPO() {
    }

    public SecretariatPO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SecretariatPO(UUID uuid, String name, String description) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
    }

    /** @return chave primária da secretaria */
    public Long getId() {
        return id;
    }

    /** @param id define a chave primária da secretaria */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return identificador universal da secretaria */
    public UUID getUuid() {
        return uuid;
    }

    /** @param uuid define o identificador universal da secretaria */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /** @return name da secretaria */
    public String getName() {
        return name;
    }

    /** @param name define o nome da secretaria */
    public void setName(String name) {
        this.name = name;
    }

    /** @return descrição da secretaria */
    public String getDescription() {
        return description;
    }

    /** @param description define a descrição da secretaria */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return data e hora de criação do registro */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** @param createdAt define a data e hora de criação do registro */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /** @return data e hora da última atualização do registro */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /** @param updatedAt define a data e hora da última atualização do registro */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        SecretariatPO that = (SecretariatPO) object;
        return Objects.equals(id, that.id)
                && Objects.equals(uuid, that.uuid)
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, name, description, createdAt, updatedAt);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "SecretariatPO{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}