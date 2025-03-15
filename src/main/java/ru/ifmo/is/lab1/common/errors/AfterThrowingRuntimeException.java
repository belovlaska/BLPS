package ru.ifmo.is.lab1.common.errors;


import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import ru.ifmo.is.lab1.common.context.ApplicationLockBean;

@Aspect
public class AfterThrowingRuntimeException {

  private final ApplicationLockBean applicationLock;

  public AfterThrowingRuntimeException(ApplicationLockBean applicationLock) {
    this.applicationLock = applicationLock;
  }

  @AfterThrowing(pointcut = "execution(* ru.ifmo.is.lab1.batchoperations.BatchOperationImporterService.*(..)))", throwing = "ex")
  public void afterThrowingAdvice(Exception ex) throws Exception {
    this.applicationLock.getImportLock().unlock();
    throw ex;
  }
}
