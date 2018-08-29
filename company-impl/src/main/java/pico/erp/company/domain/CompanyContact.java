package pico.erp.company.domain;

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
import pico.erp.company.data.CompanyContactId;
import pico.erp.company.domain.CompanyContactMessages.CreateResponse;
import pico.erp.company.domain.CompanyContactMessages.DeleteResponse;
import pico.erp.company.domain.CompanyContactMessages.UpdateResponse;
import pico.erp.shared.data.Contact;

@ToString
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audit(alias = "company-contact")
public class CompanyContact implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  CompanyContactId id;

  Company company;

  Contact contact;

  boolean enabled;

  public CompanyContact() {
  }

  public CreateResponse apply(
    CompanyContactMessages.CreateRequest request) {
    id = request.getId();
    company = request.getCompany();
    contact = request.getContact();
    enabled = request.isEnabled();
    return new CreateResponse(
      Collections.emptyList()
    );
  }

  public UpdateResponse apply(
    CompanyContactMessages.UpdateRequest request) {
    contact = request.getContact();
    enabled = request.isEnabled();
    return new UpdateResponse(
      Collections.emptyList()
    );
  }

  public DeleteResponse apply(
    CompanyContactMessages.DeleteRequest request) {
    return new DeleteResponse(
      Collections.emptyList()
    );
  }

}
