package vvss;

import vvss.domain.Nota;
import vvss.domain.Student;
import vvss.domain.Tema;
import org.junit.Test;
import vvss.repository.NotaXMLRepository;
import vvss.repository.StudentRepository;
import vvss.repository.StudentXMLRepository;
import vvss.repository.TemaXMLRepository;
import vvss.service.Service;
import vvss.validation.NotaValidator;
import vvss.validation.StudentValidator;
import vvss.validation.TemaValidator;
import vvss.validation.Validator;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void Test1() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("22", "adrian", 92));
        assertNull(s);
    }

    @Test
    public void Test2() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("22", "adrian", 92));
        assertTrue(s == null);
    }

    @Test
    public void Test3() {
        Validator<Tema> validator = new TemaValidator();
        TemaXMLRepository repository = new TemaXMLRepository(validator, "teme.xml");
        Tema t = new Tema("12", "test_desc", 12, 1);
        repository.save(t);
        assertNotNull(repository.findOne("12"));
    }

    @Test
    public void Test4() {
        Validator<Tema> validator = new TemaValidator();
        TemaXMLRepository repository = new TemaXMLRepository(validator, "teme.xml");
        Tema t = new Tema("999", "test_desc", 24, 1);
        repository.save(t);
        assertNull(repository.findOne("999"));
    }

    @Test
    public void TestAddAssignment() {
        AtomicBoolean foundFirst = new AtomicBoolean(false);
        AtomicBoolean foundSecond = new AtomicBoolean(false);
        Validator<Tema> tvalidator = new TemaValidator();
        Validator<Student> svalidator = new StudentValidator();
        Validator<Nota> nvalidator = new NotaValidator();

        StudentXMLRepository srepo = new StudentXMLRepository(svalidator, "studenti.xml");
        TemaXMLRepository trepo = new TemaXMLRepository(tvalidator, "teme.xml");
        NotaXMLRepository nrepo = new NotaXMLRepository(nvalidator, "note.xml");

        Service service = new Service(srepo, trepo, nrepo);
//        test for valid assignment
        service.saveTema("12", "test_desc", 12, 1);
//        test for invalid assignment
        service.saveTema("999", "test_desc", 22, 1);
        service.findAllTeme().forEach(tema -> {
            if (tema.getID().equals("12")) {
                foundFirst.set(true);
            }
            if (tema.getID().equals("999")) {
                foundSecond.set(true);
            }
        });

        assertTrue(foundFirst.get());

        assertFalse(foundSecond.get());

        service.deleteTema("12");
    }
}
