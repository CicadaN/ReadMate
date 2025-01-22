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
    public String showHomePage(Model model) {
        model.addAttribute("childTemplate", "index");
        model.addAttribute("title", "Добро пожаловать на ReadMate");
        return "layout"; // <--- возвращаем "layout"
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("childTemplate", "register");
        model.addAttribute("title", "Регистрация");
        return "layout"; // <--- возвращаем "layout"
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }

        model.addAttribute("childTemplate", "login");
        model.addAttribute("title", "Страница входа");
        return "layout";
    }

}
