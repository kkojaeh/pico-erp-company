package pico.erp.company.contact;

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
import pico.erp.company.Company;
import pico.erp.shared.data.Contact;

@ToString
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyContact implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  CompanyContactId id;

  Company company;

  Contact contact;

  boolean enabled;

  public CompanyContact() {
  }

  public CompanyContactMessages.CreateResponse apply(
    CompanyContactMessages.CreateRequest request) {
    id = request.getId();
    company = request.getCompany();
    contact = request.getContact();
    enabled = request.isEnabled();
    return new CompanyContactMessages.CreateResponse(
      Collections.emptyList()
    );
  }

  public CompanyContactMessages.UpdateResponse apply(
    CompanyContactMessages.UpdateRequest request) {
    contact = request.getContact();
    enabled = request.isEnabled();
    return new CompanyContactMessages.UpdateResponse(
      Collections.emptyList()
    );
  }

  public CompanyContactMessages.DeleteResponse apply(
    CompanyContactMessages.DeleteRequest request) {
    return new CompanyContactMessages.DeleteResponse(
      Collections.emptyList()
    );
  }

  public CompanyContactMessages.PrepareImportResponse apply(
    CompanyContactMessages.PrepareImportRequest request) {
    return new CompanyContactMessages.PrepareImportResponse(
      Collections.emptyList()
    );
  }

}
