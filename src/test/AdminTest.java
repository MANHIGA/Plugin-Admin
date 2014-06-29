package test;

import static org.junit.Assert.*;

import java.sql.ResultSet;

import model.Admin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAdminId() {
		int id = Admin.getAdminId("Goomoonryong", "murim");
		assertEquals(id, 1);
	}
	
	@Test
	public void testAfficherJoueurSignales(){
		try{
			ResultSet rs = Admin.afficherJoueurSignales();
			rs.first();
			Integer i = rs.getInt("idSignalement");
			assertTrue(i == 4);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testBannirJoueur(){
		boolean ban = Admin.bannirJoueur(2, "1", 6, 5);
		assertTrue(ban);
	}

}
