package br.com.facilit.kanban.project.domain.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Objects;

@Table("project_accountables")
public class ProjectAccountablesPO implements Serializable {

    @Id
    private Long id;
    @Column("project_id")
    private Long projectId;
    @Column("accountable_id")
    private Long accountableId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAccountableId() {
        return accountableId;
    }
    public void setAccountableId(Long accountableId) {
        this.accountableId = accountableId;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ProjectAccountablesPO that = (ProjectAccountablesPO) object;
        return Objects.equals(id, that.id) && Objects.equals(projectId, that.projectId) && Objects.equals(accountableId, that.accountableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, accountableId);
    }

    @Override
    public String toString() {
        return "ProjectAccountablesPO{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", accountableId=" + accountableId +
                '}';
    }
}
