import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class SafeAnimalsTest {
  SafeAnimals safeAnimal;
  @Before
  public void setUp() {
     DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", null, null);
     safeAnimal = new SafeAnimals ("owl");
  }

  @After
  public void tearDown() {
   try(Connection con = DB.sql2o.open()) {
     String deleteSightingsQuery = "DELETE FROM sightings *;";
     String deletesAnimalsQuery = "DELETE FROM animals *;";
     con.createQuery(deleteSightingsQuery).executeUpdate();
     con.createQuery(deleteAnimalsQuery).executeUpdate();
    }
  }

  @Test
  public void safeAnimal_instantiatesCorrectly_true() {
    assertTrue(safeAnimal instanceof SafeAnimals);
  }

  @Test
  public void getName_instantiatesWithName_String() {
    assertEquals("owl", safeAnimals.getName());
  }

  @Test
  public void getId_safeAnimalInstantiateWithId_1() {
    safeAnimal.save();
    assertTrue(safeAnimal.getId()> 0);
  }

  @Test
  public void save_savesIntoDatabase_true() {
    safeAnimal.save();
    assertEquals(true, SafeAnimals.all().get(0).equals(safeAnimal));
  }

  @Test
  public void all_returnsAllSafeAnimals_true () {
   safeAnimal.save();
   SafeAnimals safeAnimalTwo = new SafeAnimal("bear");
   safeAnimalTwo.save();
   assertEquals(true, SafeAnimals.all().get(0).equals(safeAnimal));
   assertEquals(true, SafeAnimals.all().get(1).equals(safeAnimalsTwo));
 }

 @Test
 public void equals_recognizesSameValues_true () {
   SafeAnimals safeAnimal = new SafeAnimals("hawk");
   safeAnimal.save();
   SafeAnimals savedSafeAnimal = SafeAnimals.find(safeAnimal.getId());
   assertEquals(true, safeAnimal.equals(savedSafeAnimal));
 }

 @Test
 public void searchBySighting_returnsAllAnimalsWithSightings_true () {
   SafeAnimals safeAnimal = new SafeAnimals("hawk");
   safeAnimal.save();
   Sightings sighting = new Sightings("NE Quadrant", "Bob", 1);
   sighting.save();
   safeAnimal.addSighting(sighting);
   List<SafeAnimals> foundSafeAnimal = SafeAnimals.searchBySighting("NE Quadrant", "Bob", 1 );
   assertEquals(true, foundSafeAnimal.contains(safeAnimal));
 }
}
