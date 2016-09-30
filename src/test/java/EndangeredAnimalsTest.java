import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class EndangeredAnimalsTest {
  EndangeredAnimals endangeredAnimal;
  @Before
  public void setUp() {
     DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", null, null);
     endangeredAnimal = new EndangeredAnimals ("fox", 3, 1);
  }

  @After
  public void tearDown() {
   try(Connection con = DB.sql2o.open()) {
     String deleteSightingsQuery = "DELETE FROM sightings *;";
     String deleteAnimalsQuery = "DELETE FROM animals *;";
     con.createQuery(deleteSightingsQuery).executeUpdate();
     con.createQuery(deleteAnimalsQuery).executeUpdate();
    }
  }

  @Test
  public void endangeredAnimal_instantiatesCorrectly_true() {
    assertTrue(endangeredAnimal instanceof EndangeredAnimals);
  }

  @Test
  public void getName_instantiatesWithName_String() {
    assertEquals("fox", endangeredAnimal.getName());
  }

  @Test
  public void getHealth_instantiatesWithHealth_int() {
    assertEquals(3, endangeredAnimal.getHealth());
  }

  @Test
  public void getAge_instantiatesWithAge_String() {
    assertEquals(1, endangeredAnimal.getAge());
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
   EndangeredAnimals endangeredAnimalTwo = new EndangeredAnimals("eagle", 2, 2);
   endangeredAnimalTwo.save();
   assertEquals(true, EndangeredAnimals.all().get(0).equals(endangeredAnimal));
   assertEquals(true, EndangeredAnimals.all().get(1).equals(endangeredAnimalTwo));
 }

 @Test
 public void equals_recognizesSameValues_true () {
   EndangeredAnimals endangeredAnimal = new EndangeredAnimals("eagle", 2, 2);
   endangeredAnimal.save();
   EndangeredAnimals savedEndangeredAnimal = EndangeredAnimals.find(endangeredAnimal.getId());
   assertEquals(true, endangeredAnimal.equals(savedEndangeredAnimal));
 }
}
