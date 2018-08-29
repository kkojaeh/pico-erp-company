package pico.erp.company;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.company.CompanyAddressRequests.CreateRequest;
import pico.erp.company.CompanyAddressRequests.DeleteRequest;
import pico.erp.company.CompanyAddressRequests.UpdateRequest;
import pico.erp.company.data.CompanyAddressData;
import pico.erp.company.data.CompanyAddressId;
import pico.erp.company.data.CompanyId;

public interface CompanyAddressService {

  CompanyAddressData create(@Valid CreateRequest request);

  void delete(@Valid DeleteRequest request);

  boolean exists(@NotNull CompanyAddressId id);

  CompanyAddressData get(@NotNull CompanyAddressId id);

  List<CompanyAddressData> getAll(@NotNull CompanyId companyId);

  void update(@Valid UpdateRequest request);

}
