package pico.erp.company

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.data.CompanyContactId
import pico.erp.company.data.CompanyId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.shared.data.Contact
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
class CompanyContactServiceSpec extends Specification {

  @Autowired
  CompanyContactService companyContactService

  def setup() {
    companyContactService.create(
      new CompanyContactRequests.CreateRequest(
        id: CompanyContactId.from("CUST1-1"),
        companyId: CompanyId.from("CUST1"),
        contact: new Contact(name: "고객 회사 담당자", email: "manager@company.com", telephoneNumber: "+821011111111", mobilePhoneNumber: "+821011111111", faxNumber: "+821011111111"),
        enabled: true
      )
    )
  }

  def "아이디로 존재하는 회사 연락처 확인"() {
    when:
    def exists = companyContactService.exists(CompanyContactId.from("CUST1-1"))

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 회사 연락처 확인"() {
    when:
    def exists = companyContactService.exists(CompanyContactId.from("!CUST1-1"))

    then:
    exists == false
  }

  def "아이디로 존재하는 회사 연락처 를 조회"() {
    when:
    def company = companyContactService.get(CompanyContactId.from("CUST1-1"))

    then:
    company.id.value == "CUST1-1"
    company.contact.name == "고객 회사 담당자"
  }

  def "아이디로 존재하지 않는 회사 연락처를 조회"() {
    when:
    companyContactService.get(CompanyContactId.from("!CUST1-1"))

    then:
    thrown(CompanyContactExceptions.NotFoundException)
  }


}
