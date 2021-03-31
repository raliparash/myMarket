package mymarket.model.service;

import mymarket.model.entities.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class CategoryServiceModel extends BaseEntity {

    private String name;
    private String description;

    public CategoryServiceModel() {
    }

    @NotNull
    @Length(min = 5, message = "The length of category name must be minimum 5 letters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Length(min = 10, message = "The length of category description must be minimum 10 letters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
