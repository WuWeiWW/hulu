package looly.github.hulu;

import java.net.URL;

import looly.github.hutool.Log;
import looly.github.hutool.Setting;
import looly.github.hutool.StrUtil;
import looly.github.hutool.URLUtil;

import org.slf4j.Logger;

/**
 * 全局设定文件
 * @author xiaoleilu
 *
 */
public class HuluSetting {
	private static Logger log = Log.get();
	
	//-------------------------------------------------------- Default value start
	/** 默认的字符集编码 */
	public final static String DEFAULT_CHARSET = "utf-8";
	/** 默认的URL参数分隔符 */
	public final static String DEFAULT_URL_PARAM_SEPERATOR = ",";
	/** 用户自定义字符集的参数名称 */
	public final static String DEFAULT_PARAM_NAME_CHARSET = "charset";
	/** 默认的配置文件路径（相对ClassPath）*/
	public final static String DEFAULT_SETTING_PATH = "config/hulu.setting";
	/** 默认Action类的后缀，既无后缀 */
	public static final String DEFAULT_ACTION_SUFFIX = StrUtil.EMPTY;
	//-------------------------------------------------------- Default value end
	
	/** 字符编码 */
	public static String charset;
	/** URL参数的分隔符 */
	public static String urlParamSeparator;
	/** Action 包 */
	public static String[] actionPackages;
	/** Action类的后缀 */
	public static String actionSuffix;
	/** 用户自定义字符集的参数名称 */
	public static String param_name_charset;
	/** 是否为开发模式 */
	public static boolean isDevMode;
	
	static {
		load();
	}
	
	/**
	 * 加载全局设定
	 */
	public static void load() {
		load(DEFAULT_SETTING_PATH);
	}
	
	/**
	 * 加载全局设定
	 * @param settingPath 设定文件路径，相对Classpath
	 */
	synchronized public static void load(String settingPath) {
		URL url = URLUtil.getURL(settingPath);
		if(url == null) {
			log.warn("Can not find Hulu setting file [{}], use default setting.", settingPath);
			return;
		}
		Setting setting = new Setting(url, DEFAULT_CHARSET, true);
		
		charset = setting.getStringWithDefault("charset", DEFAULT_CHARSET);
		urlParamSeparator = setting.getStringWithDefault("url.param.separator", DEFAULT_URL_PARAM_SEPERATOR);
		actionPackages = setting.getStringsWithDefault("action.package", new String[]{""});
		actionSuffix = setting.getStringWithDefault("action.suffix", DEFAULT_ACTION_SUFFIX);
		param_name_charset = setting.getString("param.name.charset", DEFAULT_PARAM_NAME_CHARSET);
		
		isDevMode = setting.getBool("mode.dev");
	}
}
