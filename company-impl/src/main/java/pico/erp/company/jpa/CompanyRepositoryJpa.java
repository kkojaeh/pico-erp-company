package pico.erp.company.jpa;

import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.company.Company;
import pico.erp.company.CompanyRepository;
import pico.erp.company.data.CompanyId;
import pico.erp.company.data.RegistrationNumber;

@Repository
interface CompanyEntityRepository extends CrudRepository<CompanyEntity, CompanyId> {

  @Query("SELECT c FROM Company c WHERE c.registrationNumber = :registrationNumber")
  CompanyEntity findBy(@Param("registrationNumber") RegistrationNumber registrationNumber);

}

@Repository
@Transactional
public class CompanyRepositoryJpa implements CompanyRepository {

  @Autowired
  private CompanyJpaMapper mapper;

  @Autowired
  private CompanyEntityRepository repository;

  @Override
  public Company create(Company company) {
    val entity = mapper.map(company);
    val created = repository.save(entity);
    return mapper.map(created);
  }

  @Override
  public void deleteBy(CompanyId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(CompanyId id) {
    return repository.exists(id);
  }

  @Override
  public boolean exists(RegistrationNumber registrationNumber) {
    return repository.findBy(registrationNumber) != null;
  }

  @Override
  public Optional<Company> findBy(CompanyId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::map);
  }

  @Override
  public Optional<Company> findBy(RegistrationNumber registrationNumber) {
    return Optional
      .ofNullable(repository.findBy(registrationNumber))
      .map(mapper::map);
  }

  @Override
  public void update(Company company) {
    val entity = repository.findOne(company.getId());
    mapper.pass(mapper.map(company), entity);
    repository.save(entity);
  }

}
