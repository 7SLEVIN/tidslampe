package utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestQueryBuilder {

	@Test
	public void testSelectAll() {
		String q1 = Query.selectAllFrom("foo").end();
		assertEquals("Simple select", "SELECT * FROM foo", q1);
	}
	
	@Test
	public void testSelectFrom() {
		String q1 = Query.select("test_func()").end();
		assertEquals("Simple select", "SELECT test_func()", q1);

		String q2 = Query.select("id").from("foo").end();
		assertEquals("Simple select", "SELECT id FROM foo", q2);

		String q3 = Query.select("id, name, age").from("foo").end();
		assertEquals("Simple select", "SELECT id, name, age FROM foo", q3);
	}
	
	@Test
	public void testUpdate() {
		String q1 = Query.update("foo", new String[]{"name"}, new String[]{"Karlsson"}).end();
		assertEquals("Update single field", "UPDATE foo SET name='Karlsson'", q1);

		String q2 = Query.update("foo", new String[]{"name"}, new String[]{"Karlsson"}).whereEquals("id", 1).end();
		assertEquals("Update where", "UPDATE foo SET name='Karlsson' WHERE id = 1", q2);

		String q3 = Query.update("foo", new String[]{"name"}, new String[]{"Karlsson"}).whereEquals("name", "foo").end();
		assertEquals("Update where", "UPDATE foo SET name='Karlsson' WHERE name = 'foo'", q3);
		
		String q4 = Query.update("foo", new String[]{"name", "age"}, new String[]{"Karlsson", "37"}).end();
		assertEquals("Update multiple fields", "UPDATE foo SET name='Karlsson',age='37'", q4);
	}
	
	@Test
	public void testDelete() {
		String q1 = Query.deleteFrom("foo").end();
		assertEquals("Update single field", "DELETE FROM foo", q1);

		String q2 = Query.deleteFrom("foo").whereEquals("id", 1).end();
		assertEquals("Update single field", "DELETE FROM foo WHERE id = 1", q2);
	}
	
	@Test
	public void testWhere() {
		String q1 = Query.selectAllFrom("foo").whereEquals("id", 1).end();
		assertEquals("Where equals", "SELECT * FROM foo WHERE id = 1", q1);

		String q2 = Query.selectAllFrom("foo").whereEquals("name", "bar").end();
		assertEquals("Where equals", "SELECT * FROM foo WHERE name = 'bar'", q2);

		String q3 = Query.selectAllFrom("foo").whereEquals("name", "bar").whereEquals("id", 1).end();
		assertEquals("Where equals", "SELECT * FROM foo WHERE name = 'bar' AND id = 1", q3);

		String q4 = Query.selectAllFrom("foo").whereLessOrEquals("name", "bar").end();
		assertEquals("Where less", "SELECT * FROM foo WHERE name <= 'bar'", q4);
		
		String q5 = Query.selectAllFrom("foo").whereLessOrEquals("id", 5).end();
		assertEquals("Where less", "SELECT * FROM foo WHERE id <= 5", q5);

		String q6 = Query.selectAllFrom("foo").whereGreaterOrEquals("name", "bar").end();
		assertEquals("Where less", "SELECT * FROM foo WHERE name >= 'bar'", q6);

		String q7 = Query.selectAllFrom("foo").whereGreaterOrEquals("id", 6).end();
		assertEquals("Where more", "SELECT * FROM foo WHERE id >= 6", q7);
	}
	
	@Test
	public void testWhereIn() {
		String q1 = Query.select("foo").from("bar").whereIn("id", Query.select("id").from("baz")).end();
		assertEquals("Where In", "SELECT foo FROM bar WHERE id IN (SELECT id FROM baz)", q1);
	}
	
	@Test
	public void testInsertInto() {
		String q1 = Query.insertInto("foo", new String[]{"name", "age"}, new String[]{"karl", "37"}).end();
		assertEquals("Insert into", "INSERT INTO foo (name,age) VALUES (\"karl\",\"37\")", q1);

		String q2 = Query.insertInto("foo", new String[]{"name"}, new String[]{"karl"}).end();
		assertEquals("Insert into", "INSERT INTO foo (name) VALUES (\"karl\")", q2);
		
		String q3 = Query.insertInto("foo", new String[]{}, new String[]{}).end();
		assertEquals("Insert into", "", q3);
	}
	
	@Test
	public void testOrderBy() {
		String q2 = Query.selectAllFrom("foo").whereEquals("id", 1).orderBy("name").end();
		assertEquals("Order by after where", "SELECT * FROM foo WHERE id = 1 ORDER BY name ASC", q2);
		
		String q3 = Query.selectAllFrom("foo").orderBy("name", SortDirection.Desc).end();
		assertEquals("Order by desc", "SELECT * FROM foo ORDER BY name DESC", q3);
	}

	
	@Test
	public void testLimit() {
		String q2 = Query.selectAllFrom("foo").whereEquals("id", 1).orderBy("name").limit(5).end();
		assertEquals("Order by after where", "SELECT * FROM foo WHERE id = 1 ORDER BY name ASC LIMIT 5", q2);
		
		String q3 = Query.selectAllFrom("foo").limit(1).end();
		assertEquals("Order by desc", "SELECT * FROM foo LIMIT 1", q3);
	}
	
	@Test
	public void testExists() {
		String q1 = Query.exists("foo", 1).end();
		assertEquals("Exists", "SELECT EXISTS(SELECT * FROM foo WHERE id=1)", q1);
	}
	
	@Test
	public void testCount() {
		String q1 = Query.count("foo").end();
		assertEquals("Count", "SELECT COUNT(*) FROM foo", q1);
	}
}
