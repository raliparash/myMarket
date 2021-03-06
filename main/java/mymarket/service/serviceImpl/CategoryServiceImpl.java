package mymarket.service.serviceImpl;

import mymarket.model.entities.Category;
import mymarket.model.service.CategoryServiceModel;
import mymarket.repository.CategoryRepository;
import mymarket.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {
        Category category = this.modelMapper.map(categoryServiceModel, Category.class);


        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category), CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return this.categoryRepository.findAll()
                .stream().map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel findCategoryById(String id) {

        Category  category = this.categoryRepository.findById(id).orElseThrow(()->new IllegalArgumentException("This id can not be found!"));


        return this.modelMapper.map(category,CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel) {

        Category category = this.categoryRepository.
                findById(id).orElseThrow(()-> new IllegalArgumentException("Category can not be found by this id!"));

        category.setName(categoryServiceModel.getName());
        category.setDescription(categoryServiceModel.getDescription());


        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category),CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel deleteCategory(String id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(()->new IllegalArgumentException("This id does not exist!"));

        this.categoryRepository.delete(category);
        return this.modelMapper.map(category,CategoryServiceModel.class);
    }
}
