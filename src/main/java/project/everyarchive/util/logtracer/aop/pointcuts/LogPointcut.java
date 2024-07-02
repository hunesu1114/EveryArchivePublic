package project.everyarchive.util.logtracer.aop.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class LogPointcut {

    @Pointcut("execution(* project.everyarchive.controller.*.*(..))")
    public void allController() {
    }

    @Pointcut("execution(* project.everyarchive.repository.*.*(..))")
    public void allRepository() {
    }

    @Pointcut("execution(* project.everyarchive.service.*.*(..))")
    public void allService() {}

    @Pointcut("allController() || allRepository() || allService()")
    public void unionPointcut() {}
}
