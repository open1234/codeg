package my.xujb.codeg;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import my.xujb.codeg.generator.base.BaseGenerator;
import my.xujb.codeg.utils.ConfigUtil;

/**
 * @author 徐静波
 * @version 1.0
 * @description codeg代码生成主入口
 */
public class App {
	public static void main(String[] args) {
		try {
			// 主配置文件加载
			Properties properties = ConfigUtil.load("/my/xujb/codeg/config/app.properties");

			// 文件生成器容器
			Set<BaseGenerator> generators = new HashSet<BaseGenerator>();
			// 遍历所有配置属性
			Enumeration<?> element = properties.propertyNames();
			while (element.hasMoreElements()) {
				// 获取配置键值
				String key = (String) element.nextElement();
				// 判断配置是否正确, 并同时添加生成器
				if (key.toLowerCase().startsWith("generator.")
						&& properties.getProperty(key).equalsIgnoreCase("true")) {
					generators.add(createGenerator(properties, key));
				}
			}

			// 生成代码
			doGenerator(generators);
			System.out.println("程序结束");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("系统出错!错误信息:" + e.getMessage());
		}
	}

	/**
	 * 循环生成代码
	 * 
	 * @param generators
	 * @throws Exception
	 */
	private static void doGenerator(Set<BaseGenerator> generators) throws Exception {
		for (BaseGenerator generator : generators) {
			generator.create();
		}
	}

	/**
	 * 创建生成器
	 * 
	 * @param properties
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static BaseGenerator createGenerator(Properties properties, String key) throws Exception {
		String generatorPackage = properties.getProperty("app.generator.package");
		String className = generatorPackage + "." + key.substring("generator.".length()) + "Generator";
		Class<?> classType = Class.forName(className);
		return (BaseGenerator) classType.newInstance();
	}
}
