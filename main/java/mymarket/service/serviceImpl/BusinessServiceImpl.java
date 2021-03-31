package mymarket.service.serviceImpl;

import mymarket.model.entities.Business;
import mymarket.model.service.BusinessServiceModel;
import mymarket.repository.BusinessRepository;
import mymarket.service.BusinessService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.BatchUpdateException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository businessRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BusinessServiceImpl(BusinessRepository businessRepository, ModelMapper modelMapper) {
        this.businessRepository = businessRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public BusinessServiceModel addBusiness(BusinessServiceModel businessServiceModel) {

        Business business = this.modelMapper.map(businessServiceModel, Business.class);

        return this.modelMapper.map(this.businessRepository.saveAndFlush(business), BusinessServiceModel.class);
    }

    @Override
    public List<BusinessServiceModel> findAllBusinesses() {


        return this.businessRepository.findAll()
                .stream()
                .map(business -> this.modelMapper.map(business,BusinessServiceModel.class))
                .collect(Collectors.toList());
    }
}
