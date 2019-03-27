package pico.erp.company;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.shared.data.Auditor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompanyView {

  CompanyId id;

  String name;

  RegistrationNumber registrationNumber;

  boolean enabled;

  boolean supplier;

  boolean customer;

  boolean outsourcing;

  String representative;

  String conditionDescription;

  String itemDescription;

  Auditor createdBy;

  LocalDateTime createdDate;

  Auditor lastModifiedBy;

  LocalDateTime lastModifiedDate;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String name;

    Boolean enabled;

    Boolean customer;

    Boolean supplier;

    Boolean outsourcing;

  }

}
