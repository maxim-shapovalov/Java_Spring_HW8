package ru.gb.spring.my_timesheet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j // Slf4j - Simple logging facade for java - появляется log (см.методы ниже)
@Aspect
@Component
public class LoggingAspect {

    // Before
    // AfterThrowing
    // AfterReturning
    // After = AfterReturning + AfterThrowing
    // Around ->

//  Bean = TimesheetService, obj = timesheetService
    // proxyTimesheetService(obj)

    @Pointcut("execution(* ru.gb.spring.my_timesheet.service.TimesheetService.*(..))")
    public void timesheetServiceMethodsPointcut() {
    }

    // Pointcut - точка входа в аспект

//    @Before(value = "execution(* ru.gb.spring.my_timesheet.service.TimesheetService.*(Long))")
//    public void beforeTimesheetServiceFindById(JoinPoint jp) {
//        Long id = (Long) jp.getArgs()[0];
//        log.info("TimesheetService#findById(), id = {}", id); //для конкретного метода
//    }


//    TimesheetService.findById(Long = 3)
    @Before(value = "timesheetServiceMethodsPointcut()")
    public void beforeTimesheetService(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        StringBuilder info = new StringBuilder();

        Object[] args = jp.getArgs();
        for (Object arg : args) {
            String type = arg.getClass().getSimpleName();
            info.append(" (").append(type).append(" = ")
                    .append(arg).append(")");
        }
        log.info("Before -> TimesheetService.{} " + info, methodName);
  }
//
//    @AfterThrowing(value = "timesheetServiceMethodsPointcut()", throwing = "ex")
//    public void afterTimesheetServiceFindById(JoinPoint jp, Exception ex) {
//    String methodName = jp.getSignature().getName();
//    log.info("AfterThrowing -> TimesheetService#{} -> {}", methodName, ex.getClass().getName());
//  }

}
