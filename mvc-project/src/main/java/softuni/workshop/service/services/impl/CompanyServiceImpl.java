package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.constants.GlobalConstants;
import softuni.workshop.data.dtos.CompanyRootSeedDto;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.util.ValidationUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.workshop.constants.GlobalConstants.*;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void importCompanies() throws JAXBException, FileNotFoundException {

        CompanyRootSeedDto companyRootSeedDto =
                this.xmlParser.unmarshalFromFile(COMPANIES_SEED_FILE_PATH, CompanyRootSeedDto.class);

        companyRootSeedDto.getCompanies().stream()
                .forEach(companySeedDto -> {

                    if (this.validationUtil.isValid(companyRootSeedDto) &&
                    this.companyRepository.findByName(companySeedDto.getName()) == null){

                        Company company =  this.modelMapper.map(companySeedDto, Company.class);

                        this.companyRepository.saveAndFlush(company);
                    }
                });
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        return Files.readString(Path.of(COMPANIES_SEED_FILE_PATH));
    }

    @Override
    public Company getCompanyByName(String name) {
        return this.companyRepository.findByName(name);
    }

}
