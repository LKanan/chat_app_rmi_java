package dao;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private List<User> listUsers;

    public List<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    public UserDao() {
        this.listUsers = new ArrayList<User>();
        this.listUsers.add(new User("user1","user1@gmail.com","12345"));
        this.listUsers.add(new User("user2","user2@gmail.com","12345"));
        this.listUsers.add(new User("user3","user3@gmail.com","12345"));
    }

    public User findUser(String email, String password) {
        for (User user : listUsers) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
