package pico.erp.company;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import pico.erp.company.CompanyExceptions.NotFoundException;
import pico.erp.company.CompanyRequests.CreateRequest;
import pico.erp.company.CompanyRequests.DeleteRequest;
import pico.erp.company.CompanyRequests.UpdateRequest;
import pico.erp.company.address.CompanyAddress;
import pico.erp.company.address.CompanyAddressMessages;
import pico.erp.company.address.CompanyAddressRequests;
import pico.erp.company.address.data.CompanyAddressData;
import pico.erp.company.contact.CompanyContact;
import pico.erp.company.contact.CompanyContactMessages;
import pico.erp.company.contact.CompanyContactRequests;
import pico.erp.company.contact.data.CompanyContactData;
import pico.erp.company.data.CompanyData;
import pico.erp.company.data.CompanyId;

@Mapper
public abstract class CompanyMapper {

  @Autowired
  private CompanyRepository companyRepository;

  public abstract CompanyData map(Company company);

  public abstract CompanyMessages.CreateRequest map(CreateRequest request);

  public abstract CompanyMessages.UpdateRequest map(UpdateRequest request);

  public abstract CompanyMessages.DeleteRequest map(DeleteRequest request);

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

  public abstract CompanyContactMessages.DeleteRequest map(
    CompanyContactRequests.DeleteRequest request);

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

  public abstract CompanyAddressMessages.DeleteRequest map(
    CompanyAddressRequests.DeleteRequest request);

  protected Company map(CompanyId companyId) {
    return Optional.ofNullable(companyId)
      .map(id -> companyRepository.findBy(id)
        .orElseThrow(NotFoundException::new)
      )
      .orElse(null);
  }

}


