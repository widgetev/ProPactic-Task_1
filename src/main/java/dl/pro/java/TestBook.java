package dl.pro.java;

import dl.pro.java.AfterSuite;
import dl.pro.java.BeforeSuite;
import dl.pro.java.Book;
import dl.pro.java.Test;

import java.awt.*;
import java.lang.reflect.Method;

public class TestBook {
    Book book;
    static Long startTime;

    public TestBook() {
        this.book = new Book(1, "Умная книга", "Как мама мыла раму", "Ленин В.И.");
    }

    @Test(priority = 10)
    public void soutName() {
        System.out.println( book.getName());
    }

    @Test(priority = 8)
    public void soutAuthor() {
        System.out.println( book.getAuthor());
    }

    @Test(priority = 4)
    public void soutDescription() {

        System.out.println(book.getDescription());
    }

    @Test
    public void soutId() {
        System.out.println( book.getId());
    }
    @Test
    public void soutToString() {
        System.out.println( book.toString());
    }

    @BeforeSuite
    public static void BeforeTest() {
        System.out.println("Начало теста");
        startTime=System.currentTimeMillis();
    }

    @AfterSuite
    public static void AfterTest(){
        long time = System.currentTimeMillis() - startTime;
        System.out.println("Конец теста. Затрачено : " + time + "mils.");
    }

    @BeforeTest
    public static void soutUpDelim(Method method){
        System.out.println("");
        System.out.println("Запуск " + method);
    }

    @AfterTest
    public static void soutDownDelim(Method method){
        System.out.println("Выполнен " + method);
        System.out.println("=================================");
    }


}
