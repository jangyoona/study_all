package com.demoweb.factory;

import java.io.InputStream;
import java.util.Properties;

import com.demoweb.dao.AppSettingsDao;
import com.demoweb.dao.BoardDao;
import com.demoweb.dao.MemberDao;

public class DemoWebBeanFactory2 {

	static Properties mappings;
	
	static {
		mappings = new Properties();
		
		try {
//		String path = "D:\\jangyoona\\workspace\\java-web\\demoweb\\src\\main\\webapp\\WEB-INF\\config\\bean-config.properties";
//		FileInputStream fis = new FileInputStream(path);
		InputStream  is = DemoWebBeanFactory2.class
						  .getClassLoader()
						  .getResourceAsStream("com/demoweb/config/bean-config.properties");
		mappings.load(is);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	// generic 호환성 활용한 객체 생성기
	private static <E> E getInstanceFromSettings(String key) {
		E obj = null;
		try {
			// reflection을 사용해서 인스턴스 만들기
			String className = mappings.getProperty(key); // 클래스 이름 읽기 (패키지명 포함)
			Class claz = Class.forName(className); // 클래스 이름으로 클래스 정보 객체 생성
			obj = (E)claz.getDeclaredConstructor().newInstance(); // 클래스 정보 객체를 사용해서 인스턴스 만들기 (new X)
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return obj;
	}
	
	
	// object 호환성 활용한 객체 생성기
	private static Object getInstanceFromSettings2(String key) {
		Object obj = null;
		try {
			// reflection을 사용해서 인스턴스 만들기
			String className = mappings.getProperty(key); // 클래스 이름 읽기 (패키지명 포함)
			Class claz = Class.forName(className); // 클래스 이름으로 클래스 정보 객체 생성
			obj = claz.getDeclaredConstructor().newInstance(); // 클래스 정보 객체를 사용해서 인스턴스 만들기 (new X)
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	
	public static AppSettingsDao getSettingsDao() {
			
			AppSettingsDao settingsDao = getInstanceFromSettings("app.settings.dao"); // 제네릭은 형변환 필요없음
//			AppSettingsDao settingsDao2 = (AppSettingsDao)getInstanceFromSettings2("app.settings.dao"); // 오브젝트는 받는쪽에서 형변환 필요
			
			return settingsDao;
			
		}
		
		
	public static MemberDao getMemberDao() {		
		MemberDao memberDao = getInstanceFromSettings("member.dao");
		return memberDao;		
	}
	
	public static BoardDao getBoardDao() {		
		BoardDao boardDao = getInstanceFromSettings("board.dao");
		return boardDao;		
	}
	

}
