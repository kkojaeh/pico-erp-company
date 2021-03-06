package pico.erp.company.address;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.company.CompanyId;
import pico.erp.company.address.CompanyAddressRequests.CreateRequest;
import pico.erp.company.address.CompanyAddressRequests.DeleteRequest;
import pico.erp.company.address.CompanyAddressRequests.UpdateRequest;

public interface CompanyAddressService {

  CompanyAddressData create(@Valid CreateRequest request);

  void delete(@Valid DeleteRequest request);

  boolean exists(@NotNull CompanyAddressId id);

  CompanyAddressData get(@NotNull CompanyAddressId id);

  List<CompanyAddressData> getAll(@NotNull CompanyId companyId);

  void update(@Valid UpdateRequest request);

}
