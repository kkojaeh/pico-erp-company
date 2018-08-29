package pico.erp.company.data;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.shared.data.Auditor;
import pico.erp.shared.data.Contact;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompanyContactView {

  CompanyContactId id;

  CompanyId companyId;

  String companyName;

  RegistrationNumber registrationNumber;

  Contact contact;

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

    String contactName;

    CompanyId companyId;

    Boolean enabled;

  }

}
