package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.model.common.Cart;
import br.anhembi.funmodechild.entity.Customer;
import br.anhembi.funmodechild.model.common.PasswordNotMatchException;
import br.anhembi.funmodechild.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        session.setAttribute("carrinhocompras", new Cart());
        return "login";
    }

    @GetMapping("/registration")
    public ModelAndView registrar() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("cadastrar");
        mv.addObject("usuario", new Customer());
        return mv;
    }

    // TODO: Notificar que se o processo funcionou ou não
    @PostMapping("/registration")
    public ModelAndView registrar(Customer customer) {
        ModelAndView mv = new ModelAndView();
        userService.salvar(customer);
        mv.setViewName("redirect:/login");
        return mv;
    }

    @GetMapping("/conta")
    public ModelAndView conta() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("conta");
        return mv;
    }

    @PostMapping("/conta")
    public ModelAndView conta(@RequestParam(name = "passwordOld") String oldPassword,
                              @RequestParam(name = "passwordNew") String newPassword,
                              @RequestParam(name = "passwordNewConfirm") String newPasswordConfirm,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        Principal user = request.getUserPrincipal();

        String errorMessage;
        try {
            userService.updatePassword(user, oldPassword, newPassword, newPasswordConfirm);
            errorMessage = null;
        } catch (PasswordNotMatchException e) {
            errorMessage = e.getMessage();
        }

        redirectAttributes.addFlashAttribute("error", errorMessage);
        mv.setViewName("redirect:/conta");
        return mv;
    }
}
