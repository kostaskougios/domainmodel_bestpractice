package sample;


/**
 * @author konstantine.kougios
 */
public class Performance {

    private static final int ITERATIONS = 100000000;

    public static void main(String... args) {
        // the immutable instance creation is slower than
        // the mutable one due to the builder but also mainly due
        // to the fail fast string checks.
        // but what this benchmark can't show is the multi-threaded benefits
        // of immutable classes

        for (int runs = 0; runs < 5; runs++) {
            long startImmutable = System.currentTimeMillis();
            for (int i = 0; i < ITERATIONS; i++) {
                sample.domain.User.builder().name("x").password("y").age(8).build();
            }
            long stopImmutable = System.currentTimeMillis();
            for (int i = 0; i < ITERATIONS; i++) {
                sample.mutabledomain.User u = new sample.mutabledomain.User();
                u.setName("x");
                u.setPassword("y");
                u.setAge(8);
            }
            long stopMutable = System.currentTimeMillis();
            System.out.println("Mutable : " + (stopMutable - stopImmutable) + " Immutable : " + (stopImmutable - startImmutable));
        }
    }
}
