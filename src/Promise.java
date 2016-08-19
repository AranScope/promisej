import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * A lightweight Java interpretation of JS promises.
 *
 * Usage:
 *      ASync (non-blocking):
 *          - Change the return type of your function to 'Promise<Object>'
 *          - At the start of the function, declare a default constructed promise i.e. Promise<Object> prom = new Promise<>();
 *          - Perform all of your 'intensive' operations inside a new thread
 *          - When an operation is successful call 'promisename'.resolve(data, wasSuccessful)
 *          - The last line of the function should return the default constructed promise (i.e. non-blocking)
 *
 *          - When you call the function e.g. fr.readLines(), call .then(lambda success) or .then(lambda success, lambda fail)
 *
 *      Sync (blocking):
 *          - Change the return type of your function to 'Promise<Object>'
 *          - Return a promise constructed with the (data, wasSuccessful) constructor.
 *          - When you call the function e.g. fr.readLines(), call .then(lambda success) or .then(lambda success, lambda fail)
 *
 * @param <E> The return type of the promise.
 */
public class Promise<E> {

    private boolean success, async;
    private E data;
    private Consumer<E> pos, neg;
    private final CountDownLatch latch;

    public Promise(E e, boolean success) {
        this.async = false;
        this.data = e;
        this.success = success;
        this.latch = new CountDownLatch(1);
    }

    public Promise(){
        this.async = true;
        this.latch = new CountDownLatch(1);
    }

    public void resolve(E e, boolean success) {
        try {
            latch.await();
        } catch (Exception ex) {}

        if (success) pos.accept(e);
        else neg.accept(e);
    }

    public void then(Consumer<E> pos, Consumer<E> neg){
        this.pos = pos;
        this.neg = neg;
        latch.countDown();
        if(!async) resolve(data, success);
    }

    public void then(Consumer<E> pos){
        this.pos = pos;
        latch.countDown();
        if(!async && success) resolve(data, success);
    }
}
