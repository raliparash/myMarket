package mymarket.model.binding;

import mymarket.model.service.CategoryServiceModel;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class BusinessBindingModel {

    private String name;
    private String type;
    private String description;
    private MultipartFile image;

   private List<String> categories;


    public BusinessBindingModel() {
    }
    @NotNull
    @Length(min=2, message = "The name length must be between 2 and 20 characters!" )
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotNull
    @Length(min=2, message = "The type length must be between 2 and 20 characters!" )
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
