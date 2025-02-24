package flight_booking.demo.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import flight_booking.demo.domain.user.service.UserService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ViewController {
	private final UserService userService;

	@GetMapping("/")
	public String homePage() {
		return "home/home";
	}

	@GetMapping("/search")
	public String searchPage() {
		return "search/search";
	}

	@GetMapping("/signup")
	public String signupPage() {
		return "signup/signup";
	}

	@GetMapping("/login")
	public String signup() {
		return "oauthLogin/oauthLogin";
	}

}
