package vvss;

import domain.Student;
import org.junit.Test;
import repository.StudentRepository;
import repository.StudentXMLRepository;
import validation.StudentValidator;
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
}
