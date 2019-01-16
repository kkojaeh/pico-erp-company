package pico.erp.company;

import java.io.Serializable;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class CompanyData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  CompanyId id;

  String name;

  RegistrationNumber registrationNumber;

  String conditionDescription;

  String itemDescription;

  boolean supplier;

  boolean customer;

  boolean outsourcing;

  String representative;

  boolean enabled;

}
