package com.blink;

import org.springframework.beans.factory.annotation.Autowired;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

public class CodeUtil {

	public static String camelCase(String value) {
		return String.valueOf(value.charAt(0)).toLowerCase() + value.substring(1);
	}

	public static String upperCamelCase(String value) {
		return String.valueOf(value.charAt(0)).toUpperCase() + value.substring(1);
	}

	public static String getSimpleNameFromFQN(String fqn) {
		return fqn.substring( fqn.lastIndexOf("." )+1);
	}

	public static String getSimpleCamelCaseNameFromFQN(String fqn) {
		return camelCase(getSimpleNameFromFQN(fqn));
	}

	public static JInvocation getMapperMethodInvocation(JFieldVar field, JVar clazz, JClass clazz2) {
		JInvocation j= field.invoke("map");
		j.arg(clazz);
		j.arg(JExpr.dotclass(clazz2));
		return j;
	}
	
	public static JInvocation gettoJsonMethodInvocation(JFieldVar field) {
		JInvocation j= field.invoke("toJson");
		j.arg(field);
		return j;
	}
	
	
	
	public static void addAutowiredField(JDefinedClass definedClass,JDefinedClass field) {
		String fieldName = CodeUtil.camelCase(field.name());
		JFieldVar var = definedClass.field(JMod.PRIVATE, field, fieldName);
		var.annotate(Autowired.class);
	}
	
}
