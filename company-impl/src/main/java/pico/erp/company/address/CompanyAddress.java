package pico.erp.company.address;

import java.io.Serializable;
import java.util.Collections;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.audit.annotation.Audit;
import pico.erp.company.Company;
import pico.erp.company.address.CompanyAddressMessages.CreateResponse;
import pico.erp.company.address.CompanyAddressMessages.DeleteResponse;
import pico.erp.company.address.CompanyAddressMessages.UpdateResponse;
import pico.erp.shared.data.Address;

@ToString
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audit(alias = "company-address")
public class CompanyAddress implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  CompanyAddressId id;

  Company company;

  String name;

  String telephoneNumber;

  String mobilePhoneNumber;

  boolean enabled;

  Address address;

  public CompanyAddress() {
  }

  public CreateResponse apply(
    CompanyAddressMessages.CreateRequest request) {
    id = request.getId();
    company = request.getCompany();
    name = request.getName();
    telephoneNumber = request.getTelephoneNumber();
    mobilePhoneNumber = request.getMobilePhoneNumber();
    enabled = request.isEnabled();
    address = request.getAddress();
    return new CreateResponse(
      Collections.emptyList()
    );
  }

  public UpdateResponse apply(
    CompanyAddressMessages.UpdateRequest request) {
    name = request.getName();
    telephoneNumber = request.getTelephoneNumber();
    mobilePhoneNumber = request.getMobilePhoneNumber();
    enabled = request.isEnabled();
    address = request.getAddress();
    return new UpdateResponse(
      Collections.emptyList()
    );
  }

  public DeleteResponse apply(
    CompanyAddressMessages.DeleteRequest request) {
    return new DeleteResponse(
      Collections.emptyList()
    );
  }

}
