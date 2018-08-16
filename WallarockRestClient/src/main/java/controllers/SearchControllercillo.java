package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import catalog.Product;
import search.AdvancedSearchOperands;
import search.SimpleSearchOperands;

@Controller
public class SearchControllercillo {
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/advancedsearchaction", method = RequestMethod.POST)
	public String advancedsearchaction(HttpSession session, Model model, @ModelAttribute AdvancedSearchOperands operands) {
		if(session.getAttribute("credentialresult") != null) {
			JsonNode result = null;
			if(operands.getQuery().equals("") && operands.getCategory() != 0) {
				
				result = restTemplate.getForObject("http://localhost:8020/catalog/productsbycategory/" + operands.getCategory() + "/", JsonNode.class);
			} else {
				result = restTemplate.getForObject("http://localhost:8020/catalog/advancedsearch/" + operands.getQuery() + "/" + operands.getCategory() + "/", JsonNode.class);
			}
			
			ObjectMapper mapper = new ObjectMapper();

			List<Product> prodlist = null;
			try {
				prodlist = mapper.readValue(
						mapper.treeAsTokens(result), 
						new TypeReference<List<Product>>(){});
			} catch (IOException e) {
				e.printStackTrace();
			}
			model.addAttribute("products", prodlist);
			model.addAttribute("advancedsearch", new AdvancedSearchOperands());
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			return "Searchresults";
		} else {
			return "Error";
		}
	}

	@RequestMapping(value = "/simplesearchaction", method = RequestMethod.POST)
	public String simplesearchaction(HttpSession session, Model model, @ModelAttribute SimpleSearchOperands operands) {
		if(session.getAttribute("credentialresult") != null) {
			JsonNode result = null;
			if(operands.getQuery().equals("")) {
				result = restTemplate.getForObject("http://localhost:8020/catalog/allproducts/", JsonNode.class);
			} else {
				result = restTemplate.getForObject("http://localhost:8020/catalog/simplesearch/" + operands.getQuery() + "/", JsonNode.class);
			}
			
			ObjectMapper mapper = new ObjectMapper();

			List<Product> prodlist = null;
			try {
				prodlist = mapper.readValue(
						mapper.treeAsTokens(result), 
						new TypeReference<List<Product>>(){});
			} catch (IOException e) {
				e.printStackTrace();
			}
			model.addAttribute("products", prodlist);
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			model.addAttribute("advancedsearch", new AdvancedSearchOperands());
			return "Searchresults";
		} else {
			return "Error";
		}
	}

}
