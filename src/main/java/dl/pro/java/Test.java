package dl.pro.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
    //определяет порядок выполнения. 1 - высший приоритет. 10 - выполнять в последнюю очередь
    public int priority() default 5;
}
