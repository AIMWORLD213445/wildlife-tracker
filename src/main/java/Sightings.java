import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

public class Sightings {
  int id;
  String location;
  String rangerName;
  Timestamp created;
  int animalId;

  public Sightings(String location, String rangerName, int animalId) {
    this.location = location;
    this.rangerName = rangerName;
    this.animalId = animalId;
  }

  public String getLocation() {
    return location;
  }

  public String getRangerName() {
    return rangerName;
  }

  public Timestamp getCreated() {
    return created;
  }

  public int getAnimalId() {
    return animalId;
  }

  public int getId() {
  return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (location, rangerName, created, animalId) VALUES (:location, :rangerName, now(), :animalId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("location", this.location)
        .addParameter("rangerName", this.rangerName)
        .addParameter("animalId", this.animalId)
        .executeUpdate()
        .getKey();
    }
  }

  public void update(String location, String rangerName, int animalId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE sightings SET location = :location, rangerName = :rangerName, animalId = :animalId WHERE id = :id";
      con.createQuery(sql)
        .addParameter("location", location)
        .addParameter("rangerName", rangerName)
        .addParameter("animalId", animalId)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public static List<Sightings> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings";
      return con.createQuery(sql)
      .throwOnMappingFailure(false)
      .executeAndFetch(Sightings.class);
    }
  }

  public static Sightings find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Sightings WHERE id = :id";
      Sightings sighting = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Sightings.class);
      return sighting;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM sightings WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  @Override
   public boolean equals(Object otherSighting) {
     if (!(otherSighting instanceof Sightings)) {
       return false;
     } else {
      Sightings newSighting = (Sightings) otherSighting;
       return this.getId() == newSighting.getId() &&
              this.getLocation().equals(newSighting.getLocation()) &&
              this.getRangerName().equals(newSighting.getRangerName()) &&
              this.getAnimalId() == newSighting.getAnimalId();
    }
  }
}
