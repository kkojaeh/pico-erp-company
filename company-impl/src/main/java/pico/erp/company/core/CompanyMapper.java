package pico.erp.company.core;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import pico.erp.company.CompanyAddressRequests;
import pico.erp.company.CompanyContactRequests;
import pico.erp.company.CompanyExceptions.NotFoundException;
import pico.erp.company.CompanyRequests.CreateRequest;
import pico.erp.company.CompanyRequests.DeleteRequest;
import pico.erp.company.CompanyRequests.UpdateRequest;
import pico.erp.company.data.CompanyAddressData;
import pico.erp.company.data.CompanyContactData;
import pico.erp.company.data.CompanyData;
import pico.erp.company.data.CompanyId;
import pico.erp.company.domain.Company;
import pico.erp.company.domain.CompanyAddress;
import pico.erp.company.domain.CompanyAddressMessages;
import pico.erp.company.domain.CompanyContact;
import pico.erp.company.domain.CompanyContactMessages;
import pico.erp.company.domain.CompanyMessages;

@Mapper
public abstract class CompanyMapper {

  @Autowired
  private CompanyRepository companyRepository;

  abstract CompanyData map(Company company);

  abstract CompanyMessages.CreateRequest map(CreateRequest request);

  abstract CompanyMessages.UpdateRequest map(UpdateRequest request);

  abstract CompanyMessages.DeleteRequest map(DeleteRequest request);

  @Mappings({
    @Mapping(target = "companyId", source = "company.id")
  })
  abstract CompanyContactData map(CompanyContact companyContact);

  @Mappings({
    @Mapping(target = "company", source = "companyId")
  })
  abstract CompanyContactMessages.CreateRequest map(CompanyContactRequests.CreateRequest request);

  abstract CompanyContactMessages.UpdateRequest map(CompanyContactRequests.UpdateRequest request);

  abstract CompanyContactMessages.DeleteRequest map(CompanyContactRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "companyId", source = "company.id")
  })
  abstract CompanyAddressData map(CompanyAddress companyAddress);

  @Mappings({
    @Mapping(target = "company", source = "companyId")
  })
  abstract CompanyAddressMessages.CreateRequest map(
    CompanyAddressRequests.CreateRequest request);

  abstract CompanyAddressMessages.UpdateRequest map(
    CompanyAddressRequests.UpdateRequest request);

  abstract CompanyAddressMessages.DeleteRequest map(
    CompanyAddressRequests.DeleteRequest request);

  protected Company map(CompanyId companyId) {
    return Optional.ofNullable(companyId)
      .map(id -> companyRepository.findBy(id)
        .orElseThrow(NotFoundException::new)
      )
      .orElse(null);
  }

}


