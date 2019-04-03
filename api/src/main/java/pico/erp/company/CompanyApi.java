package pico.erp.company;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Role;

public final class CompanyApi {

  @RequiredArgsConstructor
  public enum Roles implements Role {

    COMPANY_MANAGER,

    COMPANY_ACCESSOR;

    @Id
    @Getter
    private final String id = name();

  }
}
