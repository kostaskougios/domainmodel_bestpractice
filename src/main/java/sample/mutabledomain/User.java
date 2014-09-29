package sample.mutabledomain;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author konstantine.kougios
 */
public class User {
    private String name = "", password = "";
    private int age = 20;

    public User() {
    }

    public User(String name, String password, int age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    /**
     * BAD! We had to switch around the arguments so that we can have
     * a constructor that encrypts the password
     */
    public User(String name, int age, String password) {
        this.name = name;
        setMd5Password(password);
        this.age = age;
    }

    /**
     * we will anyway need a copy constructor for defensive copies.
     */
    public User(User copy) {
        name = copy.name;
        password = copy.password;
        age = copy.age;
    }

    public User(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setMd5Password(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(password.getBytes());
            this.password = Hex.encodeHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
