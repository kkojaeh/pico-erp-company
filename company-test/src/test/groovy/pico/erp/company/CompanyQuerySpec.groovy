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

  def setup() {
    companyService.create(new CompanyRequests.CreateRequest(id: CompanyId.from("ACE"), name: "에이스팩", registrationNumber: RegistrationNumber.from("1258199757")))
    companyService.create(new CompanyRequests.CreateRequest(id: CompanyId.from("SSI"), name: "삼성전자", registrationNumber: RegistrationNumber.from("1248100998")))
  }

  @Autowired
  CompanyQuery companyQuery

  @Autowired
  CompanyService companyService

  def "사용자 조회 - 조회 조건에 맞게 조회"() {
    expect:
    def page = companyQuery.retrieve(condition, pageable)
    page.totalElements == totalElements

    where:
    condition                            | pageable               || totalElements
    new CompanyView.Filter(name: "에이스팩") | new PageRequest(0, 10) || 1
    new CompanyView.Filter(name: "삼성전자") | new PageRequest(0, 10) || 1
    new CompanyView.Filter(name: "없음")   | new PageRequest(0, 10) || 0
  }

}
