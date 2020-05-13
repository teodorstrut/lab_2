package vvss;

import vvss.domain.Nota;
import vvss.domain.Pair;
import vvss.domain.Student;
import vvss.domain.Tema;
import org.junit.Test;
import vvss.repository.*;
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

    @Test
    public void TestAllBigBang() {
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

    @Test
    public void TestAddStudentNullId() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("", "adrian", 120));
        assertNull(s);
    }

    @Test
    public void TestAddStudentValidId() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("a", "adrian", 120));
        assertNotNull(repo.findOne("a"));
    }

    @Test
    public void TestAddStudentNullName() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("b", "", 120));
        assertNull(s);
    }

    @Test
    public void TestAddStudentValidName() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("b", "abc", 120));
        assertNotNull(repo.findOne("b"));
    }

    @Test
    public void TestAddStudentInvalidGroup() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("c", "abc", 5));
        assertNull(s);
    }

    @Test
    public void TestAddStudentValidGroup() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("c", "abc", 130));
        assertNotNull(repo.findOne("c"));
    }

    @Test
    public void TestGroupBoundaryValueInvalid() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("d", "abc", 109));
        assertNull(s);
    }

    @Test
    public void TestGropuBoundaryValueValid() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("d", "abc", 120));
        assertNotNull(repo.findOne("d"));
    }

    @Test
    public void addStudent() {
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        repo.save(new Student("12", "st_1_name", 937));

        assertEquals("st_1_name", repo.findOne("12").getNume());
    }

    @Test
    public void addStudentAddAssignment() {
        Validator<Student> svalidator = new StudentValidator();
        StudentRepository srepo = new StudentRepository(svalidator);
        Validator<Tema> tvalidator = new TemaValidator();
        TemaRepository trepo = new TemaRepository(tvalidator);
        srepo.save(new Student("13", "st_2_name", 937));
        trepo.save(new Tema("22", "hw_desc_1", 12, 10));

        assertEquals("st_2_name", srepo.findOne("13").getNume());
        assertEquals("hw_desc_1", trepo.findOne("22").getDescriere());
    }

    @Test
    public void addStudentAddAssignmentAddGrade() {
        Validator<Student> svalidator = new StudentValidator();
        StudentRepository srepo = new StudentRepository(svalidator);
        Validator<Tema> tvalidator = new TemaValidator();
        TemaRepository trepo = new TemaRepository(tvalidator);
        Validator<Nota> nvalidator = new NotaValidator();
        NotaRepository nrepo = new NotaRepository(nvalidator);
        srepo.save(new Student("14", "st_3_name", 937));
        trepo.save(new Tema("23", "hw_desc_2", 12, 10));
        nrepo.save(new Nota(new Pair<>("14", "23"), 10, 10, "good"));

        assertEquals("st_3_name", srepo.findOne("14").getNume());
        assertEquals("hw_desc_2", trepo.findOne("23").getDescriere());
        assertEquals((int) 10, (int) nrepo.findOne(new Pair<>("14", "23")).getNota());
    }
}
