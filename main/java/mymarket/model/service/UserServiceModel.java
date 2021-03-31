package mymarket.model.service;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel {


    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;

    private Set<RoleServiceModel> authorities;


    public UserServiceModel() {
    }



    @NotNull
    @Length(min=2, message = "First name length must be between 2 and 20 characters!" )
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Length(min = 2,max = 20,message = "First name length must be between 2 and 20 characters!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Length(min = 2,max = 20,message = "Last name length must be between 2 and 20 characters!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Email(message = "Email must contains @ and cannot be null")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 3,max = 20,message = "Password length must be between 3 and 20 characters!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "The gender cannot be null!")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

 
    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }


}
