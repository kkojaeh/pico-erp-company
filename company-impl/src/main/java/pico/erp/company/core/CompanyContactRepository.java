package pico.erp.company.core;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import pico.erp.company.data.CompanyContactId;
import pico.erp.company.data.CompanyId;
import pico.erp.company.domain.CompanyContact;

public interface CompanyContactRepository {

  CompanyContact create(@NotNull CompanyContact companyContact);

  void deleteBy(@NotNull CompanyContactId id);

  boolean exists(@NotNull CompanyContactId id);

  Stream<CompanyContact> findAllBy(@NotNull CompanyId companyId);

  Optional<CompanyContact> findBy(@NotNull CompanyContactId id);

  void update(@NotNull CompanyContact companyContact);

}
