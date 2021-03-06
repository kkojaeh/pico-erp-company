package pico.erp.company.address;

import java.io.Serializable;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.company.CompanyId;
import pico.erp.shared.data.Address;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class CompanyAddressData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  CompanyAddressId id;

  CompanyId companyId;

  String name;

  String telephoneNumber;

  String faxNumber;

  Address address;

  boolean represented;

  boolean enabled;


}
