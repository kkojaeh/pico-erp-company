package pico.erp.company;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.company.CompanyRequests.CreateRequest;
import pico.erp.company.CompanyRequests.DeleteRequest;
import pico.erp.company.CompanyRequests.UpdateRequest;

public interface CompanyService {

  CompanyData create(@Valid CreateRequest request);

  void delete(@Valid DeleteRequest request);

  boolean exists(@NotNull CompanyId id);

  boolean exists(@NotNull RegistrationNumber registrationNumber);

  CompanyData get(@NotNull CompanyId id);

  CompanyData get(@NotNull RegistrationNumber registrationNumber);

  CompanyData getOwner();

  void update(@Valid UpdateRequest request);

}
