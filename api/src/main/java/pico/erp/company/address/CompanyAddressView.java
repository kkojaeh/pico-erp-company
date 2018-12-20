package pico.erp.company.address;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.shared.data.Address;
import pico.erp.shared.data.Auditor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompanyAddressView {

  CompanyAddressId id;

  CompanyId companyId;

  String companyName;

  String name;

  String telephoneNumber;

  String mobilePhoneNumber;

  Address address;

  boolean enabled;

  Auditor createdBy;

  OffsetDateTime createdDate;

  Auditor lastModifiedBy;

  OffsetDateTime lastModifiedDate;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String name;

    CompanyId companyId;

    Boolean enabled;

  }

}
