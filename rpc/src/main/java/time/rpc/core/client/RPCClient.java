package time.rpc.core.client;

import time.rpc.core.protocol.RPCProtocol;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * RPC框架核心实现类
 * @author Time
 * @created 2019/12/19
 */
public class RPCClient {
    /**
     * 通过动态代理获取到远程接口的具体实例，通过执行这个实例返回远程调用的结果
     * @param <T>
     * @return
     */
    public static <T> T getRemoteProxy(Class<T> interfaceClass, InetSocketAddress inetAddress){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[] {interfaceClass},
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 调用远程实例的方法
                try(Socket socket = new Socket()){
                    // 连接远程服务机
                    socket.connect(inetAddress);
                    // 获取输入输出流
                    try(
                            ObjectOutputStream serialize = new ObjectOutputStream(socket.getOutputStream());
                            ObjectInputStream deSerialize = new ObjectInputStream(socket.getInputStream());
                            ){
                        // 创建一个rpc框架的请求协议对象
                        RPCProtocol rpcProtocol = new RPCProtocol();
                        // 填充属性
                        rpcProtocol.setInterfaceClassName(interfaceClass.getName());
                        rpcProtocol.setMethodName(method.getName());
                        rpcProtocol.setParameterTypes(method.getParameterTypes());
                        rpcProtocol.setParameterValues(args);
                        // 协议对象序列化，进行网络传输
                        serialize.writeObject(rpcProtocol);

                        // 服务端生成的结果反序列化
                        Object result = deSerialize.readObject();
                        // 结果返回
                        return result;
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        });


    }
}
