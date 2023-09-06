package se.lexicon.data;


import se.lexicon.model.Person;
import se.lexicon.util.PersonGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Create implementations for all methods. I have already provided an implementation for the first method *
 */
public class DataStorageImpl implements DataStorage {

    private static final DataStorage INSTANCE;

    static {
        INSTANCE = new DataStorageImpl();
    }

    private final List<Person> personList;

    private DataStorageImpl() {
        personList = PersonGenerator.getInstance().generate(1000);
    }

    static DataStorage getInstance() {
        return INSTANCE;
    }


    @Override
    public List<Person> findMany(Predicate<Person> filter) {
        List<Person> result = new ArrayList<>();
        return personList.stream()
                .filter(filter)
                .toList();
//        for (Person person : personList) {
//            if (filter.test(person)) {
//                result.add(person);
//            }
//        }
//        return result;
    }


    @Override
    public Person findOne(Predicate<Person> filter) {

        return personList.stream()
                .filter(filter).findFirst()
                .orElse(new Person(null,null,null,null));
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString) {
        return personToString.apply((findOne(filter))); //This doesn't return the string specified in the specifications of the assignment
//        Person person = findOne(filter); // This does on the other hand.
//        if (person != null) {
//            String name = person.getFirstName() + " " + person.getLastName();
//            String birthDate = person.getBirthDate().toString();
//            return "Name: " + name + " born " + birthDate;
//        } else {
//            return "Person not found";
//        }
    }

    @Override // Added a custom format function as an argument to this method so we can have different output designs for each call of the method
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString) {
        List<String> result = new ArrayList<>();
        for (Person person : personList) {
            if (filter.test(person)) {
                result.add(personToString.apply(person));
            }
        }
        return result;
    }
//    @Override
//    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString) {
//        List<String> result = new ArrayList<>();
//        for (Person person : personList) {
//            if (filter.test(person)) {
//                String name = person.getFirstName() + " " + person.getLastName();
//                String birthDate = person.getBirthDate().toString();
//                result.add("Name: " + name + " born " + birthDate);
//            }
//        }
//        return result;
//    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer) {
        for (Person person : personList) {
            if (filter.test(person)) {
                consumer.accept(person);
            }
        }
    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator) {
        List<Person> sortedList = new ArrayList<>(personList);
        sortedList.sort(comparator);
        return sortedList;
    }

    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator) {
        List<Person> result = new ArrayList<>();
        for (Person person : personList) {
            if (filter.test(person)) {
                result.add(person);
            }
        }
        result.sort(comparator);
        return result;
    }
}
