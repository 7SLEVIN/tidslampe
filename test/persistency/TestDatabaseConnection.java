package persistency;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDatabaseConnection {
	private DatabaseConnection dbCon;

	@Before
	public void setUp() throws Exception {
		this.dbCon = new DatabaseConnection("test_db.db");
		this.dbCon.execUpdate("drop table if exists foo");
		this.dbCon.execUpdate("create table foo (id integer, name string)");
	}

	@Test
	public void testInsertId() {
		int id1 = this.dbCon.create("foo", new String[]{"name"}, new String[]{"Karlsson Delight"});
		assertEquals("Last insert id", 1, id1);

		int id2 = this.dbCon.create("foo", new String[]{"name"}, new String[]{"Moby Dick"});
		assertEquals("Last insert id increased", 2, id2);
	}
	
	@After 
	public void tearDown() throws SQLException {
		this.dbCon.execUpdate("drop table if exists foo");
	}
}
