package mymarket.service;

import mymarket.model.service.BusinessServiceModel;


import java.util.List;

public interface BusinessService {

    BusinessServiceModel addBusiness(BusinessServiceModel businessServiceModel);

    List<BusinessServiceModel> findAllBusinesses();
}
