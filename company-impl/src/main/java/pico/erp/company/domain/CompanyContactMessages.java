package pico.erp.company.domain;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import pico.erp.company.data.CompanyContactId;
import pico.erp.shared.data.Contact;
import pico.erp.shared.event.Event;

public interface CompanyContactMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    CompanyContactId id;

    @Valid
    @NotNull
    Company company;

    @Valid
    @NotNull
    Contact contact;

    boolean enabled;
  }

  @Data
  class UpdateRequest {

    @Valid
    @NotNull
    Contact contact;

    boolean enabled;

  }

  @Data
  class DeleteRequest {

  }

  @Value
  class CreateResponse {

    Collection<Event> events;

  }

  @Value
  class UpdateResponse {

    Collection<Event> events;

  }

  @Value
  class DeleteResponse {

    Collection<Event> events;
  }
}
