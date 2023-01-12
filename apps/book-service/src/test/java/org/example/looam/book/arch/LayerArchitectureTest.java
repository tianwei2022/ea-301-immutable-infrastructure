package org.example.looam.book.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(
    packages = "org.example.looam.book",
    importOptions = ImportOption.DoNotIncludeTests.class)
public class LayerArchitectureTest {
  @ArchTest
  static final ArchRule layer_dependencies_are_respected =
      layeredArchitecture()
          .consideringAllDependencies()
          .layer("Inbound")
          .definedBy("org.example.looam.book.inbound..")
          .layer("AppService")
          .definedBy("org.example.looam.book.appservice..")
          .layer("Domain")
          .definedBy("org.example.looam.book.domain..")
          .layer("Outbound")
          .definedBy("org.example.looam.book.outbound..")
          .whereLayer("Inbound")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("Outbound")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("AppService")
          .mayOnlyBeAccessedByLayers("Inbound", "Outbound")
          .whereLayer("Domain")
          .mayOnlyBeAccessedByLayers("AppService", "Outbound", "Inbound");
}
