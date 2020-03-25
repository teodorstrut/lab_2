package vvss;

import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.StudentRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void Test1(){
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("22","adrian",92));
        assertNull(s);
    }

    @Test
    public void Test2(){
        Validator<Student> validator = new StudentValidator();
        StudentRepository repo = new StudentRepository(validator);
        Student s = repo.save(new Student("22","adrian",92));
        assertTrue(s == null);
    }

    @Test
    public void Test3(){
        Validator<Tema> validator = new TemaValidator();
        TemaXMLRepository repository = new TemaXMLRepository(validator, "teme.xml");
        Tema t = new Tema("12", "test_desc",12, 1);
        repository.save(t);
        assertNotNull(repository.findOne("12"));
    }

    @Test
    public void Test4(){
        Validator<Tema> validator = new TemaValidator();
        TemaXMLRepository repository = new TemaXMLRepository(validator, "teme.xml");
        Tema t = new Tema("999", "test_desc",24, 1);
        repository.save(t);
        assertNull(repository.findOne("999"));
    }
}
