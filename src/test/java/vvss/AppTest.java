package vvss;

import vvss.domain.Nota;
import vvss.domain.Pair;
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
    public void TestAddStudentBigBang() {
        Validator<Student> validator = new StudentValidator();
        StudentXMLRepository repo = new StudentXMLRepository(validator, "studenti.xml");
        Student s = repo.save(new Student("10", "adrian", 92));
        assertNull(s);
    }

    @Test
    public void TestAddAssignmentBigBang() {
        Validator<Tema> validator = new TemaValidator();
        TemaXMLRepository repository = new TemaXMLRepository(validator, "teme.xml");
        Tema t = new Tema("12", "test_desc", 10, 1);
        repository.save(t);
        assertNotNull(repository.findOne("12"));
    }

    @Test
    public void TestAddGradeBigBang() {
        Validator<Nota> notaValidator = new NotaValidator();

        NotaXMLRepository repo = new NotaXMLRepository(notaValidator, "note.xml");

        Nota newNota = new Nota(new Pair<>("10", "10"), 10, 10, "10");

        repo.save(newNota);

        assertNotNull(repo.findOne(new Pair<>("10", "10")));
    }

    @Test public void TestAllBigBang(){
        TestAddStudentBigBang();
        TestAddAssignmentBigBang();
        TestAddGradeBigBang();
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

    @Test public void TestAddStudentNullId(){
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("", "adrian", 120));
        assertNull(s);
    }

    @Test public void TestAddStudentValidId(){
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("a", "adrian", 120));
        assertNotNull(repo.findOne("a"));
    }
    @Test public void TestAddStudentNullName(){
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("b", "", 120));
        assertNull(s);
    }
    @Test public void TestAddStudentValidName(){
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("b", "abc", 120));
        assertNotNull(repo.findOne("b"));
    }
    @Test public void TestAddStudentInvalidGroup(){
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("c", "abc", 5));
        assertNull(s);
    }
    @Test public void TestAddStudentValidGroup(){
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("c", "abc", 130));
        assertNotNull(repo.findOne("c"));
    }

    @Test public void TestGroupBoundaryValueInvalid(){
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("d", "abc", 109));
        assertNull(s);
    }

    @Test public void TestGropuBoundaryValueValid(){
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("d", "abc", 120));
        assertNotNull(repo.findOne("d"));
    }
}
