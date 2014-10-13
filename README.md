Treap example
=============

### Description:
Using a [treap](https://en.wikipedia.org/wiki/Treap) the main application (when finished) will read a text and return the number of distinct words read (case incentive) and the le most used word of at least 6 characters.


This project use Apache Maven to manage testing, building and running


### Run test suite (to be sure everything works before doing anything else)
    mvn test

### Build Jar
    mvn package

### Run main application (read text in stdin)
    mvn exec:java -Dexec.mainClass="fr.univ_amu.treap_application.lectureTexte"

### Run main application with Monte Cristo example (change file path for any other text)
    mvn exec:java -Dexec.mainClass="fr.univ_amu.treap_application.lectureTexte" < src/main/java/fr/univ_amu/treap_application/texte_monte_cristo.txt

