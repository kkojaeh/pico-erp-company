package pico.erp.company;

import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface CompanyRepository {

  Company create(@NotNull Company company);

  void deleteBy(@NotNull CompanyId id);

  boolean exists(@NotNull CompanyId id);

  boolean exists(@NotNull RegistrationNumber registrationNumber);

  Optional<Company> findBy(@NotNull CompanyId id);

  Optional<Company> findBy(@NotNull RegistrationNumber registrationNumber);

  void update(@NotNull Company company);

}
