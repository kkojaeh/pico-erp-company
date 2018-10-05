package pico.erp.company

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
class CompanyServiceSpec extends Specification {

  @Autowired
  CompanyService companyService

  def setup() {
    companyService.create(new CompanyRequests.CreateRequest(id: CompanyId.from("ACE"), name: "에이스팩", registrationNumber: RegistrationNumber.from("1258199757")))
  }

  def "아이디로 존재하는 회사 확인"() {
    when:
    def exists = companyService.exists(CompanyId.from("ACE"))

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 회사 확인"() {
    when:
    def exists = companyService.exists(CompanyId.from("!ACE"))

    then:
    exists == false
  }

  def "아이디로 존재하는 회사를 조회"() {
    when:
    def company = companyService.get(CompanyId.from("ACE"))

    then:
    company.id.value == "ACE"
    company.name == "에이스팩"
  }

  def "아이디로 존재하지 않는 회사를 조회"() {
    when:
    companyService.get(CompanyId.from("!ACE"))

    then:
    thrown(CompanyExceptions.NotFoundException)
  }


  def "사업자번호(or DUNS No)로 존재하는 회사 확인"() {
    when:

    def exists = companyService.exists(RegistrationNumber.from("1258199757"))

    then:
    exists == true
  }

  def "사업자번호(or DUNS No)로 존재하지 않는 회사 확인"() {
    when:
    def exists = companyService.exists(RegistrationNumber.from("1111111111"))

    then:
    exists == false
  }

  def "사업자번호(or DUNS No)로 존재하는 회사를 조회"() {
    when:
    def company = companyService.get(RegistrationNumber.from("1258199757"))

    then:
    company.id.value == "ACE"
    company.name == "에이스팩"
  }

  def "사업자번호(or DUNS No)로 존재하지 않는 회사를 조회"() {
    when:
    companyService.get(RegistrationNumber.from("1111111111"))

    then:
    thrown(CompanyExceptions.NotFoundException)
  }


}
