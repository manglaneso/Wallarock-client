package controllers;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import catalog.Product;
import search.SimpleSearchOperands;

@Controller
public class MainControllercillo {

	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<Product> a = restTemplate.getForObject("http://localhost:8020/catalog/topsixbyprice/", ArrayList.class);
		model.addAttribute("products", a);
		model.addAttribute("simplesearch", new SimpleSearchOperands());
		if(session.getAttribute("credentialresult") == null) {
			return "HomepageUnlogged";
		} else {
			return "HomepageLogged";
		}
	}
}

