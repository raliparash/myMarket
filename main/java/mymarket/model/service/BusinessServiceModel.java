package mymarket.model.service;

import mymarket.model.entities.Category;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class BusinessServiceModel extends BaseServiceModel{

    private String name;
    private String type;
    private String description;
    private String imageUrl;
    private List<CategoryServiceModel> categories;

    public BusinessServiceModel() {
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
    @NotNull
    @Length(min=2, message = "Description length must be between 2 and 20 characters!" )
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<CategoryServiceModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryServiceModel> categories) {
        this.categories = categories;
    }
}
