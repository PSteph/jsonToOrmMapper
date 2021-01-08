package com.bopuniv.server.website;

import com.bopuniv.server.dto.LoginDto;
import com.bopuniv.server.dto.SearchResult;
import com.bopuniv.server.dto.TrainingDto;
import com.bopuniv.server.dto.UserDto;
import com.bopuniv.server.entities.Privilege;
import com.bopuniv.server.entities.RegistrationToken;
import com.bopuniv.server.entities.Training;
import com.bopuniv.server.entities.User;
import com.bopuniv.server.listners.OnRegistrationCompleteEvent;
import com.bopuniv.server.repository.TrainingRepository;
import com.bopuniv.server.services.IRegistrationTokenService;
import com.bopuniv.server.services.IUserService;
import com.bopuniv.server.website.util.GenericResponse;
import com.bopuniv.server.website.util.RegistrationConfirm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class FrontController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private MessageSource messages;

    @Autowired
    IUserService userService;

    @Autowired
    IRegistrationTokenService regTokenService;

    @Autowired
    TrainingRepository trainingRepository;

    @GetMapping("/")
    public String homePage(Model model, Authentication authentication){
        model.addAttribute("isIndex", true);
        if(authentication != null)
            System.out.println("Is user authenticated: "+authentication.isAuthenticated());
        return "index";
    }

    @GetMapping("/trainings")
    public String trainingPage(Model model){

        model.addAttribute("search","trainings");
        model.addAttribute("isIndex", false);
        return "search";
    }

    @GetMapping("/universities")
    public String universities(Model model){

        model.addAttribute("search","universities");
        model.addAttribute("isIndex", false);
        return "search";
    }

    @GetMapping("/certifications")
    public String certifications(Model model){

        model.addAttribute("search","certifications");
        model.addAttribute("isIndex", false);
        return "search";
    }

    @GetMapping("/institutes")
    public String institutes(Model model){

        model.addAttribute("search","institutes");
        model.addAttribute("isIndex", false);
        return "search";
    }

    @GetMapping("/highSchools")
    public String highSchools(Model model){

        model.addAttribute("search","highSchools");
        model.addAttribute("isIndex", false);
        return "search";
    }

//    @GetMapping("/login")
//    public String loginPage(){
//
//        return "login";
//    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username or password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        LoginDto loginDto = new LoginDto();
        model.addAttribute("login", loginDto);

        model.addAttribute("isIndex", false);
        return "login";

    }

    @GetMapping("/public/search")
    public SearchResult<TrainingDto> search(@RequestParam(defaultValue = "0") int offset,
                                            @RequestParam(defaultValue = "10000") int limit){
        Pageable page = PageRequest.of(offset, limit);
        trainingRepository.findAll(page);
        return null;
    }

    @GetMapping("/adminPage")
    public String adminPage(){
        return "build/index";
    }

    @GetMapping("/registration")
    public String registration(WebRequest request, Model model) {
        System.out.println("Inside registration getMapping");

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        model.addAttribute("isIndex", false);
        return "registration";
    }

    @PostMapping("/registration")
    @ResponseBody
    public RegistrationConfirm registerUserAccount
            (@Valid final UserDto userDto,
             HttpServletRequest request, Model model) {

        model.addAttribute("isIndex", false);

        User registered = userService.registerNewUserAccount(userDto);

        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setEmail(userDto.getEmail());
        regTokenService.save(registrationToken);
//        return new GenericResponse("success");
        return new RegistrationConfirm(registrationToken.getToken());

    }

    @GetMapping("/successRegister")
    public String successRegister(Model model, HttpServletRequest request, @RequestParam String token){
        model.addAttribute("isIndex", false);
        final String defaultMessage = "We sent a verification email to <b>userEmail</b> to make sure your email is correct. <b>Please click on the link in that email to activate your account.</b> If you don't receive the email after 10 min <b>please check your spams</b>.";
        String email = regTokenService.findByToken(token).getEmail();
        String message = messages.getMessage("message.registration.successful.content", null,defaultMessage, request.getLocale());

        if(message != null)
            message = message.replace("userEmail", email);

        model.addAttribute("regSuccessMsg",  message);
        return "successRegister";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam("token") final String token) throws UnsupportedEncodingException {
        Locale locale = request.getLocale();
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final User user = userService.getUser(token);
            // if (user.isUsing2FA()) {
            // model.addAttribute("qr", userService.generateQRUrl(user));
            // return "redirect:/qrcode.html?lang=" + locale.getLanguage();
            // }
            authWithoutPassword(user);
            model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
            return "redirect:/verificationConfirm?lang=" + locale.getLanguage();
        }

        model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
        model.addAttribute("expired", "expired".equals(result));
        model.addAttribute("token", token);
        return "redirect:/badUser.html?lang=" + locale.getLanguage();
    }

    @GetMapping("/verificationConfirm")
    public String verificationConfirm(Model model){
        model.addAttribute("isIndex", false);
        return "verificationConfirm";
    }


    public void authWithoutPassword(User user) {
        List<Privilege> privileges = user.getRoles().stream().map(role -> role.getPrivileges()).flatMap(list -> list.stream()).distinct().collect(Collectors.toList());
        List<GrantedAuthority> authorities = privileges.stream().map(p -> new SimpleGrantedAuthority(p.getName())).collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
