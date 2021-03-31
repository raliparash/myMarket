package mymarket.service;

import mymarket.model.service.RoleServiceModel;
import mymarket.model.service.UserServiceModel;

import java.util.Set;

public interface RoleService  {

    void seedRolesInDb();

  //void assignUserRoles(UserServiceModel userServiceModel, long numberOfUsers);

   Set<RoleServiceModel> findAllRoles();
   RoleServiceModel findByAuthority(String authority);


}
