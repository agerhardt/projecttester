package de.age.projecttester.internal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.age.projecttester.internal.adapter.ProjectSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({ ConfigurableProjectTest.class, NameRegexFilterTest.class, ProjectTestRunnerTest.class, ProjectSuiteTest.class })
public class AllTests {

}
