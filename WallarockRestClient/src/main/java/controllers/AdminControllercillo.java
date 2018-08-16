package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import catalog.Product;
import client.Users;
import register.RegisterOperands;
import search.SimpleSearchOperands;

@Controller
public class AdminControllercillo {
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/adminpanel")
	public String adminpanel(Model model, HttpSession session) {
		if(session.getAttribute("credentialresult") != null) {
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			@SuppressWarnings("unchecked")
			List<Users> a = restTemplate.getForObject("http://localhost:8010/client/allusers/", ArrayList.class);
			model.addAttribute("users", a);
			@SuppressWarnings("unchecked")
			List<Product> b = restTemplate.getForObject("http://localhost:8020/catalog/allproducts/", ArrayList.class);
			model.addAttribute("products", b);

			return "Adminpanel";
		} else {
			return "Error";
		}
	}
	
	@RequestMapping(value = "/adminprofile", method = RequestMethod.GET)
	public String adminprofile(Model model, HttpSession session, @RequestParam("id") String id) {
		model.addAttribute("simplesearch", new SimpleSearchOperands());
		RegisterOperands b = restTemplate.getForObject("http://localhost:8010/client/getuser/" + id + "/", RegisterOperands.class);
		model.addAttribute("operands", b);
		@SuppressWarnings("unchecked")
		List<Product> a = restTemplate.getForObject("http://localhost:8020/catalog/productsbyuseremail/" + id + "/", ArrayList.class);
		model.addAttribute("products", a);
		return "Profile";
	}

}
