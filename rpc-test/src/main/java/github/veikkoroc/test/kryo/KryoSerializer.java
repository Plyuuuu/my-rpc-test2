package github.veikkoroc.test.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import github.veikkoroc.test.serialize.Person;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/6 9:34
 */
public class KryoSerializer implements Serializer {
    /**
     * 由于 Kryo 不是线程安全的。每个线程都应该有自己的 Kryo，Input 和 Output 实例。
     * 所以，使用 ThreadLocal 存放 Kryo 对象
     */
    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        //kryo.register(RpcResponse.class);
        //kryo.register(RpcRequest.class);
        kryo.register(Person.class);
        kryo.setReferences(true);//默认值为true,是否关闭注册行为,关闭之后可能存在序列化问题，一般推荐设置为 true
        kryo.setRegistrationRequired(false);//默认值为false,是否关闭循环引用，可以提高性能，但是一般不推荐设置为 true
        return kryo;
    });
    @Test
    public void testSerialize(){
        Person person = new Person("迪丽热巴",18);
        byte[] serialize = serialize(person);
        //System.out.println(Arrays.toString(serialize));
        Person deserialize = deserialize(serialize, Person.class);
        System.out.println(deserialize);
    }

    @Override
    public byte[] serialize(Object object) {
        try(    //Io包下处理流
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                //Kryo的输出流
                Output output = new Output(byteArrayOutputStream))
        {
            //获取Kryo
            Kryo kryo = kryoThreadLocal.get();
            //Object-->bytes
            kryo.writeObject(output,object);
            kryoThreadLocal.remove();
            return output.toBytes();

        }catch (Exception e){
            throw new RuntimeException("序列化失败");
        }


    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream)){
            Kryo kryo = kryoThreadLocal.get();
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return clazz.cast(o);
        }catch (Exception e){
            throw new RuntimeException("反序列化失败");
            //e.printStackTrace();
        }
    }
}
