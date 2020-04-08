package vvss;

import vvss.console.UI;
import vvss.domain.Nota;
import vvss.domain.Student;
import vvss.domain.Tema;
import vvss.repository.NotaXMLRepository;
import vvss.repository.StudentXMLRepository;
import vvss.repository.TemaXMLRepository;
import vvss.service.Service;
import vvss.validation.NotaValidator;
import vvss.validation.StudentValidator;
import vvss.validation.TemaValidator;
import vvss.validation.Validator;

public class Main {
    public static void main(String[] args) {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        UI consola = new UI(service);
        consola.run();

        //PENTRU GUI
        // de avut un check: daca profesorul introduce sau nu saptamana la timp
        // daca se introduce nota la timp, se preia saptamana din sistem
        // altfel, se introduce de la tastatura
    }
}
