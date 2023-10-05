package Proyecto.Util;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import Proyecto.data.Configuration;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class PathUtils {

    public static Configuration loadConfiguration(String filePath) throws JAXBException {
        File configFile = new File(filePath);
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Configuration) unmarshaller.unmarshal(configFile);
    }

    public static void saveConfiguration(String filePath, Configuration configuration) throws JAXBException {
        File configFile = new File(filePath);
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(configuration, configFile);
    }

}

