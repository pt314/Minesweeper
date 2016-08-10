package pt314.just4fun.minesweeper.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRemoveMine() {

		MineField field = new MineField(1, 4);
		field.getCell(0, 2).setMined(true);
		field.getCell(0, 3).setMined(true);

		Game game = new Game(field);
		
		assertFalse(field.isCleared(0, 0));
		assertFalse(field.isCleared(0, 1));
		assertFalse(field.isCleared(0, 2));
		assertFalse(field.isCleared(0, 3));
		
		game.removeMine(0, 2);
		
		assertTrue(field.isCleared(0, 0));
		assertTrue(field.isCleared(0, 1));
		assertTrue(field.isCleared(0, 2));
		assertFalse(field.isCleared(0, 3));
	}

}
