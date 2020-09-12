package github.veikkoroc.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/8 8:50
 */
@Slf4j
public final class PropertiesFileUtils {
    private PropertiesFileUtils() {
    }

    public static Properties readPropertiesFile(String fileName) {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String rpcConfigPath = rootPath + fileName;
        try {
            rpcConfigPath= URLDecoder.decode(rpcConfigPath,"utf-8");//解决路径带空格
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("Rpc配置文件解码错误[{}]",e.getMessage());
        }
        //System.out.println(rpcConfigPath);
        Properties properties = null;
        try (FileInputStream fileInputStream = new FileInputStream(rpcConfigPath)) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            log.error("读取属性文件时发生异常: [{}]", fileName);
        }
        return properties;
    }
}
