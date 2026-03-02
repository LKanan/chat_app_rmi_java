package metier;

import dao.UserDao;
import model.User;

public class UserMetier {
    public User seConnecter(String email, String password) {
            return new UserDao().findUser(email, password);
    }
}
