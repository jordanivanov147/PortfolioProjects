package softuni.workshop.data.dtos;

import softuni.workshop.data.entities.Company;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyRootSeedDto {

    @XmlElement(name = "company")
    private List<CompanySeedDto> companies;

    public CompanyRootSeedDto() {
    }

    public List<CompanySeedDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanySeedDto> companies) {
        this.companies = companies;
    }
}
