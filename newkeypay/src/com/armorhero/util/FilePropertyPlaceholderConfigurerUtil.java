/**
 * 
 */
package com.armorhero.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.Properties;

/**
 * 
* @ClassName: FilePropertyPlaceholderConfigurerUtil 
* @Description: TODO(properties配置文件读取) 
* @author chenwei
* @date 2014-8-11 下午1:41:53 
*
 */
public class FilePropertyPlaceholderConfigurerUtil extends
		PropertyPlaceholderConfigurer {

	/** 记录日志对象类. */
	private final Log logger = LogFactory
			.getLog(FilePropertyPlaceholderConfigurerUtil.class);


	public FilePropertyPlaceholderConfigurerUtil() {
		super();
		logger.info(String.format("Load All properties By FilePropertyPlaceholderConfigurerUtil"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.FilePropertyPlaceholderConfigurerUtil#
	 * convertProperties(java.util.Properties)
	 */
	@Override
	protected void convertProperties(Properties props) {
		super.convertProperties(props);
		SecurerProperties sp = new SecurerProperties();
		props = sp.decryptProperties(props);
	}

	@Override
	public void setLocations(Resource[] locations) {
		/*
		 * if (!loadFromLocal) { super.setLocations(locations); } else {
		 */
		Resource[] local = new Resource[locations.length];

		for (int i = 0; i < locations.length; i++) {
			Resource tmpRes = locations[i];
			local[i] = getResource(tmpRes);
		}
		super.setLocations(local);
	}

	@Override
	public void setLocation(Resource location) {
		Resource resource = getResource(location);
		super.setLocation(resource);

	}

	/**
	 * 合成完整的路径.
	 * 
	 * @param oldResource
	 *            原资源.
	 * @return 新资源.
	 */
	private Resource getResource(Resource resource) {
		String serverRoot = System.getProperty("server.root");
		String rootPath = null;
		if(StringUtils.isNotEmpty(serverRoot)){
			rootPath = serverRoot+ File.separator+"config";
		}else{
			rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		}
		
		String name = resource.getFilename();
		// 合成新的路径信息.
		File filePath = new File(rootPath,name);
		// 对文件进行加密
		SecurerProperties sp = new SecurerProperties();
		sp.reSave(filePath.getAbsolutePath());
		return new FileSystemResource(filePath);
	}
}