package pico.erp.company.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.company.Company;
import pico.erp.company.CompanyId;
import pico.erp.company.CompanyMapper;

@Mapper
public abstract class CompanyAddressMapper {

  @Lazy
  @Autowired
  protected CompanyMapper companyMapper;

  public CompanyAddress domain(CompanyAddressEntity entity) {
    return CompanyAddress.builder()
      .id(entity.getId())
      .company(map(entity.getCompanyId()))
      .name(entity.getName())
      .address(entity.getAddress())
      .telephoneNumber(entity.getTelephoneNumber())
      .mobilePhoneNumber(entity.getMobilePhoneNumber())
      .enabled(entity.isEnabled())
      .build();
  }

  @Mappings({
    @Mapping(target = "companyId", source = "company.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract CompanyAddressEntity entity(CompanyAddress companyContact);

  public abstract CompanyAddressMessages.DeleteRequest map(
    CompanyAddressRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "companyId", source = "company.id")
  })
  public abstract CompanyAddressData map(CompanyAddress companyAddress);

  @Mappings({
    @Mapping(target = "company", source = "companyId")
  })
  public abstract CompanyAddressMessages.CreateRequest map(
    CompanyAddressRequests.CreateRequest request);

  public abstract CompanyAddressMessages.UpdateRequest map(
    CompanyAddressRequests.UpdateRequest request);

  protected Company map(CompanyId companyId) {
    return companyMapper.map(companyId);
  }

  public abstract void pass(CompanyAddressEntity from,
    @MappingTarget CompanyAddressEntity to);


}
