package ru.gb.spring.my_timesheet.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Recover {
// recover - восстанавливать
//    Class<?>[] noRecoverFor() default {};

//    boolean enabled() default true;
//
//    Level level() default Level.DEBUG;

}
