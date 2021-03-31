package mymarket.web;

import mymarket.model.binding.CategoryAddBindingModel;
import mymarket.model.service.CategoryServiceModel;
import mymarket.model.view.BusinessViewModel;
import mymarket.model.view.CategoryViewModel;
import mymarket.model.view.UserAllViewModel;
import mymarket.service.CategoryService;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    public final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategory() {

        return super.model("add-category");

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategoryConfirm(@ModelAttribute CategoryAddBindingModel categoryAddBindingModel) {

        this.categoryService.addCategory(modelMapper.map(categoryAddBindingModel, CategoryServiceModel.class));

        return super.redirect("categories/all");

    }


 //@GetMapping("/all")
 //@PreAuthorize("hasRole('ROLE_MODERATOR')")
 //public ModelAndView allCategories(ModelAndView modelAndView) {

 //    ModelAndView category = modelAndView.addObject("categories",
 //            this.categoryService.findAllCategories()
 //                    .stream().map(c -> this.modelMapper
 //                    .map(c, CategoryViewModel.class))
 //                    .collect(Collectors.toList()));

 //    return super.modelAndView("all-categories", modelAndView);
 //}
 @GetMapping("/all")
 @PreAuthorize("hasRole('ROLE_MODERATOR')")
 public ModelAndView allCategories(ModelAndView modelAndView) {

     modelAndView.addObject("categories", this.categoryService
             .findAllCategories().stream()
             .map(b -> this.modelMapper.map(b, CategoryViewModel.class))
             .collect(Collectors.toList()));

     return super.modelAndView("all-categories", modelAndView);
 }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editCategory(@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.addObject("model", this.modelMapper
                .map(this.categoryService.findCategoryById(id), CategoryViewModel.class));

        return super.modelAndView("edit-category", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editCategoryConfirm(@PathVariable String id, @ModelAttribute CategoryAddBindingModel categoryAddBindingModel) {

        this.categoryService.editCategory(id, this.modelMapper.map(categoryAddBindingModel, CategoryServiceModel.class));

        return super.redirect("/categories/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCategory(@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.addObject("model", this.modelMapper
                .map(this.categoryService.findCategoryById(id), CategoryViewModel.class));

        return super.modelAndView("delete-category", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCategoryConfirm(@PathVariable String id) {

        this.categoryService.deleteCategory(id);

        return super.redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @ResponseBody
    public List<CategoryViewModel> fetchCategories() {

        return this.categoryService.findAllCategories()
                .stream().map(category -> this.modelMapper.map(category, CategoryViewModel.class))
                .collect(Collectors.toList());

    }
}
