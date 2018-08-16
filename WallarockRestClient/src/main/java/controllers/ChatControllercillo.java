package controllers;

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

import chat.Chat;
import chat.ChatOperands;
import client.Users;
import search.SimpleSearchOperands;

@Controller
public class ChatControllercillo {
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/chatlist", method = RequestMethod.GET)
	public String chatlist(HttpSession session, Model model) {
		if(session.getAttribute("credentialresult")!=null) {
			Users login_result = (Users) session.getAttribute("credentialresult");
			
			@SuppressWarnings("unchecked")
			List<Chat> r = restTemplate.getForObject("http://localhost:8030/chat/allchatsby/" + login_result.getEmail() + "/", ArrayList.class);
			model.addAttribute("chats", r);
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			return "Chatlist";
		} else {
			return "Error";
		}
	}
	
	@RequestMapping(value = "/chataction", method = RequestMethod.POST)
	public String chataction(HttpSession session, Model model, @ModelAttribute ChatOperands operands, @RequestParam("id") String id) {
		if(session.getAttribute("credentialresult")!=null) {
			Users login_result = (Users) session.getAttribute("credentialresult");
			Chat r = restTemplate.getForObject("http://localhost:8030/chat/getchat/" + login_result.getEmail() + "/" + id + "/",Chat.class);
			r.appendMessage(login_result.getEmail(), operands.getMessage());
			Chat result = restTemplate.postForObject("http://localhost:8030/chat/updatechat/" + r.getId() + "/", r, Chat.class);
			model.addAttribute("chat", result);
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			model.addAttribute("operands", new ChatOperands());
			return "Chat";
		} else {
			return "Error";
		}
	}

	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	public String chat(Model model, HttpSession session, @RequestParam ("id") String id) {
		if(session.getAttribute("credentialresult")!=null) {
			Users login_result = (Users) session.getAttribute("credentialresult");
			boolean check = restTemplate.getForObject("http://localhost:8030/chat/checkchat/" + login_result.getEmail() + "/" + id + "/",boolean.class);	
			if (check){
				Chat result = restTemplate.getForObject("http://localhost:8030/chat/getchat/" + login_result.getEmail() + "/" + id + "/",Chat.class);
				model.addAttribute("chat",result);
			}else{//create chat if it does not exist
				Chat initial_chat = new Chat();
				initial_chat.setUser1(login_result.getEmail()); //sender of message
				initial_chat.setUser2(id); //receiver of message
				initial_chat.setMessages(new ArrayList<String>());
				Chat new_chat = restTemplate.postForObject("http://localhost:8030/chat/newchat", initial_chat, Chat.class);
				model.addAttribute("chat",new_chat);
			}
			model.addAttribute("operands", new ChatOperands());
			model.addAttribute("simplesearch", new SimpleSearchOperands());
			return "Chat";
		}else{
			return "Error";
		}
	}

}
