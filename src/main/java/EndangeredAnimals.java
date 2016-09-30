import org.sql2o.*;
import java.util.List;

public class EndangeredAnimals extends Animals {
  private int health;
  private int age;
  public static final String DATABASE_TYPE = "EndangeredAnimals";
  public final static int GOOD_HEALTH = 1;
  public final static int OK_HEALTH = 2;
  public final static int ILL_HEALTH = 3;
  public final static int ADULT_AGE = 3;
  public final static int YOUNG_AGE = 2;
  public final static int NEWBORN_AGE = 1;

  public EndangeredAnimals (String name, int health, int age) {
    this.name = name;
    this.health = health;
    this.age = age;
    type = DATABASE_TYPE;
  }

  public int getHealth() {
  return this.health;
  }

  public int getAge() {
  return this.age;
  }


  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name, health, age, type) VALUES (:name, :health, :age, :type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("health", this.health)
        .addParameter("age", this.age)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET name = :name, health = :health, age = :age WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("health", this.health)
        .addParameter("age", this.age)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<EndangeredAnimals> all() {
    String sql = "SELECT * FROM animals WHERE type = :type";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("type", DATABASE_TYPE)
        .executeAndFetch(EndangeredAnimals.class);
    }
  }

  public static EndangeredAnimals find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id = :id";
      EndangeredAnimals endangeredAnimal = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(EndangeredAnimals.class);
      return endangeredAnimal;
    }
  }

  public static List<EndangeredAnimals> searchBySighting(String search) {
    String sql = "SELECT animals.* FROM animals LEFT JOIN sightings_animals ON animals.Id = sightings_animals.animals_Id WHERE animals.type = :type AND sightings.location ~* :search";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("search", ".*" + search + ".*")
        .addParameter("type", DATABASE_TYPE)
        .executeAndFetch(EndangeredAnimals.class);
    }
  }
}
