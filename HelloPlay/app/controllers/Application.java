package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Application extends Controller {
  
    public static Result index() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String str = sdf.format(new Date());

        return ok(index.render("Hello world! Сегодня " + str));
    }
  
}
