package pico.erp.company.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.company.Company;
import pico.erp.company.CompanyEntity;
import pico.erp.company.CompanyId;
import pico.erp.company.CompanyMapper;

@Mapper
public abstract class CompanyContactMapper {

  @Lazy
  @Autowired
  protected CompanyMapper companyMapper;

  public CompanyContact domain(CompanyContactEntity entity) {
    return CompanyContact.builder()
      .id(entity.getId())
      .company(companyMapper.domain(entity.getCompany()))
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
  public abstract CompanyContactEntity entity(CompanyContact companyContact);

  protected CompanyEntity entity(Company company) {
    return companyMapper.entity(company);
  }

  public abstract CompanyContactMessages.DeleteRequest map(
    CompanyContactRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "companyId", source = "company.id")
  })
  public abstract CompanyContactData map(CompanyContact companyContact);

  @Mappings({
    @Mapping(target = "company", source = "companyId")
  })
  public abstract CompanyContactMessages.CreateRequest map(
    CompanyContactRequests.CreateRequest request);

  public abstract CompanyContactMessages.UpdateRequest map(
    CompanyContactRequests.UpdateRequest request);

  protected Company map(CompanyId companyId) {
    return companyMapper.map(companyId);
  }

  public abstract void pass(CompanyContactEntity from, @MappingTarget CompanyContactEntity to);

}
