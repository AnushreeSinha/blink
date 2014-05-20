package com.blink;

import static com.blink.CodeUtil.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

public class BizMethodGeneratorImpl implements BizMethodGenerator{

	JFieldVar mapperField; 

	public void generateAllBizMethods(JDefinedClass serviceClass, Class<?> bizClass) {
		if( mapperField == null){
			mapperField = serviceClass.field(JMod.PRIVATE,org.dozer.Mapper.class, "mapper");
			mapperField.annotate(Autowired.class);
		}
		getCreateBizMethod(serviceClass,bizClass);
		getReadBizMethod(serviceClass,bizClass);
		getUpdateBizMethod(serviceClass,bizClass);
		getDeleteBizMethod(serviceClass,bizClass);
	}

	public void getCreateBizMethod(JDefinedClass serviceClass, Class<?> bizClass) {

		JClass definedBizClass = serviceClass.owner().ref(bizClass);
		JMethod method  = serviceClass.method(JMod.PUBLIC, definedBizClass, "create"+ bizClass.getSimpleName());
		JVar input= method.param(definedBizClass, CodeUtil.camelCase(bizClass.getSimpleName()));

		JVar doField = getMapperStatements(method,definedBizClass,input,PackageType.DO);
		JDefinedClass doFacade = GeneratorContext.getFacade(PackageType.DO);
		String doMethodName = "create" + upperCamelCase(doField.name());
		method.body().assign(doField, serviceClass.fields().get(camelCase(doFacade.name())).invoke(doMethodName).arg(doField));
		//method.body().add();

		getReturnStatement(method,doField, definedBizClass);

	}

	public void getReadBizMethod(JDefinedClass serviceClass, Class<?> bizClass) {
		JClass definedBizClass = serviceClass.owner().ref(bizClass);
		JMethod method  = serviceClass.method(JMod.PUBLIC, bizClass, "get"+ bizClass.getSimpleName());
	    JVar param = method.param(Long.class,"id");

		JClass doClass = definedBizClass.owner().ref(getDOFromBiz(definedBizClass,PackageType.DO));
		JDefinedClass doFacade = GeneratorContext.getFacade(PackageType.DO);
		String doMethodName = "get" + upperCamelCase(doClass.name());
		
		JVar doClassRef= method.body().decl(doClass, CodeUtil.camelCase(doClass.name()),serviceClass.fields().get(camelCase(doFacade.name())).invoke(doMethodName).arg(param));
		//method.body().assign(doClassRef, );
		method.body()._return(getMapperMethodInvocation(mapperField,doClassRef,definedBizClass));

	}

	public void getUpdateBizMethod(JDefinedClass serviceClass, Class<?> bizClass) {
		JClass definedBizClass = serviceClass.owner().ref(bizClass);
		JMethod method  = serviceClass.method(JMod.PUBLIC, bizClass, "update"+ bizClass.getSimpleName());
		JVar input = method.param(bizClass,CodeUtil.camelCase(bizClass.getSimpleName()));

		JVar doField = getMapperStatements(method,definedBizClass,input,PackageType.DO);
		
		JDefinedClass doFacade = GeneratorContext.getFacade(PackageType.DO);
		String doMethodName = "update" + upperCamelCase(doField.name());
		method.body().assign(doField, serviceClass.fields().get(camelCase(doFacade.name())).invoke(doMethodName).arg(doField));
		
		getReturnStatement(method,doField, definedBizClass);
	}

	public void getDeleteBizMethod(JDefinedClass serviceClass, Class<?> bizClass) {
		JMethod method  = serviceClass.method(JMod.PUBLIC, void.class, "delete"+ bizClass.getSimpleName());
		JVar param = method.param(Long.class, "id");
		
		JDefinedClass doFacade = GeneratorContext.getFacade(PackageType.DO);
		String doMethodName = "delete" + upperCamelCase(getSimpleNameFromFQN(bizClass.getName())+PackageType.DO.toString() );
		
		method.body().add (serviceClass.fields().get(camelCase(doFacade.name())).invoke(doMethodName).arg(param));
		
	}

	private JVar getMapperStatements(JMethod method, JClass bizClass, JVar input, PackageType packageType) {
		String doClassName =  getDOFromBiz(bizClass,packageType);

		JClass doClass = bizClass.owner().ref(doClassName);

		JInvocation i = getMapperMethodInvocation(mapperField,input,doClass);
		JVar doClassRef = method.body().decl(doClass, CodeUtil.getSimpleCamelCaseNameFromFQN(doClassName),i);
		return doClassRef;

	}

	private void getReturnStatement(JMethod method,JVar doClassRef, JClass bizClass){
		JInvocation j= getMapperMethodInvocation(mapperField,doClassRef,bizClass);

		method.body()._return(j);
	}

	private String getDOFromBiz(JClass dataClass,
			PackageType packageType) {
		String packageName = dataClass._package().name()+"." + packageType.toString().toLowerCase()+".";


		return packageName+ dataClass.name()+packageType.toString();
	}



}
