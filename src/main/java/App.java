import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    get("/", (request, response) -> {
     Map<String, Object> model = new HashMap<String, Object>();
     model.put("safeAnimals", SafeAnimals.all());
     model.put("endangeredAnimals", EndangeredAnimals.all());
     model.put("template", "templates/index.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String safeAnimalName = request.queryParams("name");
      SafeAnimals newSafeAnimal = new SafeAnimals(safeAnimalName);
      newSafeAnimal.save();
      model.put("safeAnimals", SafeAnimals.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String endangeredAnimalName = request.queryParams("name");
      int endangeredAnimalHealth = request.queryParams("health");
      int endangeredAnimalAge = request.queryParams("age");
      EndangeredAnimals newEndangeredAnimal = new EndangeredAnimals(endangeredAnimalName, endangeredAnimalHealth, endangeredAnimalAge);
      newEndangeredAnimal.save();
      model.put("endangeredAnimals", EndangeredAnimals.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //lists all sightings
    get("/sightings", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("sightings", Sightings.all());
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //shows sightings information
    get("/sightings/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Sightings sighting = Sighting.find(Integer.parseInt(request.params(":id")));
      model.put("sightings", sightings);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Display sightings form
    get("/sightings/new", (request, respose) ->{
      Map<String, Object>model = new HashMap<String, Object>();
      model.put("template", "templates/sightings-form.vtl");
      Return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Create new instance of sightings when form is submitted
    Map<String, Object> model = new HashMap<String, Object>();
    String location = request.queryParams("location");
    String rangerName = request.queryParams("rangerName");
    Timestamp created = request.queryParams("created");
    Sightings newSightings = new Sightings(location, rangerName, created);
    newSightings.save();
    model.put("sightings", Sightings.all());
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
}
