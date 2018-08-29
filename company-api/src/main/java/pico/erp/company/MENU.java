package pico.erp.company;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pico.erp.shared.data.Menu;
import pico.erp.shared.data.MenuCategory;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MENU implements Menu {

  COMPANY_MANAGEMENT("/company", "fas fa-building", MenuCategory.SETTINGS);

  String url;

  String icon;

  MenuCategory category;

  public String getId() {
    return name();
  }

}
