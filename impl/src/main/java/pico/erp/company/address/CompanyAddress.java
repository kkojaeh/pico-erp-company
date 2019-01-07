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

  String faxNumber;

  boolean enabled;

  Address address;

  boolean represented;

  public CompanyAddress() {
  }

  public CompanyAddressMessages.CreateResponse apply(
    CompanyAddressMessages.CreateRequest request) {
    id = request.getId();
    company = request.getCompany();
    name = request.getName();
    telephoneNumber = request.getTelephoneNumber();
    faxNumber = request.getFaxNumber();
    enabled = request.isEnabled();
    address = request.getAddress();
    represented = request.isRepresented();
    return new CompanyAddressMessages.CreateResponse(
      Collections.emptyList()
    );
  }

  public CompanyAddressMessages.UpdateResponse apply(
    CompanyAddressMessages.UpdateRequest request) {
    name = request.getName();
    telephoneNumber = request.getTelephoneNumber();
    faxNumber = request.getFaxNumber();
    enabled = request.isEnabled();
    address = request.getAddress();
    represented = request.isRepresented();
    return new CompanyAddressMessages.UpdateResponse(
      Collections.emptyList()
    );
  }

  public CompanyAddressMessages.DeleteResponse apply(
    CompanyAddressMessages.DeleteRequest request) {
    return new CompanyAddressMessages.DeleteResponse(
      Collections.emptyList()
    );
  }

  public CompanyAddressMessages.PrepareImportResponse apply(
    CompanyAddressMessages.PrepareImportRequest request) {
    return new CompanyAddressMessages.PrepareImportResponse(
      Collections.emptyList()
    );
  }

}
