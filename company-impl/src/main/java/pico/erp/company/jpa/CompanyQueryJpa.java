package pico.erp.company.jpa;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.company.CompanyQuery;
import pico.erp.company.data.CompanyView;
import pico.erp.shared.ExtendedLabeledValue;
import pico.erp.shared.LabeledValue;
import pico.erp.shared.Public;
import pico.erp.shared.data.LabeledValuable;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Public
@Transactional(readOnly = true)
@Validated
public class CompanyQueryJpa implements CompanyQuery {

  private final QCompanyEntity company = QCompanyEntity.companyEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Override
  public List<? extends LabeledValuable> asLabels(String keyword, long limit) {
    val query = new JPAQuery<LabeledValue>(entityManager);
    val select = Projections.bean(ExtendedLabeledValue.class,
      company.id.value.as("value"),
      company.name.as("label"),
      company.registrationNumber.value.as("subLabel"),
      company.representative.as("stamp")
    );
    query.select(select);
    query.from(company);
    query
      .where(company.name.likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", keyword, "%"))
        .and(company.enabled.eq(true)));
    query.limit(limit);
    query.orderBy(company.name.asc());
    return query.fetch();
  }

  @Override
  public Page<CompanyView> retrieve(CompanyView.Filter filter, Pageable pageable) {
    val query = new JPAQuery<CompanyView>(entityManager);
    val select = Projections.bean(CompanyView.class,
      company.id,
      company.name,
      company.registrationNumber,
      company.enabled,
      company.supplier,
      company.customer,
      company.outsourcing,
      company.representative,
      company.createdBy,
      company.createdDate,
      company.lastModifiedBy,
      company.lastModifiedDate
    );

    query.select(select);
    query.from(company);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getName())) {
      builder.and(
        company.name
          .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getName(), "%")));
    }
    if (filter.getEnabled() != null) {
      builder.and(company.enabled.eq(filter.getEnabled()));
    }
    query.where(builder);

    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
