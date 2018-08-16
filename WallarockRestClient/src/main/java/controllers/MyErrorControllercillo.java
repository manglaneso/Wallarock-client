package controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorControllercillo implements ErrorController {


	@RequestMapping(value = "/error")
	public String error() {
		return "Error";
	}

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
