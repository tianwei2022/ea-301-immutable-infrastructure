package org.example.looam.web.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(
    packages = "org.example.looam.web",
    importOptions = ImportOption.DoNotIncludeTests.class)
public class CyclicDependencyRulesTest {
  @ArchTest
  static final ArchRule no_cycles_by_method_calls_between_slices =
      slices()
          .matching("..(org.example.looam.web).(*)..")
          .namingSlices("$2 of $1")
          .should()
          .beFreeOfCycles();
}
