package test.unit;
import junit;

public class DomainClassExempleTest {
	@TestSimulation
	void shouldInsertUser() {
	    TestDao dao = new TestDaoImpl(testEntityManager);

	    User user = new User("bite");
	    dao.save(user);

	    assertNotNull(dao.findByUsername("bite"));
	}

}
