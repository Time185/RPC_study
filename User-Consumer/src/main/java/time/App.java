package time;

import time.rpc.core.client.RPCClient;
import time.rpc.core.server.RPCServer;
import time.service.UserService;

import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        UserService userService = RPCClient.getRemoteProxy(UserService.class,new InetSocketAddress("127.0.0.1",8888));
        String result = userService.addUserName("王五");
        System.out.println(result);
    }
}
