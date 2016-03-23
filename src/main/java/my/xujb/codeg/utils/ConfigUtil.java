package my.xujb.codeg.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 徐静波
 * @version 1.0
 * @description 配置文件工具类
 */
public class ConfigUtil {
	public static Properties load(String path) throws IOException {
		// 获取配置文件数据流
		InputStream is = ConfigUtil.class.getResourceAsStream(path);
		// 创建配置文件
		Properties properties = new Properties();
		// 加载配置文件
		properties.load(is);
		// 返回配置文件
		return properties;
	}
}
