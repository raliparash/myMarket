package mymarket.service.serviceImpl;

import mymarket.model.entities.User;
import mymarket.model.service.UserServiceModel;
import mymarket.repository.UserRepository;
import mymarket.service.RoleService;
import mymarket.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {

        this.roleService.seedRolesInDb();

        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());

            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));

        }

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));


        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public UserServiceModel findByUserName(String username) {


        return this.userRepository.findByUsername(username).map(user -> this.modelMapper
                .map(user, UserServiceModel.class)).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {

        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));


        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("IncorrectPassword");
        }

        user.setPassword(!"".equals(userServiceModel.getPassword()) ?
                this.bCryptPasswordEncoder.encode(userServiceModel.getPassword())
                : user.getPassword());

        user.setEmail(userServiceModel.getEmail());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll().stream().map(user -> this.modelMapper
                .map(user, UserServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public void setUserRole(String id, String role) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is incorrect id. The user not found"));

        UserServiceModel userModel = this.modelMapper.map(user, UserServiceModel.class);
        userModel.getAuthorities().clear();

        switch (role){

            case "user":
                userModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                break;

            case "admin":
                userModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                userModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
                userModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_ADMIN"));
                break;

            case "moderator":
                userModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                userModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
                break;
        }

        this.userRepository.saveAndFlush(this.modelMapper.map(userModel,User.class));

    }


}
