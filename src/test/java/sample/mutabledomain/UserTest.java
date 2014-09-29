package sample.mutabledomain;

import org.junit.Test;
import sample.service.UserService;

import static org.junit.Assert.assertEquals;

public class UserTest {

    // we can't have a constant because User is mutable

    @Test
    public void hardToUse() {
        User user = new User();
        user.setName("Kostas");

        userService.authenticate2(user);
    }

    @Test
    public void sampleMutation() {

        // a lot of lines to create 1 fully populated user.
        User user = new User(); // the user instance is in a default state but that's not straight on visible from the code. One has to see the source of User to realise this.
        user.setName("Kostas");
        user.setAge(40);
        user.setPassword("secret");

        // ... mutation is bad!
        user.setName("Mr X");

        // even if mutation is on the same method, reading through the code is harder than immutable classes.
        // this is especially true if mutation occurs inside other methods

        assertEquals("Mr X", user.getName());
    }

    @Test
    public void samplePrototypeFactory() {
        // this can't be applied with the current state of the user class.
        // of course we could add a copy constructor
    }

    public void whyFailingFast() {
        User x = new User("x", -10);

        int yearOfBirth = 2014 - x.getAge(); // 2024! No fail fast makes irrelevant code fail
    }

    @Test
    public void whyFactoriesInsteadOfConstructors() {
        User kostas = new User("Kostas", 40); // it is not straight obvious that the password is not provided. It is not clear if "kostas" is in a consistent state

        String s = "Some value";
        User x = new User(s, 40); // it is not straight obvious that s is the user's name.

        User guest = new User("guest", "guest", 20); // error prone, one might accidentally instantiate it incorrectly, it is not obvious that this is the default system-wide guest user

        User u1 = new User("me", "secret", 20); // it is not obvious that this doesn't encrypt the password
        User u2 = new User("me", 20, "secret"); // it is not obvious that this encrypts the password. It is not clear why the arguments are not in the order they were before
    }

    @Test
    public void defensiveCopies() {
        // we always will need to return copies of the User class, i.e.

        User kostas = new User("Kostas", 40);

        User defensiveCopy = new User(kostas);

    }

    private UserService userService = new UserService();

}