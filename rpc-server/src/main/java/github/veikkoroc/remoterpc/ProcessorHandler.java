package github.veikkoroc.remoterpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 18:11
 */
//处理socket
public class ProcessorHandler implements Runnable{

    Socket socket;
    Object service;

    public ProcessorHandler(Socket socket,Object service){
        this.socket = socket;
        this.service = service;
    }

    public ProcessorHandler() {
    }

    @Override
    public void run() {
        System.out.println("开始执行处理客户端请求");
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream =new ObjectInputStream(socket.getInputStream()) ;
            RpcRequest rpcRequest = (RpcRequest)objectInputStream.readObject();//反序列化
            Object result = invoke(rpcRequest);
            //返回给客户端
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Object invoke(RpcRequest rpcRequest){
        //通过反射去调
        Object[] parameters = rpcRequest.getParameters();
        Class<?>[] types = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
              types[i] =  parameters[i].getClass();
        }

        try {
            //获取要调用的方法
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), types);
            //调用方法
            Object result = method.invoke(service, parameters);
            return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
