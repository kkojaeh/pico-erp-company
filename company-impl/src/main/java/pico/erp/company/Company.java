package pico.erp.company;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.audit.annotation.Audit;
import pico.erp.company.CompanyEvents.CreatedEvent;
import pico.erp.company.CompanyEvents.DeletedEvent;
import pico.erp.company.CompanyEvents.UpdatedEvent;
import pico.erp.company.CompanyMessages.CreateResponse;
import pico.erp.company.CompanyMessages.DeleteResponse;
import pico.erp.company.CompanyMessages.UpdateResponse;
import pico.erp.company.data.CompanyId;
import pico.erp.company.data.RegistrationNumber;

@ToString
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audit(alias = "company")
public class Company implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  CompanyId id;

  String name;

  RegistrationNumber registrationNumber;

  boolean supplier;

  boolean customer;

  boolean outsourcing;

  String representative;

  boolean enabled;

  public Company() {
  }

  public CreateResponse apply(CompanyMessages.CreateRequest request) {
    id = request.getId();
    name = request.getName();
    registrationNumber = request.getRegistrationNumber();
    supplier = request.isSupplier();
    customer = request.isCustomer();
    representative = request.getRepresentative();
    enabled = request.isEnabled();
    return new CreateResponse(
      Arrays.asList(new CreatedEvent(this.id))
    );
  }

  public UpdateResponse apply(CompanyMessages.UpdateRequest request) {
    name = request.getName();
    registrationNumber = request.getRegistrationNumber();
    supplier = request.isSupplier();
    customer = request.isCustomer();
    representative = request.getRepresentative();
    enabled = request.isEnabled();
    return new UpdateResponse(
      Arrays.asList(new UpdatedEvent(this.id))
    );
  }

  public DeleteResponse apply(CompanyMessages.DeleteRequest request) {
    return new DeleteResponse(
      Arrays.asList(new DeletedEvent(this.id))
    );
  }

}
