import org.sql2o.*;
import java.util.List;

public class SafeAnimals extends Animals{
public static final String DATABASE_TYPE = "SafeAnimals";

  public SafeAnimals (String name){
    this.name = name;
    type = DATABASE_TYPE;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name, type) VALUES (:name, :type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<SafeAnimals> all() {
    String sql = "SELECT * FROM animals WHERE type = :type";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("type", DATABASE_TYPE)
        .executeAndFetch(SafeAnimals.class);
    }
  }

  public static SafeAnimals find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id = :id";
      SafeAnimals safeAnimal = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(SafeAnimals.class);
      return safeAnimal;
    }
  }

  public static List<SafeAnimals> searchBySightings(String search) {
    String sql = "SELECT animals.* FROM animals " +
      "LEFT JOIN sightings_animals ON animals.Id = sightings_animals.animals_Id " +
      "LEFT JOIN sightings ON sightings_animals.sightings_id = sightings.id " +
      "WHERE animals.type = :type AND sightings.location ~* :search";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("search", ".*" + search + ".*")
        .addParameter("type", DATABASE_TYPE)
        .executeAndFetch(SafeAnimals.class);
    }
  }
}
