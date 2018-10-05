package pico.erp.company.contact;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import pico.erp.company.CompanyId;

public interface CompanyContactRepository {

  CompanyContact create(@NotNull CompanyContact companyContact);

  void deleteBy(@NotNull CompanyContactId id);

  boolean exists(@NotNull CompanyContactId id);

  Stream<CompanyContact> findAllBy(@NotNull CompanyId companyId);

  Optional<CompanyContact> findBy(@NotNull CompanyContactId id);

  void update(@NotNull CompanyContact companyContact);

}
