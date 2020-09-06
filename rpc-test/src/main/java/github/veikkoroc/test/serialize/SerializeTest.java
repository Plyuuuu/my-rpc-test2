package github.veikkoroc.test.serialize;

import org.junit.Test;

import java.io.*;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/6 8:57
 */
public class SerializeTest {
    @Test
    public void testObjectOutputStream(){
        ObjectOutputStream objectOutputStream =null;
        try {
            //创建对象输出流
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("D:\\Person.dat")));
            //向文件写Object
            objectOutputStream.writeObject(new Person("迪丽热巴",18));
            //刷新,强制把缓冲区数据写入磁盘
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            if (objectOutputStream!=null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Test
    public void testObjectInputStream(){

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(new File("D:\\Person.dat")));
            Object o = objectInputStream.readObject();
            Person person = (Person)o;
            System.out.println(person);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
