package pico.erp.company

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.shared.TestParentApplication
import spock.lang.Specification

@SpringBootTest(classes = [CompanyApplication])
@SpringBootTestComponent(parent = TestParentApplication, siblings = [])
@ComponentScan(useDefaultFilters = false)
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
