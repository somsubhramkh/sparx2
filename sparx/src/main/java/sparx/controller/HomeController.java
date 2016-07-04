package sparx.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import sparx.model.Blog;
import sparx.model.Forum;
import sparx.model.Message;
import sparx.model.OutputMessage;
import sparx.model.User;
import sparx.service.BlogService;
import sparx.service.ForumService;
import sparx.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private BlogService blogService;
    
    @Autowired
    private ForumService forumService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("index");
		return model;
	}
	
	
	@RequestMapping("/Login")
	public ModelAndView login() {
		ModelAndView model = new ModelAndView("Login");
		return model;
	}
	
	
	@RequestMapping("/Wall")
	public ModelAndView wall(Principal principal) {
		
		//sparx.model.User user = userService.getUserByUsername(activeUser.getUsername());
		User user=userService.getUserByUsername(principal.getName());
		ModelAndView model = new ModelAndView("UserProfile");
		model.addObject("user", user);
		return model;
	}
	
	@RequestMapping("/Blog")
	public ModelAndView newBlog(Model m) {
		m.addAttribute("blog", new Blog());
		List<Blog> blogs = blogService.listBlogs();
		String json = new Gson().toJson(blogs);
		ModelAndView model = new ModelAndView("NewBlog");
		model.addObject("blogs", json);
		return model;
	}
	
	@RequestMapping(value = "/Blog/add",method=RequestMethod.POST)
	public String addUser(Model model,@Valid @ModelAttribute("blog") Blog b, BindingResult result, Principal principal) {

		if(result.hasErrors())
			return "NewBlog";
		
		b.setUser(principal.getName());
		b.setDate(new Date());
		blogService.addBlog(b);
					
		return "redirect:/Blog";
	
	}
	
		
	@RequestMapping("/ChatPage")
	public ModelAndView chat() {
		ModelAndView model = new ModelAndView("chat");
		return model;
	}
	
	@MessageMapping("/chat")
	  @SendTo("/topic/message")
	  public OutputMessage sendMessage(Message message, Principal principal) {
	    return new OutputMessage(message, new Date(),principal.getName());
	  }
	
	
	@RequestMapping("/Forum")
	public ModelAndView newForum(Model m) {
		m.addAttribute("forum", new Forum());
		List<Forum> forums = forumService.listForums();
		String json = new Gson().toJson(forums);
		ModelAndView model = new ModelAndView("NewForum");
		model.addObject("forums", json);
		return model;
	}
	
	@RequestMapping(value = "/Forum/add",method=RequestMethod.POST)
	public String addForum(Model model,@Valid @ModelAttribute("forum") Forum f, BindingResult result, Principal principal) {

		if(result.hasErrors())
			return "NewForum";
		
		f.setUser(principal.getName());
		f.setDate(new Date());
		forumService.addForum(f);
					
		return "redirect:/Forum";
	
	}
	
}
