package br.com.facilit.kanban.people.domain.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa uma pessoa responsável por tarefas dentro do sistema Kanban.
 *
 * <p>Esta entidade é mapeada para a tabela {@code accountable} e
 * contém informações básicas sobre um responsável como nome, e-mail,
 * papel no projeto e um identificador universal (UUID).</p>
 *
 * @author Antonio Neto
 */
@Table("accountable")
public class AccountablePO implements Serializable {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("email")
    private String email;

    @Column("role")
    private String role;

    @Column("uuid")
    private UUID uuid;

    @Column("secretariat_id")
    private Long secretariatId;

    public AccountablePO() {
    }

    public AccountablePO(String name, String email, String role, Long secretariatId) {
        this.name = name;
        this.secretariatId = secretariatId;
        this.role = role;
        this.email = email;
    }

    /**
     * Obtém o identificador da entidade.
     *
     * @return o ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o identificador da entidade.
     *
     * @param id o ID a ser definido
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o nome do responsável.
     *
     * @return o nome
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do responsável.
     *
     * @param name o nome a ser definido
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém o e-mail do responsável.
     *
     * @return o e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail do responsável.
     *
     * @param email o e-mail a ser definido
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém o papel ou função do responsável dentro do projeto.
     *
     * @return o papel
     */
    public String getRole() {
        return role;
    }

    /**
     * Define o papel ou função do responsável.
     *
     * @param role o papel a ser definido
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Obtém o UUID associado ao responsável.
     *
     * @return o UUID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Define o UUID do responsável.
     *
     * @param uuid o UUID a ser definido
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
        AccountablePO that = (AccountablePO) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(role, that.role) && Objects.equals(uuid, that.uuid) && Objects.equals(secretariatId, that.secretariatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, role, uuid, secretariatId);
    }

    @Override
    public String toString() {
        return "AccountablePO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", uuid=" + uuid +
                ", secretariatId=" + secretariatId +
                '}';
    }
}
