import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public abstract class Animals {
  public int id;
  public String type;
  public String name;

  public String getName() {
    return name;
  }

  public int getId() {
    return this.id
  }

  public void delete() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "DELETE FROM animals WHERE id = :id";
     con.createQuery(sql)
       .addParameter("id", this.id)
       .executeUpdate();
    }
  }

  public void addSighting(Sightings sighting) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "INSERT INTO sightings_animals (animals_id, sightings_id) VALUES (:animals_id, :sightings_id)";
     con.createQuery(sql)
       .addParameter("animals_id", this.getId())
       .addParameter("sightings_id", sighting.getId())
       .executeUpdate();
    }
  }

  public void removeSighting(Sightings sighting){
   try(Connection con = DB.sql2o.open()){
     String joinRemovalQuery = "DELETE FROM sightings_animals WHERE sightings_id = :sightingId AND animals_id = :animalsId;";
     con.createQuery(joinRemovalQuery)
       .addParameter("sightingId", sighting.getId())
       .addParameter("animalsId", this.getId())
       .executeUpdate();
    }
  }

  public List<Sightings> getAllSightings() {
   try(Connection con = DB.sql2o.open()){
     String sql = "SELECT sightings.* FROM tag LEFT JOIN sightings_animals ON sightings_animals.sightings_id = sightings.id WHERE sightings_animals.animals_id = :id;";
     return con.createQuery(sql)
       .addParameter("id", this.getId())
       .executeAndFetch(Sightings.class);
    }
  }

  @Override
  public boolean equals(Object otherAnimals) {
    if (!(otherAnimals instanceof Animals)) {
      return false;
    } else {
      Animals animal = (Animals) otherAnimals;
      return this.getName().equals(animal.getName());
    }
  }
}
