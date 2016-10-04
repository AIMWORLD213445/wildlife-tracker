import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    get("/", (request, response) -> {
     Map<String, Object> model = new HashMap<String, Object>();
     model.put("template", "templates/index.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

    get("/animals", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("safeAnimals", SafeAnimals.all());
      model.put("endangeredAnimals", EndangeredAnimals.all());
      model.put("template", "templates/animals.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String endangeredAnimalName = request.queryParams("name");
      int endangeredAnimalHealth = Integer.parseInt(request.queryParams("health"));
      int endangeredAnimalAge = Integer.parseInt (request.queryParams("age"));
      EndangeredAnimals newEndangeredAnimal = new EndangeredAnimals(endangeredAnimalName, endangeredAnimalHealth, endangeredAnimalAge);
      newEndangeredAnimal.save();
      String safeAnimalName = request.queryParams("name");
      SafeAnimals newSafeAnimal = new SafeAnimals(safeAnimalName);
      newSafeAnimal.save();
      model.put("endangeredAnimals", EndangeredAnimals.all());
      model.put("safeAnimals", SafeAnimals.all());
      model.put("template", "templates/animals.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings", (request, response) -> {
     Map<String, Object> model = new HashMap<String, Object>();
     model.put("Sightings", Sightings.all());
     model.put("template", "templates/sightings.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

//if else statement to do all animals in one page?

   post("/sightings/endangered", (request, response) -> {
     Map<String, Object> model = new HashMap<String, Object>();
     EndangeredAnimals animals = EndangeredAnimals.find(Integer.parseInt(request.params("id")));
     String sightingsLocation = request.queryParams("location");
     String rangerName = request.queryParams("name");
     model.put("animals", animals.getAllSightings());
     model.put("template", "templates/sightings.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

   post("/sightings/safe", (request, response) -> {
     Map<String, Object> model = new HashMap<String, Object>();
     SafeAnimals animals = SafeAnimals.find(Integer.parseInt(request.params("id")));
     String sightingsLocation = request.queryParams("location");
     String rangerName = request.queryParams("name");
     model.put("animals", animals.getAllSightings());
     model.put("template", "templates/sightings.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());
 }

  //   //lists all sightings
  //   get("/sightings", (request, response) -> {
  //     Map<String, Object> model = new HashMap<String, Object>();
  //     model.put("sightings", Sightings.all());
  //     model.put("template", "templates/clients.vtl");
  //     return new ModelAndView(model, layout);
  //   }, new VelocityTemplateEngine());
  //
  //   //Display sightings form
  //   get("/sightings/new", (request, respose) ->{
  //     Map<String, Object>model = new HashMap<String, Object>();
  //     model.put("template", "templates/sightings-form.vtl");
  //     return new ModelAndView(model, layout);
  //   }, new VelocityTemplateEngine());
  //
  //   //Create new instance of sightings when form is submitted
  //   post("/sightings/animalsId", (request, response) -> {
  //     Map<String, Object> model = new HashMap<String, Object>();
  //     Animals newAnimals = EndangeredAnimals.find(Integer.parseInt(request.params("animalsId")));
  //     Animals newAnimals2 = SafeAnimals.find(Integer.parseInt(request.params("animalsId")));
  //     String location = request.queryParams("location");
  //     String rangerName = request.queryParams("rangerName");
  //     int created = Integer.parseInt(request.queryParams("created"));
  //     Sightings newSightings = new Sightings(location, rangerName, created);
  //     newSightings.save();
  //     String url = String.format("/animal/%d, newSightings.getAnimalId");
  //     model.put("sightings", Sightings.all());
  //     model.put("template", "templates/index.vtl");
  //     return new ModelAndView(model, layout);
  //   }, new VelocityTemplateEngine());
  // }
}
