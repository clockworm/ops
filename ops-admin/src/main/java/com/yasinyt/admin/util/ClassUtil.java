package com.yasinyt.admin.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.aop.framework.ReflectiveMethodInvocation;

import com.yasinyt.admin.base.annotation.AccessLimit;
import com.yasinyt.admin.base.annotation.CheckRepetition;
import com.yasinyt.admin.base.annotation.RequiresPermission;
import com.yasinyt.admin.base.exception.AuthorizeException;
import com.yasinyt.admin.base.exception.ResponseSpecialException;
import com.yasinyt.admin.config.MotanLogService;
import com.yasinyt.admin.entity.Emplyee;
import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.enums.ActiveStatusEnum;
import com.yasinyt.admin.enums.BusinessTypeEnum;
import com.yasinyt.admin.enums.CodeEnum;
import com.yasinyt.admin.enums.SystemLoginEmun;
import com.yasinyt.admin.rpc.impl.LoginAuthenticationServiceImpl;
import com.yasinyt.admin.rpc.impl.RedisServiceImpl;
import com.yasinyt.admin.util.redis.CheckRepetitionKey;
import com.yasinyt.admin.util.redis.TokenKey;
import com.yasinyt.admin.util.redis.UserKey;
import com.yasinyt.admin.util.redis.UserSessionKey;
import com.yasinyt.admin.web.vo.UserSessionVO;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @detail class加载打包工具类
 * @author TangLingYun
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class ClassUtil {

	private static HashSet<Class> list = new HashSet<Class>();
	
	/**项目"主包"路径*/
	private static final String PACK_AGE = "com.yasinyt.admin";

	/**需要打包的位置 任意填写文件夹地址即可*/
	private static final String OUT_JAR_PATH = "D:/rpc-service-api";

	/**打包后输出的文件路径*/
	private static final String JAR_NAME = "C:/Users/Administrator/Desktop/motan-service-api.jar";

	public static void main(String[] args) {

		/**需要打包接口的实现类*/
		Class[] obj = new Class[] { 
				new LoginAuthenticationServiceImpl().getClass(),
				new RedisServiceImpl().getClass(),
				new RpcPackageNeedClass().getClass()
		};
		classPackage(obj);
	}

	/** 开始RPC接口打包->jar */
	private static void classPackage(Class[] obj) {
		for (Class clazz : obj) {
			Set<Class> set = getRelyClass(clazz, list);

			for (Class class2 : set) {
				ProtectionDomain protectionDomain = class2.getProtectionDomain();
				String packageNamePath = StringUtils.replaceAll(class2.getName(), "\\.", "/");
				String classpath = protectionDomain.getCodeSource().getLocation().toString().concat(packageNamePath).concat(".class").replaceAll("file:\\/", "");
				String[] split = packageNamePath.split("/");
				String[] pac = new String[split.length-1];
				for (int i = 0; i < split.length-1; i++) {
					pac[i] = split[i];
				}
				String filePath = OUT_JAR_PATH.concat("/"+StringUtils.join(pac, "/"));
				try {
					FileUtils.copyFileToDirectory(new File(classpath), new File(filePath));
				} catch (Exception e) {
					log.error("文件拷贝异常:{}", e);
				}
			}
		}
		ZipUtil.zipFiles(JAR_NAME, OUT_JAR_PATH.concat("/com"));
	}

	/** 获取当前实现类的所有依赖类(包括接口) */
	private static Set<Class> getRelyClass(Class class1, HashSet<Class> list) {
		Method[] methods = class1.getMethods();
		for (Method method : methods) {
			Class[] classes = method.getParameterTypes();
			for (Class class2 : classes) {
				list = getSupClass(class2, list);
			}
			Class returnType = method.getReturnType();
			if (returnType.getName().contains(PACK_AGE)) {
				list = getSupClass(returnType, list);
				getFieldClass(returnType, list);
			}
		}
		list = getInterfaces(class1, list);
		return list;
	}

	/** 获取当前类的父类 */
	private static HashSet<Class> getSupClass(Class clazz, HashSet<Class> list) {
		getInterfaces(clazz, list);
		if (clazz.getName().contains(PACK_AGE)) {
			list.add(clazz);
		}
		Class class1 = clazz.getSuperclass();
		if (class1 != null && class1.getName().contains(PACK_AGE)) {
			return getSupClass(class1, list);
		}
		return list;
	}

	/** 获取当前类的父接口 */
	private static HashSet<Class> getInterfaces(Class clazz, HashSet<Class> list) {
		Class[] interfaces = clazz.getInterfaces();
		for (Class class1 : interfaces) {
			if (class1 != null && class1.getName().contains(PACK_AGE)) {
				list.add(class1);
			}
		}
		return list;
	}

	/**获取当前类属性的类*/
	private static HashSet<Class> getFieldClass(Class clazz, HashSet<Class> list) {
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			return getSupClass(field.getClass(), list);
		}
		return list;
	}
	
	/**获取截点的目标方法*/
	public static @SneakyThrows Method getMethod(final JoinPoint point) {
		MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) point;
		Field proxy = methodPoint.getClass().getDeclaredField("methodInvocation");
		proxy.setAccessible(true);
		ReflectiveMethodInvocation j = (ReflectiveMethodInvocation) proxy.get(methodPoint);
		Method method = j.getMethod();
		return method;
	}
	
	/**获取截点的目标方法的参数->进行封装成map对象  参数名:参数值*/
	public static Map<String,Object> encapsulationMethodParameters(final JoinPoint point) {
		Signature signature = point.getSignature();  
		MethodSignature methodSignature = (MethodSignature) signature;  
		String[] parameterNames = methodSignature.getParameterNames();  
		Object[] values = point.getArgs();
		ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<String,Object>();
		if(parameterNames.length == values.length){
			for (int i = 0,length = values.length ; i < length; i++) {
				map.put(parameterNames[i], values[i]);
			}
		}
		return map;  
	}
	
	

}

/**打包扩展类*/
class RpcPackageNeedClass {
	public void packAgeMustClassArgs(
			UserKey key,TokenKey tokenKey,UserSessionKey sessionKey,
			UserSessionVO vo1,User u1,Emplyee e1,Role r1,Permission p1,
			ActiveStatusEnum enuma,CodeEnum codee,ArrayUtil ua,BeanUtil b2,
			CookieUtil cu,EnumUtil u,KeyUtil kt,PageUtil pu,AccessLimit ac,RequiresPermission qq,
			AuthorizeException ae,ResponseSpecialException re,SystemLoginEmun lo,MotanLogService se,
			ClassUtil t,CheckRepetitionUtil cu1,CheckRepetition co,CheckRepetitionKey ckey,
			CheckRepetitionUtil.InvadeCode ic
			){
	};
	
	public void packAgeNeedClassArgs(
			BusinessTypeEnum b
			
			
			//TODO 需要额外打包的class 请放在方法参数里
			){
	
	};
}
