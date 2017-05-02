package com.iis.soft.dao;

import com.iis.soft.models.DepWorker;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vladislav on 28-Apr-17.
 */
public class XmlDao {

    final static Logger logger = Logger.getLogger(XmlDao.class);

    /**
     * @param fileName - path to xml file
     * @return - Set of objects from file
     */
    public Set<DepWorker> readFromXml(String fileName) {
        Set<DepWorker> depWorkers = new HashSet<>();
        try {
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new File(fileName));
            logger.info("Open file: " + fileName);
            document.getDocumentElement().normalize();
            NodeList depWorkerEls = document.getElementsByTagName("depWorker");

            for (int i = 0; i < depWorkerEls.getLength(); i++) {
                Node item = depWorkerEls.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;
                    boolean add = depWorkers.add(new DepWorker(element.getElementsByTagName("depCode").item(0).getTextContent(),
                            element.getElementsByTagName("depJob").item(0).getTextContent(),
                            element.getElementsByTagName("depDescription").item(0).getTextContent()));
                    if (!add) {
                        logger.error("Same elements in file");
                        System.err.println("Same elements in file");
                    }
                }
            }

            logger.info("Read from file: " + fileName);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            logger.error("XmlDao read error: ", e);
        }
        return depWorkers;
    }


    /**
     * @param filename - path to xml file for writing
     * @param depWorkers - set of objects to import into file
     */
    public void writeToXml(String filename, Set<DepWorker> depWorkers) {
        logger.info("Start write to file: " + filename);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("company");
            document.appendChild(rootElement);

            for (DepWorker depWorker : depWorkers) {
                Element depWorkerElement = document.createElement("depWorker");
                rootElement.appendChild(depWorkerElement);
                Element depCode = document.createElement("depCode");
                Element depJob = document.createElement("depJob");
                Element depDescription = document.createElement("depDescription");
                depCode.appendChild(document.createTextNode(depWorker.getDepCode()));
                depJob.appendChild(document.createTextNode(depWorker.getDepJob()));
                depDescription.appendChild(document.createTextNode(depWorker.getDepDescription()));

                depWorkerElement.appendChild(depCode);
                depWorkerElement.appendChild(depJob);
                depWorkerElement.appendChild(depDescription);
            }

            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult(filename);

            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(domSource, streamResult);

            logger.info("Write to file: " + filename);
        } catch (ParserConfigurationException | TransformerException e) {
            logger.error("XmlDao write error: ", e);
        }
    }


}
