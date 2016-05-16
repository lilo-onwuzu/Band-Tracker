// import static spark.Spark.*;
// import spark.ModelAndView;
// import spark.template.velocity.VelocityTemplateEngine;
// import java.util.List;
// import java.util.HashMap;
//
// public class App {
//
//   public static void main(String [] args) {
//     staticFileLocation("/public");
//     String layout = "templates/layout.vtl";
//
//     get("/stylists", (request, response) -> {
//       HashMap model = new HashMap();
//       model.put("stylists", Stylist.all());
//       model.put("template", "templates/stylists.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     post("/stylists", (request, response) -> {
//       HashMap model = new HashMap();
//       String newInputStylist = request.queryParams("inputStylist");
//       Stylist stylist = new Stylist(newInputStylist);
//       // create stylist: save to the DB as a row in the sylists table
//       stylist.save();
//       model.put("stylists", Stylist.all());
//       model.put("template", "templates/stylists.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/stylists/new", (request, response) -> {
//       HashMap model = new HashMap();
//       model.put("template", "templates/newStylist.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/stylists/:stylist_id", (request, response) -> {
//       HashMap<String, Object> model = new HashMap<String, Object>();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
//       model.put("stylist", stylist);
//       model.put("clients", stylist.getClients());
//       model.put("template", "templates/stylist.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     // renders edit Stylist form
//     get("/stylists/:stylist_id/edit", (request, response) -> {
//       HashMap model = new HashMap();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
//       model.put("stylist", stylist);
//       model.put("template", "templates/editStylist.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     // post to this url path whenever a stylist name is edited
//     post("/stylists/:stylist_id", (request, response) -> {
//       HashMap<String, Object> model = new HashMap<String, Object>();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
//       String name = request.queryParams("editStylist");
//       // update stylist
//       stylist.update(name);
//       response.redirect("/stylists/" + stylist.getId());
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     // deletes stylist after you click on the link and then redirects to full list of stylists page
//     get("/stylists/:stylist_id/remove", (request, response) -> {
//       HashMap model = new HashMap();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
//       // delete stylist
//       stylist.delete();
//       response.redirect("/stylists");
//       model.put("stylist", stylist);
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     // renders add client form after you click on the "addClient" link
//     get("/stylists/:stylist_id/clients/new", (request, response) -> {
//       HashMap model = new HashMap();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
//       model.put("stylist", stylist);
//       model.put("template", "templates/newClient.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/stylists/:stylist_id/clients", (request, response) -> {
//       HashMap model = new HashMap();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
//       model.put("stylist", stylist);
//       model.put("clients", stylist.getClients());
//       model.put("template", "templates/clients.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     // post to this url path whenever a new client is created
//     post("/stylists/:stylist_id/clients", (request, response) -> {
//       HashMap<String, Object> model = new HashMap<String, Object>();
//       // read stylist
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
//       String newInputClient = request.queryParams("inputClient");
//       // create a new client. Also a new one to many association is made between this client and this stylist
//       Client client = new Client(newInputClient, stylist.getId());
//       client.save();
//       model.put("stylist", stylist);
//       model.put("clients", stylist.getClients());
//       model.put("template", "templates/clients.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     // brings up page that allows you to delete or update this client
//     get("/stylists/:stylist_id/clients/:id", (request, response) -> {
//       HashMap model = new HashMap();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
//       Client client = Client.find(Integer.parseInt(request.params(":id")));
//       model.put("stylist", stylist);
//       model.put("client", client);
//       model.put("template", "templates/client.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     // updates client
//     post("/stylists/:stylist_id/clients/:id", (request, response) -> {
//       HashMap model = new HashMap();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
//       Client client = Client.find(Integer.parseInt(request.params(":id")));
//       String editClient = request.queryParams("editClient");
//       client.update(editClient);
//       client = Client.find(Integer.parseInt(request.params(":id")));
//       model.put("stylist", stylist);
//       model.put("client", client);
//       model.put("template", "templates/client.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     // deletes client after you click on the link and then redirects to full list of clients page
//     get("/stylists/:stylist_id/clients/:id/remove", (request, response) -> {
//       HashMap model = new HashMap();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
//       Client client = Client.find(Integer.parseInt(request.params(":id")));
//       client.delete();
//       response.redirect("/stylists/" + stylist.getId() + "/clients");
//       model.put("stylist", stylist);
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//   }
// }
