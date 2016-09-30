import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

public class SightingsTest {
  Sightings sighting;
  @Before
  public void setUp() {
     DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", null, null);
     sighting = new Sightings ("NE Quadrant", "Bob", 1 );
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
  public void sighting_instantiatesCorrectly_true() {
    assertTrue(sighting instanceof Sightings);
  }

  @Test
  public void getLocation_instantiatesWithLocation_String() {
    assertEquals("NE Quadrant", sighting.getLocation());
  }

  @Test
  public void getRangerName_instantiatesWithRangerName_String() {
    assertEquals("Bob", sighting.getRangerName());
  }

  @Test
  public void getAnimalsId_instantiatesWithAnimalsId_int() {
    assertEquals(1, sighting.getAnimalId());
  }

  @Test
  public void getId_sightingInstantiateWithId_1() {
    sighting.save();
    assertTrue(sighting.getId()> 0);
  }

  @Test
  public void all_returnsAllSightingsInstances_true() {
    Sightings sighting = new Sightings("NE Quadrant", "Bob", 1);
    sighting.save();
    Sightings secondSighting = new Sightings("SW Quadrant", "Sarah", 2);
    secondSighting.save();
    assertEquals(true, Sightings.all().get(0).equals(sighting));
    assertEquals(true, Sightings.all().get(1).equals(secondSighting));
  }

  @Test
  public void equals_returnsTrueIfCharacteristicsAreTheSame() {
    Sightings secondSighting = new Sightings("NE Quadrant", "Bob", 1);
    assertTrue(sighting.equals(secondSighting));
}

  @Test
  public void save_returnsTrueIfFieldsAreSame_true() {
    sighting.save();
    assertEquals(true, Sightings.all().get(0).equals(sighting));
  }

  @Test
  public void save_assignsIdToObject() {
    sighting.save();
    Sightings savedSighting = Sightings.all().get(0);
    assertEquals(sighting.getId(), savedSighting.getId());
  }

  @Test
  public void save_savesAnimalsIdIntoDB_true() {
    SafeAnimals animal = new SafeAnimals("Owl");
    animal.save();
    Sightings sighting = new Sightings("NE Quadrant", "Bob", animal.getId());
    sighting.save();
    Sightings savedSighting = Sightings.find(sighting.getId());
    assertEquals(savedSighting.getAnimalId(), animal.getId());
  }

  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    Sightings sighting = new Sightings("NW Quadrant", "John", 1);
    sighting.save();
    Timestamp savedSighting= Sightings.find(sighting.getId()).getCreated();
    Timestamp now = new Timestamp(new Date().getTime());
    assertEquals(now.getDay(), savedSighting.getDay());
  }

  @Test
  public void find_returnsSightingsWithSameId_secondSighting() {
    sighting.save();
    Sightings secondSighting = new Sightings("NW Quadrant", "John", 1);
    secondSighting.save();
    assertEquals(Sightings.find(secondSighting.getId()), secondSighting);
  }

  @Test
  public void delete_deletesSighting_true() {
    Sightings sighting = new Sightings("NW Quadrant", "John", 1);
    sighting.save();
    int sightingId = sighting.getId();
    sighting.delete();
    assertEquals(null, Sightings.find(sightingId));
  }

  @Test
  public void update_UpdatesTheSighting_Void(){
    sighting.save();
    sighting.update("SW Quadrant","Jill", 2);
    assertEquals("SW Quadrant", Sightings.find(sighting.getId()).getLocation());
    assertEquals("Jill", Sightings.find(sighting.getId()).getRangerName());
    assertEquals(2 ,Sightings.find(sighting.getId()).getAnimalId());
  }
}
