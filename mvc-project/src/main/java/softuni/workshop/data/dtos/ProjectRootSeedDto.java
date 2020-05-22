package softuni.workshop.data.dtos;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectRootSeedDto {

    @XmlElement(name = "project")
    private List<ProjectSeedDto> projects;

    public ProjectRootSeedDto() {
    }

    public List<ProjectSeedDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectSeedDto> projects) {
        this.projects = projects;
    }
}
