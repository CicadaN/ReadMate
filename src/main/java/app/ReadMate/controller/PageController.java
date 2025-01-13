package app.ReadMate.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class PageController {

    @GetMapping("/")
    public String indexPage() { return "index"; // Шаблон index.html
    }

    @GetMapping("/register")
    public String registerPage() { return "register"; // Шаблон register.html
    }

    @GetMapping("/welcome")
    public String welcomePage() { return "welcome"; // Шаблон welcome.html
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            System.out.println("CSRF Token in controller: " + csrfToken.getToken());
            model.addAttribute("_csrf", csrfToken);
        } else {
            System.out.println("CSRF Token in controller: null");
        }
        return "login";
    }
}
