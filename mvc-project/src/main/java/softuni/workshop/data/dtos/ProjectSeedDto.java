package softuni.workshop.data.dtos;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectSeedDto {

    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement(name = "is-finished")
    private boolean isFinished;
    @XmlElement
    private BigDecimal payment;
    @XmlElement(name = "start-date")
    private String startDate;
    @XmlElement
    private CompanySeedDto company;

    public ProjectSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public CompanySeedDto getCompany() {
        return company;
    }

    public void setCompany(CompanySeedDto company) {
        this.company = company;
    }
}
