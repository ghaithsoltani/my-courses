package soa.jaxws.exercice1;

import jakarta.jws.WebService;
import java.util.List;

@WebService(name = "NotebookService", targetNamespace = "http://jaxws.exercice1.soa.com")
public interface NotebookService {
    boolean addPerson(Person person);
    List<Person> getPersons();
    Person getPersonAt(String name);
}