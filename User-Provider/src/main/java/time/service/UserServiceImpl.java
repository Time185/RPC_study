package time.service;

/**
 * @author Time
 * @created 2019/12/25
 */
public class UserServiceImpl implements UserService {
    @Override
    public String addUserName(String user) {
        return "添加了" + user;
    }
}
