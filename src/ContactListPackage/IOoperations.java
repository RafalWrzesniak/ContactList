package ContactListPackage;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

class IOoperations {

    static void writeContact(Contact newContact, Document doc) {

//        Document doc = openContactsDocument();
        Node rootElement = doc.getFirstChild();
        Element contact = doc.createElement("contact");
        try {
            contact.setAttribute("id", String.valueOf(createNewId(doc)));
        } catch (NullPointerException e) {
            contact.setAttribute("id", "0");
        }
        rootElement.appendChild(contact);


        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(newContact.getName()));
        contact.appendChild(name);

        Element surname = doc.createElement("surname");
        surname.appendChild(doc.createTextNode(newContact.getSurname()));
        contact.appendChild(surname);

        Element phone = doc.createElement("phone");
        phone.appendChild(doc.createTextNode(newContact.getPhone()));
        contact.appendChild(phone);

        Element note = doc.createElement("note");
        note.appendChild(doc.createTextNode(newContact.getNote()));
        contact.appendChild(note);

        // Output to console for testing
//            StreamResult consoleResult = new StreamResult(System.out);
//            transformer.transform(source, consoleResult);

    }

    static ObservableList<Contact> readContacts(String[] columnNames) {
        ObservableList<Contact> lista = FXCollections.observableArrayList();
        Document doc = openContactsDocument();
        doc.getDocumentElement().normalize();
        NodeList xmlList = doc.getElementsByTagName("contact");
        for (int i = 0; i < xmlList.getLength(); i++){
            Node contactNode = xmlList.item(i);
            if (contactNode.getNodeType() == Node.ELEMENT_NODE) {
                Element contactNodeElement = (Element) contactNode;
                String nodeContName = contactNodeElement.getElementsByTagName(columnNames[0]).item(0).getTextContent();
                String nodeContSurname = contactNodeElement.getElementsByTagName(columnNames[1]).item(0).getTextContent();
                String nodeContPhone = contactNodeElement.getElementsByTagName(columnNames[2]).item(0).getTextContent();
                String nodeContNote = contactNodeElement.getElementsByTagName(columnNames[3]).item(0).getTextContent();
                String nodeContId = contactNodeElement.getAttribute("id");
                lista.add(new Contact(nodeContName, nodeContSurname, nodeContPhone, nodeContNote, nodeContId));
            }
        }
        return lista;
    }

    private static int createNewId(Document doc) {
        Node rootElement = doc.getFirstChild();
        return Integer.parseInt(rootElement.getLastChild().getAttributes().getNamedItem("id").getNodeValue()) + 1;
    }


    static void saveAllToXml(ObservableList<Contact> lista) {
        File oldFile = new File("contacts.xml");
        oldFile.delete();
        Document doc = openContactsDocument();
        for (Contact contactToSave: lista) {
            writeContact(contactToSave, doc);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
              transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("contacts.xml"));
            transformer.transform(source, result);

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static Document openContactsDocument(){
        Document doc;
        try {
            File inputFile = new File("contacts.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
        } catch (IOException | ParserConfigurationException | SAXException e){
//            System.out.println("Failed to open contacts file. Creating new one.");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            }
            assert dBuilder != null;
            doc = dBuilder.newDocument();
            Node rootElement = doc.createElement("contacts");
            doc.appendChild(rootElement);
        }

        return doc;
    }
}
