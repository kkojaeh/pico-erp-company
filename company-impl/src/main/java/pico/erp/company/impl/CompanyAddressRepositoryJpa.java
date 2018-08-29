package pico.erp.company.impl;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.company.core.CompanyAddressRepository;
import pico.erp.company.data.CompanyAddressId;
import pico.erp.company.data.CompanyId;
import pico.erp.company.domain.CompanyAddress;
import pico.erp.company.impl.jpa.CompanyAddressEntity;

@Repository
interface CompanyAddressEntityRepository extends
  CrudRepository<CompanyAddressEntity, CompanyAddressId> {

  @Query("SELECT ca FROM CompanyAddress ca WHERE ca.company.id = :companyId")
  Stream<CompanyAddressEntity> findAllBy(@Param("companyId") CompanyId companyId);

}

@Repository
@Transactional
public class CompanyAddressRepositoryJpa implements CompanyAddressRepository {

  @Autowired
  private CompanyJpaMapper mapper;

  @Autowired
  private CompanyAddressEntityRepository repository;

  @Override
  public CompanyAddress create(CompanyAddress companyAddress) {
    val entity = mapper.map(companyAddress);
    val created = repository.save(entity);
    return mapper.map(created);
  }

  @Override
  public void deleteBy(CompanyAddressId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(CompanyAddressId id) {
    return repository.exists(id);
  }

  @Override
  public Stream<CompanyAddress> findAllBy(CompanyId companyId) {
    return repository.findAllBy(companyId)
      .map(mapper::map);
  }

  @Override
  public Optional<CompanyAddress> findBy(CompanyAddressId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::map);
  }

  @Override
  public void update(CompanyAddress companyAddress) {
    val entity = repository.findOne(companyAddress.getId());
    mapper.pass(mapper.map(companyAddress), entity);
    repository.save(entity);
  }
}
