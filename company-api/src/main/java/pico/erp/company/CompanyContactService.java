package pico.erp.company;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.company.CompanyContactRequests.CreateRequest;
import pico.erp.company.CompanyContactRequests.DeleteRequest;
import pico.erp.company.CompanyContactRequests.UpdateRequest;
import pico.erp.company.data.CompanyContactData;
import pico.erp.company.data.CompanyContactId;
import pico.erp.company.data.CompanyId;

public interface CompanyContactService {

  CompanyContactData create(@Valid CreateRequest request);

  void delete(@Valid DeleteRequest request);

  boolean exists(@NotNull CompanyContactId id);

  CompanyContactData get(@NotNull CompanyContactId id);

  List<CompanyContactData> getAll(@NotNull CompanyId companyId);

  void update(@Valid UpdateRequest request);

}
