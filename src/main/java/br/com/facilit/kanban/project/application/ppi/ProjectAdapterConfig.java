package br.com.facilit.kanban.project.application.ppi;

import br.com.facilit.kanban.project.application.command.*;
import br.com.facilit.kanban.project.domain.dto.ProjectDTO;
import br.com.facilit.kanban.project.domain.enums.StatusProject;
import br.com.facilit.kanban.project.mapping.ProjectMapper;
import br.com.facilit.kanban.shared.usecase.IUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Configuração responsável por instanciar o adaptador para a porta {@link ProjectPort},
 * realizando a conexão das requisições vindas do Resource ao caso de uso correspondente.
 *
 * <p>Implementa o padrão da Arquitetura Hexagonal, atuando como o Adapter que
 * converte {@link ProjectDTO.Request} para comandos da aplicação e mapeia
 * as respostas para {@link ProjectDTO.Response}.</p>
 *
 * @author Antonio Neto
 */
@Lazy
@Configuration
public class ProjectAdapterConfig {

    /**
     * Cria o bean {@link ProjectPort} conectando os casos de uso necessários
     * ao fluxo de CRUD e alteração de status de projetos Kanban.
     *
     * @param createProject caso de uso responsável pela criação de um novo projeto
     * @param listProject caso de uso responsável por listar projetos paginados
     * @param findProject caso de uso responsável pela consulta de projeto por UUID
     * @param updateProject caso de uso responsável pela atualização de dados de um projeto
     * @param changeStatusProject caso de uso responsável pela troca de status do projeto
     * @return implementação concreta de {@link ProjectPort}
     */
    @Bean("projectAdpter")
    public ProjectPort projectAdpter(@Qualifier("createProjectUseCase")
                                     IUseCase<CreateProjectCommand.Input, Mono<CreateProjectCommand.Output>> createProject,
                                     @Qualifier("listProjectUseCase")
                                     IUseCase<ListProjectCommand.Input, Mono<Page<ListProjectCommand.Output>>> listProject,
                                     @Qualifier("findProjectUseCase")
                                     IUseCase<FindProjectCommand.Input, Mono<FindProjectCommand.Output>> findProject,
                                     @Qualifier("updateProjectUseCase")
                                     IUseCase<UpdateProjectCommand.Input, Mono<UpdateProjectCommand.Output>> updateProject,
                                     @Qualifier("changeStatusProjectUseCase")
                                     IUseCase<ChangeStatusProjectCommand.Input, Mono<ChangeStatusProjectCommand.Output>> changeStatusProject) {

        return new ProjectPort() {

            /**
             * {@inheritDoc}
             */
            @Override
            public Mono<ProjectDTO.Response> create(ProjectDTO.Request request) {
                CreateProjectCommand.Input input = ProjectMapper.Create.REQUEST_TO_INPUT.apply(request);
                return createProject.execute(input).map(ProjectMapper.Create.OUTPUT_TO_RESPONSE);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Mono<Page<ProjectDTO.Response>> list(Pageable pageable) {
                ListProjectCommand.Input input = ProjectMapper.List.REQUEST_TO_INPUT.apply(pageable);
                return listProject.execute(input).map(ProjectMapper.List.PAGE_OUTPUT_TO_PAGE_RESPONSE);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Mono<ProjectDTO.Response> find(UUID id) {
                FindProjectCommand.Input input = ProjectMapper.Find.REQUEST_TO_INPUT.apply(id);
                return findProject.execute(input).map(ProjectMapper.Find.OUTPUT_TO_RESPONSE);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Mono<ProjectDTO.Response> update(UUID id, ProjectDTO.Request request) {
                UpdateProjectCommand.Input input = ProjectMapper.Update.REQUEST_TO_INPUT.apply(id, request);
                return updateProject.execute(input).map(ProjectMapper.Update.OUTPUT_TO_RESPONSE);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Mono<ProjectDTO.Response> changeStatus(UUID id, StatusProject status) {
                ChangeStatusProjectCommand.Input input = ProjectMapper.ChangeStatus.REQUEST_TO_INPUT.apply(id, status);
                return changeStatusProject.execute(input).map(ProjectMapper.ChangeStatus.OUTPUT_TO_RESPONSE);
            }
        };
    }
}
