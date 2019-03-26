package pico.erp.company

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
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
class CompanyServiceSpec extends Specification {

  @Autowired
  CompanyService companyService

  def id = CompanyId.from("test")

  def unknownId = CompanyId.from("unknown")

  def name = "테스트사"

  def registrationNumber = RegistrationNumber.from("1258199757")

  def unknownRegistrationNumber = RegistrationNumber.from("unknown")

  def representative = "대표자"

  def conditionDescription = "제조"

  def itemDescription = "금형,알미늄,합성수지,포장재"

  def setup() {
    companyService.create(
      new CompanyRequests.CreateRequest(
        id: id,
        name: name,
        registrationNumber: registrationNumber,
        representative: representative,
        supplier: false,
        customer: true,
        outsourcing: false,
        enabled: true,
        conditionDescription: conditionDescription,
        itemDescription: itemDescription
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = companyService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = companyService.exists(unknownId)

    then:
    exists == false
  }

  def "존재 - 사업자번호(or DUNS No)로 확인"() {
    when:

    def exists = companyService.exists(registrationNumber)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 사업자번호(or DUNS No)로 확인"() {
    when:
    def exists = companyService.exists(unknownRegistrationNumber)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def company = companyService.get(id)

    then:
    company.id == id
    company.name == name
    company.registrationNumber == registrationNumber
    company.representative == representative
    company.supplier == false
    company.customer == true
    company.outsourcing == false
    company.enabled == true
    company.conditionDescription == conditionDescription
    company.itemDescription == itemDescription
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    companyService.get(unknownId)

    then:
    thrown(CompanyExceptions.NotFoundException)
  }

  def "조회 - 사업자번호(or DUNS No)로 조회"() {
    when:
    def company = companyService.get(registrationNumber)

    then:
    company.id == id
    company.name == name
    company.registrationNumber == registrationNumber
    company.representative == representative
    company.supplier == false
    company.customer == true
    company.outsourcing == false
    company.enabled == true
  }

  def "조회 - 존재하지 않는 사업자번호(or DUNS No)로 조회"() {
    when:
    companyService.get(unknownRegistrationNumber)

    then:
    thrown(CompanyExceptions.NotFoundException)
  }

  def "생성 - 동일한 사업자번호(or DUNS No)로 생성"() {
    when:
    def id = CompanyId.from("test-2")

    companyService.create(
      new CompanyRequests.CreateRequest(
        id: id,
        name: name,
        registrationNumber: registrationNumber,
        representative: representative,
        supplier: false,
        customer: true,
        outsourcing: false,
        enabled: true
      )
    )
    then:
    thrown(CompanyExceptions.RegistrationNumberAlreadyExistsException)
  }

  def "수정 - 수정"() {
    when:

    def name = "Test Corp"
    def representative = "Owner"
    def registrationNumber = RegistrationNumber.from("1258199759")
    companyService.update(
      new CompanyRequests.UpdateRequest(
        id: id,
        name: name,
        registrationNumber: registrationNumber,
        representative: representative,
        supplier: true,
        customer: false,
        outsourcing: true,
        enabled: false
      )
    )

    def company = companyService.get(id)

    then:
    company.id == id
    company.name == name
    company.registrationNumber == registrationNumber
    company.representative == representative
    company.supplier == true
    company.customer == false
    company.outsourcing == true
    company.enabled == false
  }

  def "수정 - 동일한 사업자번호(or DUNS No)로 수정"() {
    when:
    def registrationNumber = RegistrationNumber.from("1258199759")
    companyService.create(
      new CompanyRequests.CreateRequest(
        id: CompanyId.from("test-2"),
        name: name,
        registrationNumber: registrationNumber,
        representative: representative,
        supplier: false,
        customer: true,
        outsourcing: false,
        enabled: true
      )
    )
    companyService.update(
      new CompanyRequests.UpdateRequest(
        id: id,
        name: name,
        registrationNumber: registrationNumber,
        representative: representative,
        supplier: true,
        customer: false,
        outsourcing: true,
        enabled: false
      )
    )

    then:
    thrown(CompanyExceptions.RegistrationNumberAlreadyExistsException)
  }


}
