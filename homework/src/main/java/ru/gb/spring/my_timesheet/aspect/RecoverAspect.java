package ru.gb.spring.my_timesheet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class RecoverAspect {
    /**
    Создать аспект, который аспектирует методы, помеченные аннотацией Recover, и делает следующее:
        Если в процессе исполнения метода был exception (любой), то его нужно залогировать
    ("Recovering TimesheetService#findById after Exception[RuntimeException.class, "exception message"]") и
    вернуть default-значение наружу. Default-значение: для примитивов значение по умолчанию, для ссылочных типов - null.
    Для void-методов возвращать не нужно.
     */

    @Pointcut("@annotation(ru.gb.spring.my_timesheet.aspect.Recover)") // method
    public void methodRecoverPointcut() {}

    @Around(value = "methodRecoverPointcut()")
    public Object methodRecover(ProceedingJoinPoint pjp){
        Object object = null;
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object target = pjp.getTarget();
        Method method = methodSignature.getMethod();

//        if (method != null) {
        if(method.isAnnotationPresent(Recover.class)){
            try {
                System.out.println("Started");
                object = pjp.proceed();
            } catch (Throwable ex) {
//                "Recovering TimesheetService#findById after Exception[RuntimeException.class, "exception message"]"
                log.info("Recovering {}#{} after Exception[{}, \"{}\"]",
                        target.getClass().getSimpleName(),
                        method.getName(),
                        ex.getClass().getSimpleName(),
                        ex.getMessage());
                return method.getDefaultValue();
            }
        }
        return object;
    }
}
