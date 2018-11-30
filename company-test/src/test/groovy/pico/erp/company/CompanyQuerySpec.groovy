package pico.erp.company

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
class CompanyQuerySpec extends Specification {

  @Autowired
  CompanyQuery companyQuery

  def "사용자 조회 - 조회 조건에 맞게 조회"() {
    expect:
    def page = companyQuery.retrieve(condition, pageable)
    page.totalElements == totalElements

    where:
    condition                           | pageable               || totalElements
    new CompanyView.Filter(name: "고객사") | new PageRequest(0, 10) || 2
    new CompanyView.Filter(name: "공급사") | new PageRequest(0, 10) || 2
    new CompanyView.Filter(name: "없음")  | new PageRequest(0, 10) || 0
  }

}
