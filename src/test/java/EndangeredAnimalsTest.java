import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class EndangeredAnimalsTest {
  EndangeredAnimals endangeredAnimal;
  @Before
  public void setUp() {
     DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", null, null);
     endangeredAnimal = new EndangeredAnimals ("fox", 1, 3);
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
  public void endangeredAnimal_instantiatesCorrectly_true() {
    assertTrue(endangeredAnimal instanceof EndageredAnimals);
  }

  @Test
  public void getName_instantiatesWithName_String() {
    assertEquals("fox", endangeredAnimals.getName());
  }

  @Test
  public void getHealth_instantiatesWithHealth_int() {
    assertEquals("1", endangeredAnimals.getHealth());
  }

  @Test
  public void getAge_instantiatesWithAge_String() {
    assertEquals("3", endangeredAnimals.getAge());
  }

  @Test
  public void getId_endangeredAnimalInstantiateWithId_1() {
    endangeredAnimal.save();
    assertTrue(endangeredAnimal.getId()> 0);
  }

  @Test
  public void save_savesIntoDatabase_true() {
    endangeredAnimal.save();
    assertEquals(true, EndangeredAnimals.all().get(0).equals(endangeredAnimal));
  }

  @Test
  public void all_returnsAllEndangeredAnimals_true () {
   endangeredAnimal.save();
   EndangeredAnimals endangeredAnimalTwo = new EndangeredAnimal("eagle", 2, 2);
   endangeredAnimalTwo.save();
   assertEquals(true, EndangeredAnimals.all().get(0).equals(endangeredAnimal));
   assertEquals(true, EndangeredAnimals.all().get(1).equals(endangeredAnimalsTwo));
 }

 @Test
 public void equals_recognizesSameValues_true () {
   EndangeredAnimals endangeredAnimal = new EndangeredAnimals("eagle", 2, 2);
   endangeredAnimal.save();
   EndangeredAnimals savedEndangeredAnimal = EndangeredAnimals.find(endangeredAnimal.getId());
   assertEquals(true, endangeredAnimal.equals(savedEndangeredAnimal));
 }

 @Test
 public void searchBySighting_returnsAllAnimalsWithSightings_true () {
   EndangeredAnimals endangeredAnimal = new EndangeredAnimals("eagle", 2, 2);
   endangeredAnimal.save();
   Sightings sighting = new Sightings("NE Quadrant", "Bob", 1);
   sighting.save();
   endangeredAnimal.addSighting(sighting);
   List<EndangeredAnimals> foundEndangeredAnimal = EndangeredAnimals.searchBySighting("NE Quadrant", "Bob", 1 );
   assertEquals(true, foundEndangeredAnimal.contains(endangeredAnimal));
 }
}
