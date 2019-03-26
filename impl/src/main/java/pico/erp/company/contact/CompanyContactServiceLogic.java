package pico.erp.company.contact;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.company.CompanyId;
import pico.erp.company.contact.CompanyContactExceptions.AlreadyExistsException;
import pico.erp.company.contact.CompanyContactExceptions.NotFoundException;
import pico.erp.company.contact.CompanyContactRequests.CreateRequest;
import pico.erp.company.contact.CompanyContactRequests.DeleteRequest;
import pico.erp.company.contact.CompanyContactRequests.UpdateRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class CompanyContactServiceLogic implements CompanyContactService {

  @Autowired
  private CompanyContactRepository companyContactRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private CompanyContactMapper mapper;

  @Override
  public CompanyContactData create(CreateRequest request) {
    if (companyContactRepository.exists(request.getId())) {
      throw new AlreadyExistsException();
    }
    val companyContact = new CompanyContact();
    val response = companyContact.apply(mapper.map(request));
    val created = companyContactRepository.create(companyContact);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val companyContact = companyContactRepository.findBy(request.getId())
      .orElseThrow(NotFoundException::new);
    val response = companyContact.apply(mapper.map(request));
    companyContactRepository.deleteBy(request.getId());
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(CompanyContactId id) {
    return companyContactRepository.exists(id);
  }

  @Override
  public CompanyContactData get(CompanyContactId id) {
    return companyContactRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(NotFoundException::new);
  }

  @Override
  public List<CompanyContactData> getAll(CompanyId companyId) {
    return companyContactRepository.findAllBy(companyId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(UpdateRequest request) {
    val companyContact = companyContactRepository.findBy(request.getId())
      .orElseThrow(NotFoundException::new);
    val response = companyContact.apply(mapper.map(request));
    companyContactRepository.update(companyContact);
    eventPublisher.publishEvents(response.getEvents());
  }
}
