package sample.domain;

import org.junit.Test;
import sample.service.UserService;

import static org.junit.Assert.assertEquals;

public class UserTest {

    // we can have constants because no client code can mutate them!
    // also we can instantiate the constant in 1 go.
    private static final User KOSTAS = User.builder()
            .name("Kostas")
            .password("secret")
            .age(40)
            .build();

    @Test
    public void easyToUse() {
        userService.authenticate1(User.builder().name("Kostas").build());
    }

    @Test
    public void sampleMutation() {
        // KOSTAS is immutable, so how can we possibly update his name?
        User mrX = User.copy(KOSTAS).name("Mr X").build();
        assertEquals("Mr X", mrX.getName());
    }

    @Test
    public void samplePrototypeFactory() {
        // KOSTAS can also serve as a prototype factory, all these are 40 years old and have the same password
        // (maybe a bit silly sample, but you get the point)
        User mrX = User.copy(KOSTAS).name("Mr X").build();
        User mrY = User.copy(KOSTAS).name("Mr Y").build();
        assertEquals("Mr X", mrX.getName());
        assertEquals("Mr Y", mrY.getName());
    }

    @Test
    public void theBuilderCanDoThingsForUs() {
        User mrSecure = User.builder().name("MR Secure").md5Password("md5Me!").build();
        assertEquals("969f1e10ea726356dfedaf822b6b8f5b", mrSecure.getPassword());
    }

    @Test
    public void sensibleDefaults() {
        // sensible default values can help, especially during test cases and to avoid null pointer exceptions
        User def = User.builder().build();
        assertEquals("guest", def.getName());
        assertEquals("guest", def.getPassword());
        assertEquals(20, def.getAge());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whyFailingFast() {
        // if we fail fast, then we know the exact line of code that something is wrong.
        // the following line with throw an IllegalArgumentException due to the invalid age.
        User user = User.builder().age(-10).build();

        // in 10 years the age would be... this will never execute because
        // the failfast for age() will throw an exception. This way we never
        // have to deal with the incorrect age of 0!
        int tenYearsFromNowAge = user.getAge() + 10;
    }

    @Test
    public void whyFactoriesInsteadOfConstructors() {
        // (See "Efficient Java", Item 1, http://www.amazon.com/Effective-Java-Edition-Joshua-Bloch/dp/0321356683#reader_0321356683 )
        // a class can benefit from not exposing any constructors. Instead it exposes
        // a couple of well defined (and named) static factories.

        // a guest user, note that because User is immutable the method returns a singleton.
        // it is well defined who is the guest user of the system
        User guest = User.guest();

        // a user that needs to change his password
        User unsecured = User.withoutPassword("MR NeedToChangePassword", 30);

        // a user with a secure password
        User secured = User.withMd5Password("Mr Ok", "safe_password", 25);

        // some of the benefits include:
        // - the static method has a name => increases code readability
        // - there can be many static factory methods with the same parameters => avoid constructors that switch parameter order just to achieve the same
        // - we can control instance creation, i.e. User.guest() returns always the same instance
    }

    private UserService userService = new UserService();
}