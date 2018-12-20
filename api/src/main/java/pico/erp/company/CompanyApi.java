package pico.erp.company;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.ApplicationId;
import pico.erp.shared.data.Role;

public final class CompanyApi {

  public static ApplicationId ID = ApplicationId.from("company");

  @RequiredArgsConstructor
  public enum Roles implements Role {

    COMPANY_MANAGER;

    @Id
    @Getter
    private final String id = name();

  }
}
