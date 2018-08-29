package pico.erp.company.impl;

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
import pico.erp.company.CompanyContactQuery;
import pico.erp.company.data.CompanyContactView;
import pico.erp.company.data.CompanyId;
import pico.erp.company.impl.jpa.QCompanyContactEntity;
import pico.erp.shared.ExtendedLabeledValue;
import pico.erp.shared.LabeledValue;
import pico.erp.shared.Public;
import pico.erp.shared.data.LabeledValuable;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Public
@Transactional(readOnly = true)
@Validated
public class CompanyContactQueryJpa implements CompanyContactQuery {

  private final QCompanyContactEntity companyContact = QCompanyContactEntity.companyContactEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Override
  public List<? extends LabeledValuable> asLabels(CompanyId companyId, String keyword,
    long limit) {
    val query = new JPAQuery<LabeledValue>(entityManager);
    val select = Projections.bean(ExtendedLabeledValue.class,
      companyContact.id.value.as("value"),
      companyContact.contact.name.as("label"),
      companyContact.company.name.as("subLabel"),
      companyContact.contact.mobilePhoneNumber.as("stamp")
    );
    query.select(select);
    query.from(companyContact);

    val builder = new BooleanBuilder();
    builder.and(companyContact.company.id.eq(companyId));
    builder.and(companyContact.contact.name
      .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", keyword, "%")));
    query.where(builder);
    query.limit(limit);
    query.orderBy(companyContact.contact.name.asc());
    return query.fetch();
  }

  @Override
  public Page<CompanyContactView> retrieve(CompanyContactView.Filter filter, Pageable pageable) {
    val query = new JPAQuery<CompanyContactView>(entityManager);
    val select = Projections.bean(CompanyContactView.class,
      companyContact.id,
      companyContact.company.id.as("companyId"),
      companyContact.company.name.as("companyName"),
      companyContact.contact,
      companyContact.enabled,
      companyContact.createdBy,
      companyContact.createdDate,
      companyContact.lastModifiedBy,
      companyContact.lastModifiedDate
    );

    query.select(select);
    query.from(companyContact);
    query.join(companyContact.company);

    val builder = new BooleanBuilder();

    if (filter.getCompanyId() != null) {
      builder.and(companyContact.company.id.eq(filter.getCompanyId()));
    }

    if (!isEmpty(filter.getContactName())) {
      builder.and(
        companyContact.contact.name
          .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getContactName(), "%")));
    }
    if (filter.getEnabled() != null) {
      builder.and(companyContact.enabled.eq(filter.getEnabled()));
    }
    query.where(builder);

    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
