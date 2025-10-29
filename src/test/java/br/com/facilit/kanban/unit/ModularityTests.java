package br.com.facilit.kanban.unit;

import br.com.facilit.kanban.FacilitKanbanApplication;
import com.tngtech.archunit.base.DescribedPredicate;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.modulith.test.ApplicationModuleTest;

import com.tngtech.archunit.core.domain.JavaClass;
import org.springframework.test.context.ContextConfiguration;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;

@ApplicationModuleTest
@ContextConfiguration(classes = ModularityTests.TestConfig.class)
public class ModularityTests {

    public static final DescribedPredicate<JavaClass> IGNORED =
            resideInAnyPackage( "br.com.facilit.kanban.shared..");
    public static final ApplicationModules modules =
            ApplicationModules.of(br.com.facilit.kanban.FacilitKanbanApplication.class, IGNORED);

    @Configuration
    @Import(FacilitKanbanApplication.class)
    static class TestConfig {}

    @Test
    void encapsulated_and_withoutCycles() {
        modules.verify();
    }

    @Test
    void generateDiagrams() {
        new Documenter(modules)
                .writeModuleCanvases()
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }

    @Test
    void generateAsciidoc() {
        var canvasOptions = Documenter.CanvasOptions.defaults();

        var docOptions = Documenter.DiagramOptions.defaults()
                .withStyle(Documenter.DiagramOptions.DiagramStyle.UML);

        new Documenter(modules)
                .writeDocumentation(docOptions, canvasOptions);
    }
}
