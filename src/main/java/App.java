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
      EndangeredAnimals endangeredAnimal = EndangeredAnimals.find(Integer.parseInt(request.params("id")));
      SafeAnimals safeAnimal = SafeAnimals.find(Integer.parseInt(request.params("id")));
      model.put("safeAnimal", SafeAnimals.all());
      model.put("endangeredAnimal", EndangeredAnimals.all());
      model.put("template", "templates/animals.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/endangered", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String endangeredAnimalName = request.queryParams("name");
      int endangeredAnimalHealth = Integer.parseInt(request.queryParams("health"));
      int endangeredAnimalAge = Integer.parseInt (request.queryParams("age"));
      EndangeredAnimals endangeredAnimal  = new EndangeredAnimals(endangeredAnimalName, endangeredAnimalHealth, endangeredAnimalAge);
      endangeredAnimal.save();
      model.put("endangeredAnimals", EndangeredAnimals.all());
      model.put("template", "templates/animals/endangered.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/safe", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String safeAnimalName = request.queryParams("name");
      SafeAnimals safeAnimal  = new SafeAnimals(safeAnimalName);
      safeAnimal.save();
      model.put("safeAnimals", SafeAnimals.all());
      model.put("template", "templates/animals/safe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings", (request, response) -> {
     Map<String, Object> model = new HashMap<String, Object>();
     model.put("sightings", Sightings.all());
     model.put("template", "templates/sightings.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

//if else statement to do all animals in one page?
//add saves needed to save to DB

  post("/sightings", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    SafeAnimals safeAnimal = SafeAnimals.find(Integer.parseInt(request.params(":id")));
    EndangeredAnimals endangeredAnimal = EndangeredAnimals.find(Integer.parseInt(request.params(":id")));
    String sightingsLocation = request.queryParams("location");
    String rangerName = request.queryParams("name");
    Sightings sighting = new Sightings(sightingsLocation, rangerName, safeAnimal.getId());
    sighting.save();
    model.put("sightings", Sightings.all());
    model.put("template", "templates/sightings.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

 // post("/sightings/safeAnimals", (request, response) -> {
 //   Map<String, Object> model = new HashMap<String, Object>();
 //   SafeAnimals safeAnimal = SafeAnimals.find(Integer.parseInt(request.params(":id")));
 //   String sightingsLocation = request.queryParams("location");
 //   String rangerName = request.queryParams("name");
 //   Sightings sighting = new Sightings(sightingsLocation, rangerName, safeAnimal.getId());
 //   sighting.save();
 //   model.put("sightings", Sightings.all());
 //   model.put("template", "templates/sightings.vtl");
 //   return new ModelAndView(model, layout);
 // }, new VelocityTemplateEngine());
 //
 // post("/sightings/endangeredAnimals", (request, response) -> {
 //   Map<String, Object> model = new HashMap<String, Object>();
 //   EndangeredAnimals endangeredAnimal = EndangeredAnimals.find(Integer.parseInt(request.params(":id")));
 //   String sightingsLocation = request.queryParams("location");
 //   String rangerName = request.queryParams("name");
 //   Sightings sighting = new Sightings(sightingsLocation, rangerName, endangeredAnimal.getId());
 //   sighting.save();
 //   model.put("sightings", Sightings.all());
 //   model.put("template", "templates/sightings.vtl");
 //   return new ModelAndView(model, layout);
 // }, new VelocityTemplateEngine());

  //  post("/sightings/endangeredAnimal", (request, response) -> {
  //    Map<String, Object> model = new HashMap<String, Object>();
  //    EndangeredAnimals endangeredAnimal = EndangeredAnimals.find(Integer.parseInt(request.params("animalId")));
  //    Sightings sighting =
  //    String sightingsLocation = request.queryParams("location");
  //    String rangerName = request.queryParams("name");
   //
  //    model.put("animals", animals.getAllSightings());
  //    model.put("template", "templates/sightings.vtl");
  //    return new ModelAndView(model, layout);
  //  }, new VelocityTemplateEngine());
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
