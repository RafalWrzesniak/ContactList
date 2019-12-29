package ContactListPackage;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class IOoperations {


    public static void writeData (Contact newContact) {


        System.out.println(Main.mainController.lista.get(0).getName());

        try {
            File inputFile = new File("contacts.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;
            Node rootElement;
            try {
                doc = dBuilder.parse(inputFile);
                rootElement = doc.getFirstChild();
            } catch (IOException e) {
                doc = dBuilder.newDocument();
                rootElement = doc.createElement("contacts");
                doc.appendChild(rootElement);
            }
            System.out.println();
            // contact element
            Element contact = doc.createElement("contact");
            try {
                contact.setAttribute("id", String.valueOf(Integer.parseInt(rootElement.getLastChild().getAttributes().getNamedItem("id").getNodeValue()) + 1));
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

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("contacts.xml"));
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }







    }




}
