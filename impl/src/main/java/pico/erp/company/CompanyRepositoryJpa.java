package pico.erp.company;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface CompanyEntityRepository extends CrudRepository<CompanyEntity, CompanyId> {

  @Query("SELECT c FROM Company c WHERE c.registrationNumber = :registrationNumber")
  CompanyEntity findBy(@Param("registrationNumber") RegistrationNumber registrationNumber);

  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c WHERE c.registrationNumber = :registrationNumber")
  boolean exists(@Param("registrationNumber") RegistrationNumber registrationNumber);

}

@Repository
@Transactional
public class CompanyRepositoryJpa implements CompanyRepository {

  @Autowired
  private CompanyMapper mapper;

  @Autowired
  private CompanyEntityRepository repository;

  @Override
  public Company create(Company company) {
    val entity = mapper.jpa(company);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(CompanyId id) {
    repository.deleteById(id);
  }

  @Override
  public boolean exists(CompanyId id) {
    return repository.existsById(id);
  }

  @Override
  public boolean exists(RegistrationNumber registrationNumber) {
    return repository.exists(registrationNumber);
  }

  @Override
  public Optional<Company> findBy(CompanyId id) {
    return repository.findById(id)
      .map(mapper::jpa);
  }

  @Override
  public Optional<Company> findBy(RegistrationNumber registrationNumber) {
    return Optional
      .ofNullable(repository.findBy(registrationNumber))
      .map(mapper::jpa);
  }

  @Override
  public Stream<Company> getAll() {
    return StreamSupport.stream(
      repository.findAll().spliterator(), false
    ).map(mapper::jpa);
  }

  @Override
  public void update(Company company) {
    val entity = repository.findById(company.getId()).get();
    mapper.pass(mapper.jpa(company), entity);
    repository.save(entity);
  }

}
