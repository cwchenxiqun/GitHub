/**
 * 
 */
package com.armorhero.util;

import com.armorhero.util.system.TripleDesWrapperUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * 
 * @author chenwei
 *
 */
public class SecurerProperties {
    /** 记录日志对象类.  */
    private final Log logger = LogFactory.getLog(SecurerProperties.class);

    /** 加密的标记.*/
    private static final String PREFIX_ENCRYPT = "{enc}";
    /** 加密的属性名称的列表，如果key=value 中key包含以下字段，着对值进行加密.*/
    private static final List<String> DECRYPT_TAG_LIST = new ArrayList<String>();

    private boolean isEncrypted = false;

    /**
     * default.
     */
    static {
        DECRYPT_TAG_LIST.add("password");
        DECRYPT_TAG_LIST.add("pwd");
        DECRYPT_TAG_LIST.add("username");
    }

    /**
     * 对加密字段解密.
     * @param props Properties
     * @return Properties 解密后的Properties
     */
    public Properties decryptProperties(Properties props) {
        Enumeration<?> propertyNames = props.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = (String) propertyNames.nextElement();
            String propertyValue = props.getProperty(propertyName);
            String convertedValue = decryptValue(propertyName, propertyValue);
            props.setProperty(propertyName, convertedValue);
        }
        return props;
    }

    /**
     * 对加密字段解密.
     * @param props Properties
     * @return Properties 解密后的Properties
     */
    public Properties encryptProperties(Properties props) {
        Enumeration<?> propertyNames = props.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = (String) propertyNames.nextElement();
            String propertyValue = props.getProperty(propertyName);
            String convertedValue = encryptValue(propertyName, propertyValue);
            props.setProperty(propertyName, convertedValue);
        }
        return props;
    }

    /**
     * 加密字符串值.
     * @param name key
     * @param value 加密字段
     * @return 解密后值.
     */
    private String encryptValue(final String name, String value) {
        String encv = value.trim();
        if (value == null || "".equals(value.trim())) {//空字符串不加密
            return value;
        }

        if (encv.startsWith(PREFIX_ENCRYPT)) {//已经加密过，不加密.
            return value;
        }

        boolean enc = false;
        for (String s : DECRYPT_TAG_LIST) {
            if (name.indexOf(s) > -1) {
                logger.warn(name + "  is marked must be encrypted!! ");
                enc = true;
                break;
            }
        }
        if (enc) {//包含加密的敏感值，如password等.
            try {
                String nv = PREFIX_ENCRYPT + TripleDesWrapperUtil.getInstance().doEnc(value);
                isEncrypted = true;
                return nv;
            } catch (Exception e) {
                logger.error(value + " encrypt error use old value,errorMsg:" + e.getMessage(), e);
                return value;
            }
        } else {
            return value;
        }
    }

    /**
     * 解密字符串值.
     * @param value 加密字段
     * @return 解密后值.
     */
    private String decryptValue(String name, String value) {
        String encv = value.trim();
        if (encv != null && encv.startsWith(PREFIX_ENCRYPT)) {
            encv = value.substring(PREFIX_ENCRYPT.length());
            try {
                String nv = TripleDesWrapperUtil.getInstance().doDec(encv);
                logger.info(String.format("Found that the property(%s=%s) must be decrypted, Decrypted OK.", name,
                        value));
                return nv;
            } catch (Exception e) {
                logger.error(value + " decrypt error:" + e.getMessage(), e);
                return value;
            }
        } else {
            return value;
        }
    }

    /**
     * 关闭资源.
     * @param f RandomAccessFile
     */
    private void close(RandomAccessFile f) {
        if (f != null) {
            try {
                f.close();
            } catch (Exception e) {
            }
        }
        f = null;
    }

    /**
     * 对未加密的文件进行加密保存.
     * @param filename 文件名.
     */

    public void reSave(String filename) {

        String prostring = readProperties(filename);
        if (!isEncrypted) {
            return;
        }
        RandomAccessFile savefile = null;
        try {
            savefile = new RandomAccessFile(filename, "rw");
            savefile.write(prostring.getBytes());
            savefile.close();
            logger.info("Found that the file must be encrypted, Encrypted OK.FileName:" + filename);
        } catch (Exception ex) {
            logger.error("rewrite properties file error:" + filename);
        } finally {
            close(savefile);
        }

    }

    /**
     * 读取文件，并对包含敏感数据字段进行加密。
     * @param filename 文件名
     * @return 加密后数据
     */
    private String readProperties(String filename) {
        StringBuffer readbuffer = new StringBuffer();

        RandomAccessFile profile = null;
        try {
            profile = new RandomAccessFile(filename, "r");
            String instring;
            while ((instring = profile.readLine()) != null) {
                if (!isPropertiesConfig(instring)) {
                    readbuffer.append(instring);
                } else {
                    readbuffer.append(parser(instring));
                }
                readbuffer.append("\r\n");
            }
            profile.close();
            return readbuffer.toString();
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            logger.error("read properties file error:" + filename);
            isEncrypted = false;
            return readbuffer.toString();
        } finally {
            close(profile);
        }

    }

    /**
     * 判断一个字符串是否是一个Properties配置项.(key=value 的格式是正确的配置项.).
     * @param instring 字符串
     * @return 是否是一个Properties配置项
     */
    private boolean isPropertiesConfig(String instring) {
        if ("".equals(instring.trim())) {//空行
            return false;
        }
        if (instring.startsWith("#")) {//注释
            return false;

        }

        if (instring.indexOf("=") < 1) {//没有等于号
            return false;
        }
        return true;
    }

    /**
     * 判断是否需要做加密.
     * @param str 行数据
     * @return 加密后的数据
     */
    private String parser(String str) {
        int ipos = str.indexOf("=");
        if (ipos < 1) {
            return str;
        }
        String k = str.substring(0, ipos);
        String v = str.substring(ipos + 1);
        String nv = encryptValue(k, v);

        return k + "=" + nv;
    }

    /**
     * 
     * @param args .
     */
    public static void main(String[] args) {
        String encryptStr = "{enc}e44496aba3545216";
        String decryptStr = "innoplay";

        String decrypt = new SecurerProperties().decryptValue("test", encryptStr);
        String encrypt = new SecurerProperties().encryptValue("db.password", decryptStr);
        System.out.println(decryptStr);
        System.out.println(encryptStr);
        System.out.println("decrypt equals? " + decrypt.equals(decryptStr));
        System.out.println("encrypt equals? " + encrypt.equals(encryptStr));

        new SecurerProperties().reSave("etc\\properties\\test.properties");
    }
}
