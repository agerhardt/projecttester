package de.age.projecttester.internal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConfigurableProjectTest.class, NameRegexFilterTest.class, ProjectTestRunnerTest.class })
public class AllTests {

}
