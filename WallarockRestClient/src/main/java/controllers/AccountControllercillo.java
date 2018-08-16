package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import catalog.Product;
import client.Users;
import login.LoginOperands;
import register.RegisterOperands;
import search.SimpleSearchOperands;

@Controller
public class AccountControllercillo {

	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/profileaction", method = RequestMethod.POST)
	public String profileaction(HttpSession session, Model model, @ModelAttribute RegisterOperands operands) {
		if(operands.getPassword().equals(operands.getRepeatpassword())) {
			Users result = restTemplate.postForObject("http://localhost:8010/client/modifyuser/", operands, Users.class);
			session.setAttribute("credentialresult", result);
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			@SuppressWarnings("unchecked")
			List<Product> a = restTemplate.getForObject("http://localhost:8020/catalog/topsixbyprice/", ArrayList.class);
			model.addAttribute("products", a);
			return "HomepageLogged";
		} else {
			return "Error";
		}
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(Model model, HttpSession session) {
		Users logged = (Users) session.getAttribute("credentialresult");
		RegisterOperands b = restTemplate.getForObject("http://localhost:8010/client/getuser/" + logged.getEmail() + "/", RegisterOperands.class);
		model.addAttribute("operands", b);
		model.addAttribute("simplesearch", new SimpleSearchOperands());
		@SuppressWarnings("unchecked")
		List<Product> a = restTemplate.getForObject("http://localhost:8020/catalog/productsbyuseremail/" + logged.getEmail() + "/", ArrayList.class);
		model.addAttribute("products", a);
		return "Profile";
	}
	
	@RequestMapping(value = "/deleteaccount", method = RequestMethod.GET)
	public String deleteaccount(HttpSession session, Model model, @RequestParam ("id") String id) {
		if(session.getAttribute("credentialresult") == null) {
			return "Error";
		} else {
			Users logged = (Users) session.getAttribute("credentialresult");
					
			JsonNode delprods = restTemplate.getForObject("http://localhost:8020/catalog/productsbyuseremail/" + id + "/", JsonNode.class);

			ObjectMapper mapper = new ObjectMapper();

			List<Product> prodlist = null;
			try {
				prodlist = mapper.readValue(
						mapper.treeAsTokens(delprods), 
						new TypeReference<List<Product>>(){});
			} catch (IOException e) {
				e.printStackTrace();
			}				
			restTemplate.delete("http://localhost:8030/chat/deletealluserchats/" + id + "/");
			
			for(int i = 0; i < delprods.size(); i++) {
				restTemplate.delete("http://localhost:8020/catalog/deleteproduct/" + prodlist.get(i).getId() + "/");
			}

			restTemplate.delete("http://localhost:8010/client/deleteuser/" + id + "/");
			
			
			@SuppressWarnings("unchecked")
			List<Product> a = restTemplate.getForObject("http://localhost:8020/catalog/topsixbyprice/", ArrayList.class);
			model.addAttribute("products", a);
			
			if(logged.getEmail().equals("admin")) {
				model.addAttribute("simplesearch", new SimpleSearchOperands());
				return "HomepageLogged";
			}
			session.invalidate();
			return "HomepageUnlogged";
		}

	}

	@RequestMapping(value = "/loginaction", method = RequestMethod.POST)
	public String loginaction(Model model, HttpSession session, @ModelAttribute LoginOperands operands) {
		if(operands.getEmail() != null || operands.getPassword() != null) {
			Users result = restTemplate.getForObject("http://localhost:8010/client/checkcredentials/" + operands.getEmail() + "/" + operands.getPassword(), Users.class);
			if(result != null) {
				session.setAttribute("credentialresult", result);
				@SuppressWarnings("unchecked")
				List<Product> a = restTemplate.getForObject("http://localhost:8020/catalog/topsixbyprice/", ArrayList.class);
				model.addAttribute("products", a);
				model.addAttribute("simplesearch", new SimpleSearchOperands());
				return "HomepageLogged";
			} else {
				return "Error";
			}
		} else {
			return "Error";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("operands",new LoginOperands("",""));
		return "Login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) {
		session.invalidate();
		@SuppressWarnings("unchecked")
		List<Product> a = restTemplate.getForObject("http://localhost:8020/catalog/topsixbyprice/", ArrayList.class);
		model.addAttribute("products", a);
		return "HomepageUnlogged";
	}

	@RequestMapping(value = "/registeraction", method = RequestMethod.POST)
	public String registeraction(HttpSession session, Model model, @ModelAttribute RegisterOperands operands) {
		if(operands.getPassword().equals(operands.getRepeatpassword())) {
			Users result = restTemplate.postForObject("http://localhost:8010/client/createuser/", operands, Users.class);
			session.setAttribute("credentialresult", result);
			@SuppressWarnings("unchecked")
			List<Product> a = restTemplate.getForObject("http://localhost:8020/catalog/topsixbyprice/", ArrayList.class);
			model.addAttribute("products", a);
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			return "HomepageLogged";
		} else {
			return "Error";
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("operands",new RegisterOperands());
		return "Register";
	}
	
}
