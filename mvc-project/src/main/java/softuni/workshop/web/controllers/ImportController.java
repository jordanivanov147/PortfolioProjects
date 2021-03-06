package softuni.workshop.web.controllers;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @Autowired
    public ImportController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/xml")
    public ModelAndView xmls() {

        ModelAndView modelAndView = new ModelAndView("xml/import-xml");
        boolean[] areImported = new boolean[]{
                this.companyService.areImported(),
                this.projectService.areImported(),
                this.employeeService.areImported()
        };
        modelAndView.addObject("areImported", areImported);
        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView companies() {

        ModelAndView modelAndView = new ModelAndView("xml/import-companies");
        try {
            modelAndView.addObject("companies", this.companyService.readCompaniesXmlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @PostMapping("/companies")
    public ModelAndView companiesConfirm() {

        try {
            this.companyService.importCompanies();
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return super.redirect("/import/xml");
    }

    @GetMapping("/employees")
    public ModelAndView employees() {

        ModelAndView modelAndView = new ModelAndView("xml/import-employees");
        try {
            modelAndView.addObject("employees", this.employeeService.readEmployeesXmlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @PostMapping("/employees")
    public ModelAndView employeesConfirm(){

        try {
            this.employeeService.importEmployees();
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return super.redirect("/import/xml");
    }

    @GetMapping("/projects")
    public ModelAndView projects() {

        ModelAndView modelAndView = new ModelAndView("xml/import-projects");
        try {
            modelAndView.addObject("projects", this.projectService.readProjectsXmlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @PostMapping("/projects")
    public ModelAndView projectsConfirm() {

        try {
            this.projectService.importProjects();
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return super.redirect("/import/xml");
    }
}
