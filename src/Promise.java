import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Function;

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
public class Promise<E>{
    private boolean success, async;
    private E data;
    private Function<E> pos;
    private Consumer<E> neg;
    private final CountDownLatch latch;

    private Promise<E> next;

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

    private void resolve(E e, boolean success) {
        try {
            latch.await();
        } catch (Exception ex) {}

        if (success){
            this.next = pos.apply(e);
        } else {
            neg.accept(e);
        }
    }

    public Promise<E> then(Function<E> pos, Consumer<E> neg){
        this.pos = pos;
        this.neg = neg;
        latch.countDown();
        if(!async) resolve(data, success);
        return next;
    }

    public Promise<E> then(Function<E> pos){
        this.pos = pos;
        latch.countDown();
        if(!async && success) resolve(data, success);
        return next;
    }
}
