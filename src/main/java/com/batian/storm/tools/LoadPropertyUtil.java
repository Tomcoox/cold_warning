package com.batian.storm.tools;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 加载配置文件工具类
 * @author dongxie
 * created by 20170413
 */
public class LoadPropertyUtil {

	// 读取邮件配置文件
	public static String getEmail(String key) {
		String value = "";
		Locale locale = Locale.getDefault();
		try {
			ResourceBundle localResource = ResourceBundle.getBundle( "coldwarn",
					locale );
			value = localResource.getString( key );
		} catch (MissingResourceException mre) {
			value = "";
		}
		return value;
	}

	// 读取数据库配置文件
	public static String getDB(String key) {
		String value = "";
		Locale locale = Locale.getDefault();
		try {
			ResourceBundle localResource = ResourceBundle.getBundle( "db",
					locale );
			value = localResource.getString( key );
		} catch (MissingResourceException mre) {
			value = "";
		}
		return value;
	}

	// 读取公共配置文件
	public static String getConfig(String key) {
		String value = "";
		Locale locale = Locale.getDefault();
		try {
			ResourceBundle localResource = ResourceBundle.getBundle( "config",
					locale );
			value = localResource.getString( key );
		} catch (MissingResourceException mre) {
			value = "";
		}
		return value;
	}

}
