package br.com.facilit.kanban.project.mapping;

import br.com.facilit.kanban.project.application.command.*;
import br.com.facilit.kanban.project.domain.dto.ProjectDTO;
import br.com.facilit.kanban.project.domain.enums.StatusProject;
import br.com.facilit.kanban.project.domain.po.ProjectPO;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Classe utilitária para mapeamento entre DTOs do domínio e Commands da aplicação.
 * Segue o padrão funcional, fornecendo {@link Function}s prontas para uso em streams ou fluxos reativos.
 *
 * @author Antonio Neto
 */
public final class ProjectMapper {

    private ProjectMapper() {}

    /**
     * Mapeamentos relacionados à criação de projetos.
     */
    public static final class Create {

        private Create() {}

        /**
         * Função para converter um {@link ProjectDTO.Request} em um
         * {@link CreateProjectCommand.Input}.
         *
         * <p>Útil para transformar os dados recebidos pela API em comando de criação
         * para a camada de aplicação.</p>
         *
         * <p>Uso em Stream:</p>
         * <pre>
         * ProjectDTO.Request request = ...;
         * CreateProjectCommand.Input input = Create.REQUEST_TO_INPUT.apply(request);
         * </pre>
         */
        public static final Function<ProjectDTO.Request, CreateProjectCommand.Input> REQUEST_TO_INPUT =
                request -> (request == null) ? null : new CreateProjectCommand.Input(
                        request.name(),
                        request.status(),
                        null,
                        request.expectedStart(),
                        request.expectedEnd(),
                        request.startActual(),
                        request.endActual(),
                        request.daysLate(),
                        request.percentageOfTimeRemaining(),
                        request.secretariatId()
                );

