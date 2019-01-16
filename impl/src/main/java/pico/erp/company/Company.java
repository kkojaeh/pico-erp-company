package pico.erp.company;

import java.io.Serializable;
import java.util.Arrays;
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

  String conditionDescription;

  String itemDescription;

  boolean supplier;

  boolean customer;

  boolean outsourcing;

  String representative;

  boolean enabled;

  public Company() {
  }

  public CompanyMessages.CreateResponse apply(CompanyMessages.CreateRequest request) {
    id = request.getId();
    name = request.getName();
    registrationNumber = request.getRegistrationNumber();
    supplier = request.isSupplier();
    customer = request.isCustomer();
    outsourcing = request.isOutsourcing();
    representative = request.getRepresentative();
    conditionDescription = request.getConditionDescription();
    itemDescription = request.getItemDescription();
    enabled = request.isEnabled();
    return new CompanyMessages.CreateResponse(
      Arrays.asList(new CompanyEvents.CreatedEvent(this.id))
    );
  }

  public CompanyMessages.UpdateResponse apply(CompanyMessages.UpdateRequest request) {
    name = request.getName();
    registrationNumber = request.getRegistrationNumber();
    supplier = request.isSupplier();
    customer = request.isCustomer();
    outsourcing = request.isOutsourcing();
    representative = request.getRepresentative();
    conditionDescription = request.getConditionDescription();
    itemDescription = request.getItemDescription();
    enabled = request.isEnabled();
    return new CompanyMessages.UpdateResponse(
      Arrays.asList(new CompanyEvents.UpdatedEvent(this.id))
    );
  }

  public CompanyMessages.DeleteResponse apply(CompanyMessages.DeleteRequest request) {
    return new CompanyMessages.DeleteResponse(
      Arrays.asList(new CompanyEvents.DeletedEvent(this.id))
    );
  }

  public CompanyMessages.PrepareImportResponse apply(CompanyMessages.PrepareImportRequest request) {
    return new CompanyMessages.PrepareImportResponse(
      Collections.emptyList()
    );
  }

}
