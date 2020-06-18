package ext.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import wipro.automation.utilities.CommonUI;

public class Test {

	public static void main(String args[]) throws Exception{
		/*Class<?> execClass = Class.forName("abfl.rpa.processes.TestProcess"); // convert string classname to class
	    Object obj = execClass.newInstance(); // invoke empty constructor

	    String methodName = "";

	    // with single parameter, return void
	    methodName = "getStep";
	    Method setNameMethod = obj.getClass().getMethod(methodName, String.class);
	    setNameMethod.invoke(obj, "Test"); */
		CommonUI c=new CommonUI();
		System.out.println(c.getTimeDifference("2017-11-05 21:48:44", "2017-11-05 21:50:42"));
		
	}
	
	public void getPath(){
		System.out.println(getClass().getResource("/"));
	}
}
