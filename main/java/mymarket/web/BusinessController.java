package mymarket.web;

import mymarket.model.binding.BusinessBindingModel;
import mymarket.model.service.BusinessServiceModel;
import mymarket.model.view.BusinessViewModel;
import mymarket.service.BusinessService;
import mymarket.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/businesses")
public class BusinessController extends BaseController {

    private final BusinessService businessService;

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;


    @Autowired
    public BusinessController(BusinessService businessService, CategoryService categoryService, ModelMapper modelMapper) {
        this.businessService = businessService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }



    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addBusiness() {
        return super.model("add-business");
    }

    @PostMapping ("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addBusinessConfirm(@ModelAttribute BusinessBindingModel businessBindingModel) throws IOException {

        BusinessServiceModel businessServiceModel = this.modelMapper.map(businessBindingModel, BusinessServiceModel.class);

        businessServiceModel.
                setCategories(this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> businessBindingModel.getCategories().contains(c.getId()))
                        .collect(Collectors.toList()));



        this.businessService.addBusiness(businessServiceModel);
        return super.redirect("businesses/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allBusinesses(ModelAndView modelAndView) {

        modelAndView.addObject("businesses", this.businessService
                .findAllBusinesses().stream()
                .map(b -> this.modelMapper.map(b, BusinessViewModel.class))
                .collect(Collectors.toList()));

        return super.modelAndView("all-businesses", modelAndView);
    }

}
