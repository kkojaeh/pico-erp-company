package pico.erp.company.data;

import java.io.Serializable;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.shared.data.Contact;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class CompanyContactData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  CompanyContactId id;

  CompanyId companyId;

  Contact contact;

  boolean enabled;


}
