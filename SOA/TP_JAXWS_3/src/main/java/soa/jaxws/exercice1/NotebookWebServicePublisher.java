package soa.jaxws.exercice1;
import jakarta.xml.ws.Endpoint;
public class NotebookWebServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:9991/ws/notebookservice", new
                NotebookServiceImpl());
    }
}