package pico.erp.company.contact;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.company.CompanyId;
import pico.erp.company.contact.CompanyContactRequests.CreateRequest;
import pico.erp.company.contact.CompanyContactRequests.DeleteRequest;
import pico.erp.company.contact.CompanyContactRequests.UpdateRequest;

public interface CompanyContactService {

  CompanyContactData create(@Valid CreateRequest request);

  void delete(@Valid DeleteRequest request);

  boolean exists(@NotNull CompanyContactId id);

  CompanyContactData get(@NotNull CompanyContactId id);

  List<CompanyContactData> getAll(@NotNull CompanyId companyId);

  void update(@Valid UpdateRequest request);

}
