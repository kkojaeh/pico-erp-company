package pico.erp.company.core;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import pico.erp.company.data.CompanyAddressId;
import pico.erp.company.data.CompanyId;
import pico.erp.company.domain.CompanyAddress;

public interface CompanyAddressRepository {

  CompanyAddress create(@NotNull CompanyAddress companyAddress);

  void deleteBy(@NotNull CompanyAddressId id);

  boolean exists(@NotNull CompanyAddressId id);

  Stream<CompanyAddress> findAllBy(@NotNull CompanyId companyId);

  Optional<CompanyAddress> findBy(@NotNull CompanyAddressId id);

  void update(@NotNull CompanyAddress companyAddress);

}
