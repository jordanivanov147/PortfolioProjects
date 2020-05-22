package softuni.workshop.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public interface XmlParser {

    public <T> T unmarshalFromFile(String filePath, Class<T> tClass) throws JAXBException, FileNotFoundException;

    public <T> void marshalToFile(String filePath, T rootDto) throws JAXBException;
 }
