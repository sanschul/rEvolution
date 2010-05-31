package edu.usp.ime.revolution.scm.changesets;

import java.text.SimpleDateFormat;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.scm.SCM;

public class ChangeSetsInPeriodFactoryTest {

	private SCM scm;
	private Config config;

	@Before
	public void SetUp() {
		scm = mock(SCM.class);
		config = mock(Config.class);
	}
	
	@Test(expected=ChangeSetNotFoundException.class)
	public void ShouldThrownAnExceptionIfConfigIsNotFound() {
		when(config.get("changesets.all.startPeriod")).thenReturn("no-date-here");
		when(config.get("changesets.all.endPeriod")).thenReturn("10/10/2009");
		
		ChangeSetsInPeriodFactory factory = new ChangeSetsInPeriodFactory();
		factory.build(scm, config);
	}
	
	@Test
	public void ShouldSetStartAndEndPeriod() {		
		when(config.get("changesets.all.startPeriod")).thenReturn("10/10/2008");
		when(config.get("changesets.all.endPeriod")).thenReturn("10/10/2009");
		
		ChangeSetsInPeriodFactory factory = new ChangeSetsInPeriodFactory();
		ChangeSetsInPeriod cs = (ChangeSetsInPeriod) factory.build(scm, config);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		assertEquals("10/10/2008", sdf.format(cs.getStartPeriod().getTime()));
		assertEquals("10/10/2009", sdf.format(cs.getEndPeriod().getTime()));
	}
}
