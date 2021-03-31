package mymarket.web;

import mymarket.model.binding.UserEditBindingModel;
import mymarket.model.binding.UserRegisterBindingModel;
import mymarket.model.service.UserServiceModel;
import mymarket.model.view.UserAllViewModel;
import mymarket.model.view.UserProfileViewModel;
import mymarket.service.UserService;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register() {
        return super.model("register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel userRegisterBindingModel) {

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            return super.model("register");
        }

        this.userService.registerUser(this.modelMapper
                .map(userRegisterBindingModel, UserServiceModel.class));

        return super.redirect("/users/login");

    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {


        return super.model("login");

    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("model",
                this.modelMapper.map(this.userService.findByUserName(principal.getName())
                        , UserProfileViewModel.class));

        return super.model("profile");
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("model",
                this.modelMapper.map(this.userService.findByUserName(principal.getName())
                        , UserProfileViewModel.class));

        return super.modelAndView("edit-profile", modelAndView);

    }

    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@ModelAttribute UserEditBindingModel model) {

        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.model("edit-profile");
        }

        this.userService.editUserProfile(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());

        return super.redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        List<UserAllViewModel> users = this.userService.findAllUsers()
                .stream()
                .map(u -> {
                    UserAllViewModel user = this.modelMapper.map(u, UserAllViewModel.class);
                    user.setAuthorities(u.getAuthorities().stream()
                            .map(a -> a.getAuthority()).collect(Collectors.toSet()));

                    return user;
                }).collect(Collectors.toList());

        modelAndView.addObject("users", users);
        return super.modelAndView("all-users", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize(("hasRole('ROLE_ADMIN')"))
    public ModelAndView setUser(@PathVariable String id) {

        this.userService.setUserRole(id, "user");

        return super.redirect("/users/all");

    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize(("hasRole('ROLE_ADMIN')"))
    public ModelAndView setModerator(@PathVariable String id) {
        this.userService.setUserRole(id, "moderator");

        return super.redirect("/users/all");

    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize(("hasRole('ROLE_ADMIN')"))
    public ModelAndView setAdmin(@PathVariable String id) {

        this.userService.setUserRole(id, "admin");

        return super.redirect("/users/all");

    }


}
