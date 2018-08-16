package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import catalog.Product;
import client.Users;
import search.SimpleSearchOperands;

@Controller
public class ProductControllercillo {
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/deleteproduct", method = RequestMethod.GET)
	public String deleteproduct(HttpSession session, Model model, @RequestParam("id") int id) {
		if(session.getAttribute("credentialresult") == null) {
			return "Error";
		} else {
			restTemplate.delete("http://localhost:8020/catalog/deleteproduct/" + id + "/");
			@SuppressWarnings("unchecked")
			List<Product> a = restTemplate.getForObject("http://localhost:8020/catalog/topsixbyprice/", ArrayList.class);
			model.addAttribute("products", a);
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			return "HomepageLogged";
		}

	}

	@RequestMapping(value = "/editproductaction", method = RequestMethod.POST)
	public String editproductaction(HttpSession session, Model model, @ModelAttribute Product operands, @RequestParam MultipartFile file, @RequestParam("id") int id) {
		if(session.getAttribute("credentialresult") != null) {
			
			Product p = restTemplate.getForObject("http://localhost:8020/catalog/getproduct/" + id + "/", Product.class);
			operands.setUser(p.getUser());
			operands.setId(id);

			Product result = restTemplate.postForObject("http://localhost:8020/catalog/updateproduct/", operands, Product.class);
			model.addAttribute("product", result);

			LinkedMultiValueMap<String, Object> parts = 
					new LinkedMultiValueMap<String, Object>();
			parts.add("useremail", result.getUser().getEmail());
			File f = null;
			try {
				String tmp = System.getProperty("catalina.home") + File.separator + file.getOriginalFilename();
				f = new File(tmp);
				file.transferTo(f);
				parts.add("file", new FileSystemResource(tmp));

			} catch (IOException e) {
				e.printStackTrace();
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
					new HttpEntity<LinkedMultiValueMap<String, Object>>(parts, headers);

			ResponseEntity<String> response =
					restTemplate.exchange("http://localhost:8020/catalog/uploadPhoto/", 
							HttpMethod.POST, requestEntity, String.class);

			f.delete();
			if (response == null || response.getBody().trim().equals("")) {
				return "Error";
			}

			model.addAttribute("simplesearch", new SimpleSearchOperands());
			return "ProductLogged";
		} else {
			return "ProductUnlogged";
		}

	}

	@RequestMapping(value = "/editproduct", method = RequestMethod.GET)
	public String editproduct(HttpSession session, Model model, @RequestParam("id") int id) {
		if(session.getAttribute("credentialresult") != null) {
			Product result = restTemplate.getForObject("http://localhost:8020/catalog/getproduct/" + id + "/", Product.class);
			model.addAttribute("product", result);
			model.addAttribute("operands",result);
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			return "EditProduct";
		} else {
			return "Error";
		}
	}


	@RequestMapping(value = "/addproductaction", method = RequestMethod.POST)
	public String addproductaction(HttpSession session, Model model, @ModelAttribute Product operands, @RequestParam MultipartFile file) {

		if(session.getAttribute("credentialresult") != null) {
			Users u = new Users();
			Users l = (Users) session.getAttribute("credentialresult");
			u.setEmail(l.getEmail());
			u.setName(l.getName());
			u.setPassword(l.getPassword());
			u.setSurname(l.getSurname());
			u.setAdmin(l.getAdmin());
			u.setCity(l.getCity());
			operands.setUser(u);
			operands.setStatus(1);
			Product result = restTemplate.postForObject("http://localhost:8020/catalog/createproduct/", operands, Product.class);
			model.addAttribute("product", result);

			LinkedMultiValueMap<String, Object> parts = 
					new LinkedMultiValueMap<String, Object>();
			parts.add("useremail", result.getUser().getEmail());
			File f = null;
			try {
				String tmp = System.getProperty("catalina.home") + File.separator + file.getOriginalFilename();
				f = new File(tmp);
				file.transferTo(f);
				parts.add("file", new FileSystemResource(tmp));

			} catch (IOException e) {
				e.printStackTrace();
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
					new HttpEntity<LinkedMultiValueMap<String, Object>>(parts, headers);

			ResponseEntity<String> response =
					restTemplate.exchange("http://localhost:8020/catalog/uploadPhoto/", 
							HttpMethod.POST, requestEntity, String.class);

			f.delete();
			if (response == null || response.getBody().trim().equals("")) {
				return "Error";
			}

			model.addAttribute("simplesearch", new SimpleSearchOperands());
			return "ProductLogged";
		} else {
			return "ProductUnlogged";
		}

	}

	@RequestMapping(value = "/addproduct", method = RequestMethod.GET)
	public String addproduct(HttpSession session, Model model) {
		if(session.getAttribute("credentialresult") != null) {
			model.addAttribute("operands",new Product());
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			return "Addproduct";	
		} else {
			return "Error";
		}
	}

	@RequestMapping(value = "/productaction", method = RequestMethod.GET)
	public String productdetail(HttpSession session, Model model, @RequestParam("id") int id) {
		Product result = restTemplate.getForObject("http://localhost:8020/catalog/getproduct/" + id + "/", Product.class);
		model.addAttribute("product", result);
		if(session.getAttribute("credentialresult") == null) {
			return "ProductUnlogged";
		} else {
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			return "ProductLogged";
		}

	}

}
