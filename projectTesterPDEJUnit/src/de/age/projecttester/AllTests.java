package de.age.projecttester;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.age.projecttester.internal.adapter.ProjectAdapterTest;

@RunWith(Suite.class)
@SuiteClasses( { ApiExperiments.class, ProjectAdapterTest.class })
public class AllTests {

}
