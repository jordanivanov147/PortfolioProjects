package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.ProjectRootSeedDto;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.ValidationUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.workshop.constants.GlobalConstants.*;


@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CompanyService companyService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, CompanyService companyService) {
        this.projectRepository = projectRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.companyService = companyService;
    }

    @Override
    public void importProjects() throws JAXBException, FileNotFoundException {

        ProjectRootSeedDto projectRootSeedDto =
                this.xmlParser.unmarshalFromFile(PROJECTS_SEED_FILE_PATH, ProjectRootSeedDto.class);

        projectRootSeedDto.getProjects()
                .stream()
                .forEach(projectSeedDto -> {
                    if (this.validationUtil.isValid(projectSeedDto) &&
                    this.projectRepository.findByName(projectSeedDto.getName()) == null){

                        Project project = this.modelMapper.map(projectSeedDto, Project.class);
                        project.setCompany(this.companyService.getCompanyByName(projectSeedDto.getCompany().getName()));

                        this.projectRepository.saveAndFlush(project);
                    }
                });
    }

    @Override
    public boolean areImported() {
       return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
      return Files.readString(Path.of(PROJECTS_SEED_FILE_PATH));
    }

    @Override
    public String exportFinishedProjects(){
        StringBuilder resultMessage = new StringBuilder();

        this.projectRepository.findByFinishedIsTrue()
                .forEach(p -> {
                    resultMessage.append("Project name:" ).append(p.getName());
                    resultMessage.append("\n\tDescription: ").append(p.getDescription());
                    resultMessage.append("\n\t").append(p.getPayment());
                    resultMessage.append(System.lineSeparator());
                });


        return resultMessage.toString();
    }

    @Override
    public Project getProjectByName(String projectName) {
        return this.projectRepository.findByName(projectName);
    }
}
