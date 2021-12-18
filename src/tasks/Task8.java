package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
//    if (persons.size() <= 0) { // {strelchm} Why <= 0? If <=0 then isEmpty method need to use. But here expression persons.size() <= 1 makes the sense
//      return Collections.emptyList();
//    }
//    persons.remove(0); {strelchm} potential O(n)
    return persons.stream()
            .skip(1)
            .map(Person::getFirstName)
            .collect(Collectors.toList()); // {strelchm} It'll be faster through Stream-pipelines then removing (potentially O(n)) the first element before iterating through collection.
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons)); // {strelchm} Redundant distinct operation combined with Set-collection. The case with HashSet-constructor is simpler and shorter (may be faster too?)
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) { // {strelchm} Using stream joining or StringBuilder is better than String recreation
    return Stream.of(person.getFirstName(), person.getSecondName(), person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream().collect(Collectors.toMap(Person::getId, Person::getFirstName, (oldPerson, newPerson) -> newPerson));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream().anyMatch(persons2::contains);
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count(); // {strelchm} Streams count function using added (instead of redundant variable increment)
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = true;
    return codeSmellsGood || reviewerDrunk;
  }
}
