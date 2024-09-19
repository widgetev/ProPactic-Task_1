package dl.pro.java;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestRunner {

    private final static List<Method> beforeSuite = new ArrayList();
    private final static List<Method> afterSuite = new ArrayList();
    private final static List<Method> beforeTests = new ArrayList();
    private final static List<Method> afterTests = new ArrayList();
    private final static Map<Integer,ArrayList<Method>> test = new HashMap();
    private final static List<Constructor> constructors = new ArrayList();
    private final Class aClass;
    private static Object obj = new Object();

    public TestRunner(Class aClass) {
        this.aClass = aClass;
    }

    static void runTests(Class c) throws Exception {

        TestRunner testRunner=new TestRunner(c);
        testRunner.prepareLists();
        obj = constructors.get(0).newInstance(); //Да, могут быть разные. Но сейчас это опустим
        testRunner.runByList(beforeSuite);
        testRunner.runByMapPriority(test);
        testRunner.runByList(afterSuite);
    }

    private void runByMapPriority(Map<Integer,ArrayList<Method>> map) throws InvocationTargetException, IllegalAccessException {
        Set<Integer> keys = map.keySet();
        Object[] args = new Object[0];//Пока без параметров
        keys.stream().sorted().forEach( it ->
                map.get(it).forEach( m->
                {
                    try {
                        for (Method beforeTest: beforeTests) {
                            beforeTest.invoke(obj,m);
                        }
                        m.invoke(obj, args);
                        for (Method afterTest: afterTests) {
                            afterTest.invoke(obj,m);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
        );
    }

    private void runByList(List<Method> list) throws InvocationTargetException, IllegalAccessException {
        Object[] args = new Object[0];//Пока только без параметров
        for (Method method: list) {
            method.setAccessible(true); //а если он не public?
            method.invoke(obj, args);
        }
    }

    private void prepareLists() throws Exception {
        /*Констуркторы отдельно. Не буду проверять*/
        for (Constructor constructor : aClass.getDeclaredConstructors()) {
            constructors.add(constructor);
        }

        /*Все прочие методы*/
        for (Method method : aClass.getDeclaredMethods()) {
            for (Annotation anotation : method.getDeclaredAnnotations()) {
                if (anotation instanceof Test) {
                        ArrayList methods = test.get(((Test) anotation).priority());
                        if(methods!=null) {
                            methods.add(method);
                        } else {
                            methods=new ArrayList(Collections.singleton(method));
                        }
                        test.put(((Test) anotation).priority(),methods);
                } else if (anotation instanceof BeforeSuite) {
                    checkSuiteAnnotation(beforeSuite, method, anotation);
                } else if (anotation instanceof AfterSuite) {
                    checkSuiteAnnotation(afterSuite, method, anotation);
                } else if(anotation instanceof BeforeTest) {
                    checkSuiteAnnotation(beforeTests, method, anotation);
                    //beforeTests.add(method);
                } else if(anotation instanceof AfterTest) {
                    checkSuiteAnnotation(afterTests, method, anotation);
                    //afterTests.add(method);
                }
            }
        }
    }

    private void checkSuiteAnnotation(List<Method> list, Method method, Annotation annotation) throws Exception {
        if(!Modifier.isStatic(method.getModifiers()))
            throw new Exception(annotation.annotationType().getSimpleName()+ " Применима только к static методам");
        if (list.isEmpty())
            list.add(method);
        else
            throw new Exception(annotation.getClass().getName() + "- не более одного применения.");
    }
}
