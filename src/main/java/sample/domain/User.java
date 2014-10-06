package sample.domain;

import org.apache.commons.codec.binary.Hex;
import sample.utils.FailFast;

import javax.annotation.concurrent.Immutable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author konstantine.kougios
 */
@Immutable
public final class User {
    private final String name, password;
    private final int age;

    // please use the builder
    private User(Builder builder) {
        name = builder.name;
        password = builder.password;
        age = builder.age;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    private static final User GUEST = builder().name("guest").md5Password("guest").build();

    /**
     * @return the guest user
     */
    public static User guest() {
        // note that we can return the same instance, nice!
        return GUEST;
    }

    /**
     * a user who's password is not yet defined
     */
    public static User withoutPassword(String name, int age) {
        return builder().name(name).age(age).password("").build();
    }

    /**
     * a user with a secure md5 password
     */
    public static User withMd5Password(String name, String password, int age) {
        return builder().name(name).age(age).md5Password(password).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (age != user.age) return false;
        if (!name.equals(user.name)) return false;
        if (!password.equals(user.password)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + age;
        return result;
    }

    @Override
    public String toString() {
        // just for demo purposes, we include the password
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder copy(User copy) {
        Builder builder = new Builder();
        builder.name = copy.name;
        builder.password = copy.password;
        builder.age = copy.age;
        return builder;
    }


    public static final class Builder {
        // sensible defaults
        private String name = "guest";
        private String password = "guest";
        private int age = 20;

        private Builder() {
        }

        public Builder name(String name) {
            FailFast.blank(name, "name");

            this.name = name;
            return this;
        }

        public Builder password(String password) {
            // we allow blank passwords, for the sake of this demo
            FailFast.notNull(password, "password");

            this.password = password;
            return this;
        }

        public Builder md5Password(String password) {
            // we allow blank passwords, for the sake of this demo
            FailFast.notNull(password, "password");

            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] digest = md.digest(password.getBytes());
                this.password = Hex.encodeHexString(digest);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e);
            }
            return this;
        }

        public Builder age(int age) {
            FailFast.between(age, 1, 100);

            this.age = age;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
