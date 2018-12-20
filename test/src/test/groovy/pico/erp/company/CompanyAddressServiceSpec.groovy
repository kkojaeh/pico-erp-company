package pico.erp.company

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.address.CompanyAddressExceptions
import pico.erp.company.address.CompanyAddressId
import pico.erp.company.address.CompanyAddressRequests
import pico.erp.company.address.CompanyAddressService
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

  def id = CompanyAddressId.from("CUST1-1")

  def unknownId = CompanyAddressId.from("unknown")

  def companyId = CompanyId.from("CUST1")

  def name = "본사"

  def telephoneNumber = "+821011111111"

  def mobilePhoneNumber = "+821011111112"

  def address = new Address(
    postalCode: '13496',
    street: '경기도 성남시 분당구 장미로 42',
    detail: '야탑리더스 410호'
  )

  def setup() {
    companyAddressService.create(
      new CompanyAddressRequests.CreateRequest(
        id: id,
        companyId: companyId,
        name: name,
        telephoneNumber: telephoneNumber,
        mobilePhoneNumber: mobilePhoneNumber,
        address: address,
        enabled: true
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = companyAddressService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = companyAddressService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def companyAddress = companyAddressService.get(id)

    then:
    companyAddress.id == id
    companyAddress.companyId == companyId
    companyAddress.name == name
    companyAddress.telephoneNumber == telephoneNumber
    companyAddress.mobilePhoneNumber == mobilePhoneNumber
    companyAddress.address.postalCode == address.postalCode
    companyAddress.address.street == address.street
    companyAddress.address.detail == address.detail
    companyAddress.enabled == true
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    companyAddressService.get(unknownId)

    then:
    thrown(CompanyAddressExceptions.CompanyAddressNotFoundException)
  }

  def "수정 - 수정"() {
    when:
    def name = "안성공장"
    def telephoneNumber = "+821011111113"
    def mobilePhoneNumber = "+821011111114"
    def address = new Address(
      postalCode: '13497',
      street: '경기도 성남시 분당구 장미로 43',
      detail: '야탑리더스 411호'
    )
    companyAddressService.update(
      new CompanyAddressRequests.UpdateRequest(
        id: id,
        name: name,
        telephoneNumber: telephoneNumber,
        mobilePhoneNumber: mobilePhoneNumber,
        address: address,
        enabled: true
      )
    )
    def companyAddress = companyAddressService.get(id)
    then:
    companyAddress.id == id
    companyAddress.companyId == companyId
    companyAddress.name == name
    companyAddress.telephoneNumber == telephoneNumber
    companyAddress.mobilePhoneNumber == mobilePhoneNumber
    companyAddress.address.postalCode == address.postalCode
    companyAddress.address.street == address.street
    companyAddress.address.detail == address.detail
    companyAddress.enabled == true
  }


}
