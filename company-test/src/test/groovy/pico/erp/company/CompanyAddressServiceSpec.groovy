package pico.erp.company

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.address.CompanyAddressExceptions
import pico.erp.company.address.CompanyAddressRequests
import pico.erp.company.address.CompanyAddressService
import pico.erp.company.address.data.CompanyAddressId
import pico.erp.company.data.CompanyId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.shared.data.Address
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
class CompanyAddressServiceSpec extends Specification {

  @Autowired
  CompanyAddressService companyAddressService

  def setup() {
    companyAddressService.create(
      new CompanyAddressRequests.CreateRequest(
        id: CompanyAddressId.from("CUST1-1"),
        companyId: CompanyId.from("CUST1"),
        name: "본사",
        telephoneNumber: "+821011111111",
        mobilePhoneNumber: "+821011111112",
        address: new Address(
          postalCode: '13496',
          street: '경기도 성남시 분당구 장미로 42',
          detail: '야탑리더스 410호'
        ),
        enabled: true
      )
    )
  }

  def "아이디로 존재하는 회사 배송지 확인"() {
    when:
    def exists = companyAddressService.exists(CompanyAddressId.from("CUST1-1"))

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 회사 배송지 확인"() {
    when:
    def exists = companyAddressService.exists(CompanyAddressId.from("!CUST1-1"))

    then:
    exists == false
  }

  def "아이디로 존재하는 회사 배송지 를 조회"() {
    when:
    def company = companyAddressService.get(CompanyAddressId.from("CUST1-1"))

    then:
    company.id.value == "CUST1-1"
    company.telephoneNumber == "+821011111111"
    company.mobilePhoneNumber == "+821011111112"
  }

  def "아이디로 존재하지 않는 회사 배송지를 조회"() {
    when:
    companyAddressService.get(CompanyAddressId.from("!CUST1-1"))

    then:
    thrown(CompanyAddressExceptions.CompanyAddressNotFoundException)
  }


}
