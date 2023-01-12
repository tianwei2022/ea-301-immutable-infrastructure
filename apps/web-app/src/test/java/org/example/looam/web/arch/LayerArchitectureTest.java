package org.example.looam.web.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(
    packages = "org.example.looam.web",
    importOptions = ImportOption.DoNotIncludeTests.class)
public class LayerArchitectureTest {
  @ArchTest
  static final ArchRule layer_dependencies_are_respected =
      layeredArchitecture()
          .consideringAllDependencies()
          .layer("Controller")
          .definedBy("org.example.looam.web.controller..")
          .layer("Service")
          .definedBy("org.example.looam.web.service..")
          .layer("Client")
          .definedBy("org.example.looam.web.client..")
          .whereLayer("Controller")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("Service")
          .mayOnlyBeAccessedByLayers("Controller")
          .whereLayer("Client")
          .mayOnlyBeAccessedByLayers("Controller", "Service");
}
