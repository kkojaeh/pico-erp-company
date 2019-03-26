package pico.erp.company

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.contact.CompanyContactExceptions
import pico.erp.company.contact.CompanyContactId
import pico.erp.company.contact.CompanyContactRequests
import pico.erp.company.contact.CompanyContactService
import pico.erp.shared.TestParentApplication
import pico.erp.shared.data.Contact
import spock.lang.Specification

@SpringBootTest(classes = [CompanyApplication])
@SpringBootTestComponent(parent = TestParentApplication, siblings = [])
@ComponentScan(useDefaultFilters = false)
@Transactional
@Rollback
@ActiveProfiles("test")
class CompanyContactServiceSpec extends Specification {

  @Autowired
  CompanyContactService companyContactService


  def companyId = CompanyId.from("CUST1")

  def id = CompanyContactId.from("CUST1-1")

  def unknownId = CompanyContactId.from("unknown")

  def contact = new Contact(
    name: "고객 회사 담당자",
    email: "manager@company.com",
    telephoneNumber: "+821011111111",
    mobilePhoneNumber: "+821011111111",
    faxNumber: "+821011111111"
  )

  def setup() {
    companyContactService.create(
      new CompanyContactRequests.CreateRequest(
        id: id,
        companyId: companyId,
        contact: contact,
        enabled: true
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = companyContactService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = companyContactService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def companyContact = companyContactService.get(id)

    then:
    companyContact.id == id
    companyContact.companyId == companyId
    companyContact.contact.name == contact.name
    companyContact.contact.email == contact.email
    companyContact.contact.telephoneNumber == contact.telephoneNumber
    companyContact.contact.mobilePhoneNumber == contact.mobilePhoneNumber
    companyContact.contact.faxNumber == contact.faxNumber
    companyContact.enabled == true
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    companyContactService.get(unknownId)

    then:
    thrown(CompanyContactExceptions.NotFoundException)
  }

  def "수정 - 수정"() {
    when:
    def contact = new Contact(
      name: "고객 회사 담당자2",
      email: "manager2@company.com",
      telephoneNumber: "+821011111112",
      mobilePhoneNumber: "+821011111112",
      faxNumber: "+821011111112"
    )
    companyContactService.update(
      new CompanyContactRequests.UpdateRequest(
        id: id,
        contact: contact,
        enabled: false
      )
    )
    def companyContact = companyContactService.get(id)
    then:
    companyContact.id == id
    companyContact.companyId == companyId
    companyContact.contact.name == contact.name
    companyContact.contact.email == contact.email
    companyContact.contact.telephoneNumber == contact.telephoneNumber
    companyContact.contact.mobilePhoneNumber == contact.mobilePhoneNumber
    companyContact.contact.faxNumber == contact.faxNumber
    companyContact.enabled == false
  }


}
