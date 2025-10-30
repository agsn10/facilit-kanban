package br.com.facilit.kanban;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.modulith.test.ApplicationModuleTest;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;

class ModularityTests {

    public static final DescribedPredicate<JavaClass> IGNORED =
            resideInAnyPackage("br.com.facilit.kanban.shared..");

    public static final ApplicationModules modules =
            ApplicationModules.of(FacilitKanbanApplication.class, IGNORED);

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
