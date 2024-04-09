package com.example.app;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RssParser {

    private final String name;
    String delimiter = "item";

    URL url;

    public RssParser(String url, String name) {
        try {
            this.url = URI.create(url).toURL();
            this.name = name;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void read() throws Exception {

        String description = "";
        String title = "";
        String link = "";
        String language = "";
        String copyright = "";
        String author = "";
        String pubdate = "";
        String guid = "";

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = openUrl();
        XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(in);

        while (xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                delimiter = "http://www.w3.org/2005/Atom".equals(startElement.getNamespaceURI("")) ? "entry" : delimiter;
                String localPart = xmlEvent.asStartElement().getName()
                        .getLocalPart();
                switch (localPart) {

                    case "item":
                        break;
                    case "title":
                        title = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "description":
                        description = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "link":
                        link = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "guid":
                        guid = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "language":
                        break;
                    case "author":
                        author = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "pubDate":
                        break;
                    case "copyright":
                        break;
                }


            } else if (xmlEvent.isEndElement()) {
//                if (xmlEvent.asEndElement().getName().getLocalPart() == "item") {
                if (xmlEvent.asEndElement().getName().getLocalPart() == delimiter) {
                    FeedMessage feedMessage = new FeedMessage(author, description, guid, link, title);
                    System.out.println(feedMessage);

//                    try (FileWriter fileWriter = new FileWriter("test-" + UUID.randomUUID() + ".txt",
                    try (FileWriter fileWriter = new FileWriter("test-" + name + ".txt",
                            StandardCharsets.UTF_8)) {
                        fileWriter.write(feedMessage.title() + ": \n");
                        fileWriter.write("\t" + feedMessage + "\n");
                    }
                    continue;
                }
            }
        }

    }
    public List<FeedMessage> readRss() throws Exception {
        List<FeedMessage> list = new ArrayList<>();
        String description = "";
        String title = "";
        String link = "";
        String language = "";
        String copyright = "";
        String author = "";
        String pubdate = "";
        String guid = "";

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = openUrl();
        XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(in);

        while (xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                delimiter = "http://www.w3.org/2005/Atom".equals(startElement.getNamespaceURI("")) ? "entry" : delimiter;
                String localPart = xmlEvent.asStartElement().getName()
                        .getLocalPart();
                switch (localPart) {

                    case "item":
                        break;
                    case "title":
                        title = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "description":
                        description = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "content":
                        link = xmlEvent.asStartElement().getAttributeByName(QName.valueOf("url")).getValue();
//                        link = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "link":
                        link = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "guid":
                        guid = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "language":
                        break;
                    case "author":
                        author = getCharacterData(xmlEvent, xmlEventReader);
                        break;
                    case "pubDate":
                        break;
                    case "copyright":
                        break;
                }


            } else if (xmlEvent.isEndElement()) {
//                if (xmlEvent.asEndElement().getName().getLocalPart() == "item") {
                if (xmlEvent.asEndElement().getName().getLocalPart() == delimiter) {
                    FeedMessage feedMessage = new FeedMessage(title, description, link, guid , guid);
//                    System.out.println(feedMessage);
                    list.add(feedMessage);
//                    try (FileWriter fileWriter = new FileWriter("test-" + UUID.randomUUID() + ".txt",
//                    try (FileWriter fileWriter = new FileWriter("test-" + name + ".txt",
//                            StandardCharsets.UTF_8)) {
//                        fileWriter.write(feedMessage.title() + ": \n");
//                        fileWriter.write("\t" + feedMessage + "\n");
//                    }
                    continue;
                }
            }
        }

        return list;
    }

    private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
//            result = new String(event.asCharacters().getData().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            result = new String(event.asCharacters().getData().getBytes(StandardCharsets.UTF_8));
//            result = event.asCharacters().getData();;
        }
        return result;
    }

    private InputStream openUrl() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
