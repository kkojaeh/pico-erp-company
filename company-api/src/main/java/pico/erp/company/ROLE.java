package pico.erp.company;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Role;

@RequiredArgsConstructor
public enum ROLE implements Role {

  COMPANY_MANAGER;

  @Id
  @Getter
  private final String id = name();

}
