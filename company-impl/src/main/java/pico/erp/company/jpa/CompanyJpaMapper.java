package pico.erp.company.jpa;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import pico.erp.company.Company;
import pico.erp.company.address.CompanyAddress;
import pico.erp.company.contact.CompanyContact;

@Mapper
public abstract class CompanyJpaMapper {

  public Company map(CompanyEntity entity) {
    return Company.builder()
      .id(entity.getId())
      .name(entity.getName())
      .registrationNumber(entity.getRegistrationNumber())
      .supplier(entity.isSupplier())
      .customer(entity.isCustomer())
      .representative(entity.getRepresentative())
      .enabled(entity.isEnabled())
      .build();
  }

  @Mappings({
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract CompanyEntity map(Company company);

  public CompanyContact map(CompanyContactEntity entity) {
    return CompanyContact.builder()
      .id(entity.getId())
      .company(map(entity.getCompany()))
      .contact(entity.getContact())
      .enabled(entity.isEnabled())
      .build();
  }

  @Mappings({
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract CompanyContactEntity map(CompanyContact companyContact);

  public CompanyAddress map(CompanyAddressEntity entity) {
    return CompanyAddress.builder()
      .id(entity.getId())
      .company(map(entity.getCompany()))
      .name(entity.getName())
      .address(entity.getAddress())
      .telephoneNumber(entity.getTelephoneNumber())
      .mobilePhoneNumber(entity.getMobilePhoneNumber())
      .enabled(entity.isEnabled())
      .build();
  }

  @Mappings({
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract CompanyAddressEntity map(CompanyAddress companyContact);

  public abstract void pass(CompanyEntity from, @MappingTarget CompanyEntity to);

  public abstract void pass(CompanyContactEntity from, @MappingTarget CompanyContactEntity to);

  public abstract void pass(CompanyAddressEntity from,
    @MappingTarget CompanyAddressEntity to);


}
