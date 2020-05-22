package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.EmployeeRootSeedDto;
import softuni.workshop.data.entities.Employee;
import softuni.workshop.data.repositories.EmployeeRepository;
import softuni.workshop.service.services.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final ProjectService projectService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, ProjectService projectService) {
        this.employeeRepository = employeeRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.projectService = projectService;
    }


    @Override
    public void importEmployees() throws JAXBException, FileNotFoundException {

        EmployeeRootSeedDto employeeRootSeedDto =
                this.xmlParser.unmarshalFromFile(EMPLOYEES_SEED_FILE_PATH, EmployeeRootSeedDto.class);


        employeeRootSeedDto.getEmployees()
                .stream().
        forEach(employeeSeedDto -> {
            if (this.validationUtil.isValid(employeeSeedDto) &&
            this.employeeRepository.findByFirstNameAndLastName(employeeSeedDto.getFirstName(),employeeSeedDto.getLastName()) == null){

                Employee employee = this.modelMapper.map(employeeSeedDto, Employee.class);
                employee.setProject(this.projectService.getProjectByName(employeeSeedDto.getProject().getName()));

                this.employeeRepository.saveAndFlush(employee);
            }
        });

    }

    @Override
    public boolean areImported() {
       return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEES_SEED_FILE_PATH));
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        StringBuilder resultMessage = new StringBuilder();

        this.employeeRepository.findByAgeGreaterThan(25)
                .forEach(e -> {
                    resultMessage.append("Name: ").append(e.getFirstName()).append(" ").append(e.getLastName());
                    resultMessage.append("\n\tAge:").append(e.getAge());
                    resultMessage.append("\n\tProject name: ").append(e.getProject().getName());
                    resultMessage.append(System.lineSeparator());
                });

        return resultMessage.toString();
    }
}
