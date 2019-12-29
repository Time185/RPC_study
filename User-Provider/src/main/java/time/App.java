package time;

import time.rpc.core.server.RPCServer;
import time.service.UserService;
import time.service.UserServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // 获取服务端
        RPCServer server = new RPCServer();
        // 暴露接口
        server.publicServiceAPI(UserService.class, new UserServiceImpl());
        // 发布服务
        server.start(8888);
    }
}
