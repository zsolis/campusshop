package com.zsolis.campusshop.aspect;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.zsolis.campusshop.util.*;

@Component
@Aspect
public class TransactionAspect {
	@Autowired
	private HibernateUtil hibernateUtil;
	
	@Pointcut("execution(* com.zsolis.campusshop.service.*Service.*(..))")
	public Object serviceMethod() {
		return new Object();
	}
	
	@Around("serviceMethod()")
	public Object applyService(ProceedingJoinPoint joinPoint) {
		try {
			hibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			Object result = joinPoint.proceed();
			hibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
			return result;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			hibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}
}