        /**
         * Função para converter um {@link CreateProjectCommand.Output} em um
         * {@link ProjectDTO.Response}.
         *
         * <p>Útil para transformar o resultado de um comando de criação em DTO
         * de saída para a API.</p>
         *
         * <p>Uso em Stream:</p>
         * <pre>
         * CreateProjectCommand.Output output = ...;
         * ProjectDTO.Response response = Create.OUTPUT_TO_RESPONSE.apply(output);
         * </pre>
         */
        public static final Function<CreateProjectCommand.Output, ProjectDTO.Response> OUTPUT_TO_RESPONSE =
                output -> (output == null) ? null : new ProjectDTO.Response(
                        output.uuid(),
                        output.name(),
                        output.status(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

        public static final Function<CreateProjectCommand.Input, ProjectPO> INPUT_TO_PO =
                input -> (input == null) ? null : new ProjectPO(
                        input.name(),
                        input.status(),
                        input.expectedStart(),
                        input.expectedEnd(),
                        input.startActual(),
                        input.endActual(),
                        input.daysLate(),
                        input.percentageOfTimeRemaining(),
                        input.secretariatId()
                );

        public static final Function<ProjectPO, CreateProjectCommand.Output> PO_TO_OUTPUT =
                po -> (po == null) ? null : new CreateProjectCommand.Output(
                        po.getName(),
                        po.getStatus(),
                        po.getUuid()
                );

    }

    public static final class List{

        private List(){}

        public static final Function<ListProjectCommand.Input, Pageable> INPUT_TO_PO =
                input -> (input == null) ? null : PageRequest.of(input.pageNumber(), input.pageSize(), input.sort());

        public static final Function<Pageable, ListProjectCommand.Input> REQUEST_TO_INPUT =
                request -> {
                    if (request == null) return null;
                    return new ListProjectCommand.Input(
                            request.getPageNumber(),
                            request.getPageSize(),
                            request.getSort() != null ? request.getSort() :
                                    Sort.by("name").ascending().and(Sort.by("createdAt").descending())
                    );
                };

        public static final Function<Page<ProjectPO>, Page<ListProjectCommand.Output>> PAGE_PO_TO_PAGE_OUTPUT =
                pagePO -> {
                    @SuppressWarnings("unchecked")
                    java.util.List<ListProjectCommand.Output> output = pagePO.getContent()
                            .stream()
                            .map(po ->{
                                        return new ListProjectCommand.Output(po.getName(), po.getStatus(), po.getUuid(),
                                                po.getExpectedStart(), po.getExpectedThermal(), po.getStartActual(), po.getThermalActual(),
                                                po.getDaysLate(), po.getPercentageOfTimeRemaining());
                                    }
                            ).toList();

                    return new PageImpl<>(
                            output,
                            Pageable.unpaged(), // já está paginado no output, então não usamos o pageable real
                            pagePO.getTotalElements()
                    );
                };

        public static final Function<Page<ListProjectCommand.Output>, Page<ProjectDTO.Response>> PAGE_OUTPUT_TO_PAGE_RESPONSE =
                output -> {
                    @SuppressWarnings("unchecked")
                    java.util.List<ProjectDTO.Response> responses =  output.getContent()
                            .stream()
                            .map(out->{
                                        return new ProjectDTO.Response(out.uuid(), out.name(), out.status(), out.expectedStart(), out.expectedEnd(),
                                                out.startActual(), out.endActual(), out.daysLate(), out.percentageOfTimeRemaining(), null);
                                    }
                            ).toList();

                    return new PageImpl<>(
                            responses,
                            Pageable.unpaged(), // já está paginado no output, então não usamos o pageable real
                            output.getTotalElements()
                    );
                };
    }

    public static final class Find{

        private Find(){}

        public static final Function<ProjectPO, FindProjectCommand.Output> PO_TO_OUTPUT =
                po -> (po == null) ? null : new FindProjectCommand.Output(
                        po.getName(),
                        po.getStatus(),
                        po.getUuid(),
                        po.getExpectedStart(),
                        po.getExpectedThermal(),
                        po.getStartActual(),
                        po.getThermalActual(),
                        po.getDaysLate(),
                        po.getPercentageOfTimeRemaining(),
                        po.getSecretariatId()
                );

        public static final Function<UUID, FindProjectCommand.Input> REQUEST_TO_INPUT =
                uuid -> (uuid == null) ? null : new FindProjectCommand.Input(
                        uuid
                );

        public static final Function<FindProjectCommand.Output, ProjectDTO.Response> OUTPUT_TO_RESPONSE =
                output -> (output == null) ? null : new ProjectDTO.Response(
                        output.uuid(),
                        output.name(),
                        output.status(),
                        output.expectedStart(),
                        output.expectedThermal(),
                        output.startActual(),
                        output.thermalActual(),
                        output.daysLate(),
                        output.percentageOfTimeRemaining(),
                        output.secretariatId()
                );
    }

    public static final class Update{

        private Update(){}

        public static final BiFunction<UUID, ProjectDTO.Request, UpdateProjectCommand.Input> REQUEST_TO_INPUT =
                (uuid, request) -> (uuid == null) ? null : new UpdateProjectCommand.Input(
                        request.name(),
                        request.status(),
                        uuid,
                        request.expectedStart(),
                        request.expectedThermal(),
                        request.expectedEnd(),
                        request.startActual(),
                        request.endActual(),
                        request.thermalActual(),
                        request.daysLate(),
                        request.percentageOfTimeRemaining(),
                        request.secretariatId()
                );

        public static final Function<UpdateProjectCommand.Output, ProjectDTO.Response> OUTPUT_TO_RESPONSE =
                output -> (output == null) ? null : new ProjectDTO.Response(
                        output.uuid(),
                        output.name(),
                        output.status(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

        public static final Function<UpdateProjectCommand.Input, ProjectPO> INPUT_TO_PO =
                input -> (input == null) ? null : new ProjectPO(
                        input.name(),
                        input.status(),
                        input.expectedStart(),
                        input.expectedThermal(),
                        input.startActual(),
                        input.thermalActual(),
                        input.daysLate(),
                        input.percentageOfTimeRemaining(),
                        input.secretariatId()
                );

        public static final Function<ProjectPO, UpdateProjectCommand.Output> PO_TO_OUTPUT =
                po -> (po == null) ? null : new UpdateProjectCommand.Output(
                        po.getName(),
                        po.getStatus(),
                        po.getUuid()
                );


    }

    public static final class ChangeStatus{

        private ChangeStatus(){}

        public static final BiFunction<UUID, StatusProject, ChangeStatusProjectCommand.Input> REQUEST_TO_INPUT =
                (uuid, statusProject) -> (uuid == null) ? null : new ChangeStatusProjectCommand.Input(
                        uuid, statusProject
                );

        public static final Function<ChangeStatusProjectCommand.Output, ProjectDTO.Response> OUTPUT_TO_RESPONSE =
                output -> (output == null) ? null : new ProjectDTO.Response(
                        output.uuid(),
                        output.name(),
                        output.status(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

        public static final Function<ProjectPO, ChangeStatusProjectCommand.Output> PO_TO_OUTPUT =
                po -> (po == null) ? null : new ChangeStatusProjectCommand.Output(
                        po.getName(),
                        po.getStatus(),
                        po.getUuid()
                );

        public static final BiFunction<ProjectPO, ChangeStatusProjectCommand.Input, ProjectPO> INPUT_TO_PO =
                (po, input) -> (input == null) ? null : new ProjectPO(
                        po.getId(),
                        po.getName(),
                        po.getStatus(),
                        po.getExpectedStart(),
                        po.getExpectedThermal(),
                        po.getStartActual(),
                        po.getThermalActual(),
                        po.getDaysLate(),
                        po.getPercentageOfTimeRemaining(),
                        po.getSecretariatId()
                );

    }
}
