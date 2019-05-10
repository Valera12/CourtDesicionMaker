package sample.model;

import java.util.ArrayList;
import java.util.List;

public class DataContainer {
    private List<List<String>> persons = new ArrayList<>();

    public void addPerson (List<String> person) {
        persons.add(person);
    }

    public List<List<String>> getPersons() {
        return persons;
    }
}
