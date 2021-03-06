package pico.erp.company.address;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.ComponentBean;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.company.CompanyId;
import pico.erp.company.address.CompanyAddressExceptions.CompanyAddressAlreadyExistsException;
import pico.erp.company.address.CompanyAddressExceptions.CompanyAddressNotFoundException;
import pico.erp.company.address.CompanyAddressRequests.CreateRequest;
import pico.erp.company.address.CompanyAddressRequests.DeleteRequest;
import pico.erp.company.address.CompanyAddressRequests.UpdateRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@ComponentBean
@Transactional
@Validated
public class CompanyAddressServiceLogic implements CompanyAddressService {

  @Autowired
  private CompanyAddressRepository companyAddressRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private CompanyAddressMapper mapper;

  @Override
  public CompanyAddressData create(CreateRequest request) {
    if (companyAddressRepository.exists(request.getId())) {
      throw new CompanyAddressAlreadyExistsException();
    }
    val companyAddress = new CompanyAddress();
    val response = companyAddress.apply(mapper.map(request));
    val created = companyAddressRepository.create(companyAddress);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val companyAddress = companyAddressRepository.findBy(request.getId())
      .orElseThrow(CompanyAddressNotFoundException::new);
    val response = companyAddress.apply(mapper.map(request));
    companyAddressRepository.deleteBy(request.getId());
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(CompanyAddressId id) {
    return companyAddressRepository.exists(id);
  }

  @Override
  public CompanyAddressData get(CompanyAddressId id) {
    return companyAddressRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(CompanyAddressNotFoundException::new);
  }

  @Override
  public List<CompanyAddressData> getAll(CompanyId companyId) {
    return companyAddressRepository.findAllBy(companyId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(UpdateRequest request) {
    val companyAddress = companyAddressRepository.findBy(request.getId())
      .orElseThrow(CompanyAddressNotFoundException::new);
    val response = companyAddress.apply(mapper.map(request));
    companyAddressRepository.update(companyAddress);
    eventPublisher.publishEvents(response.getEvents());
  }
}
