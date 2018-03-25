package club.cheapok.persistence;

import club.cheapok.model.User;

import javax.ejb.Stateless;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserDao {

    private static final String PATHNAME = "Users.dat";

    public List<User> getAllUsers() {
        List<User> users = null;

        try {
            final File file = new File(PATHNAME);
            if (!file.exists()) {
                users = new ArrayList<>();
                users.add(new User(1, "Jora", "Partizan"));
                saveUserList(users);
            } else {
                final ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                users = (List<User>) is.readObject();
                is.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUser(final int id) {
        return getAllUsers().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean addUser(final User user) {
        final List<User> users = getAllUsers();

        final boolean exists = users.stream().anyMatch(u -> u.getId() == user.getId());

        if (exists) {
            return false;
        }

        users.add(user);
        saveUserList(users);
        return true;
    }

    public boolean updateUser(final User user) {
        final List<User> users = getAllUsers();

        final Optional<Integer> index = users.stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst()
                .map(users::indexOf);

        return index.map(i -> {
            users.set(i, user);
            return true;
        }).orElse(false);
    }

    public boolean deleteUser(final int id) {
        final List<User> users = getAllUsers();

        final boolean removed = users.removeIf(u -> u.getId() == id);

        saveUserList(users);

        return removed;
    }

    private void saveUserList(final List<User> users) {
        try {
            final ObjectOutputStream os = new ObjectOutputStream(
                    new FileOutputStream(new File(PATHNAME))
            );
            os.writeObject(users);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
