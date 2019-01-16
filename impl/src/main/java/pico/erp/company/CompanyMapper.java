package pico.erp.company;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.company.CompanyExceptions.NotFoundException;
import pico.erp.company.CompanyRequests.CreateRequest;
import pico.erp.company.CompanyRequests.DeleteRequest;
import pico.erp.company.CompanyRequests.UpdateRequest;

@Mapper
public abstract class CompanyMapper {

  @Lazy
  @Autowired
  private CompanyRepository companyRepository;

  public abstract CompanyData map(Company company);

  public abstract CompanyMessages.CreateRequest map(CreateRequest request);

  public abstract CompanyMessages.UpdateRequest map(UpdateRequest request);

  public abstract CompanyMessages.DeleteRequest map(DeleteRequest request);

  public Company jpa(CompanyEntity entity) {
    return Company.builder()
      .id(entity.getId())
      .name(entity.getName())
      .registrationNumber(entity.getRegistrationNumber())
      .supplier(entity.isSupplier())
      .customer(entity.isCustomer())
      .outsourcing(entity.isOutsourcing())
      .representative(entity.getRepresentative())
      .conditionDescription(entity.getConditionDescription())
      .itemDescription(entity.getItemDescription())
      .enabled(entity.isEnabled())
      .build();
  }

  @Mappings({
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract CompanyEntity jpa(Company company);

  public Company map(CompanyId companyId) {
    return Optional.ofNullable(companyId)
      .map(id -> companyRepository.findBy(id)
        .orElseThrow(NotFoundException::new)
      )
      .orElse(null);
  }

  public abstract void pass(CompanyEntity from, @MappingTarget CompanyEntity to);

}


