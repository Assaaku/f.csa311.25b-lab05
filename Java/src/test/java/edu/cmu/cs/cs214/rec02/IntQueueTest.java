package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


/**
 * TODO: 
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
    //    mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        // TODO: write your own unit test
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        // TODO: write your own unit test
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        // TODO: write your own unit test
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        assertEquals(Integer.valueOf(1), mQueue.peek());
        assertEquals(Integer.valueOf(1), mQueue.peek());
    }
    @Test
    public void testClear() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.peek());
    }
    
    @Test
    public void testEnsureCapacity() {
        int initialCapacity = 10; 
        for (int i = 0; i < initialCapacity; i++) {
            mQueue.enqueue(i);
        }
        mQueue.enqueue(initialCapacity);
        int expectedNewSize = initialCapacity + 1;

        assertEquals(expectedNewSize, mQueue.size());

        for (int i = 0; i < expectedNewSize; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        assertTrue(mQueue.isEmpty());
    }

    

    
    
    @Test
    public void testSequentialOperations() {

        assertTrue(mQueue.isEmpty());
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        mQueue.enqueue(3);
        assertEquals(Integer.valueOf(2), mQueue.peek());
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertEquals(Integer.valueOf(3), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
    }


    @Test
    public void testDequeueEmptyQueue() {
        assertTrue("queue should be empty", mQueue.isEmpty());

        assertNull("dequeue from an empty queue should return null", mQueue.dequeue());

    }

    @Test
    public void testEnsureCapacityWrapAround() {
        int capacity = 10; 
        for (int i = 0; i < capacity; i++) {
            mQueue.enqueue(i);
        }
    
        int dequeueCount = 5;
        for (int i = 0; i < dequeueCount; i++) {
            mQueue.dequeue();
        }
    
        for (int i = 0; i < dequeueCount; i++) {
            mQueue.enqueue(capacity + i);
        }
    
        mQueue.enqueue(capacity + dequeueCount);
    
        
        assertEquals("queue should handle wrap-around correctly and resize", capacity + 1, mQueue.size());

        assertEquals("first element should be correct after wrap-around", Integer.valueOf(dequeueCount), mQueue.dequeue());
    
    
        for (int i = dequeueCount + 1; i <= capacity + dequeueCount; i++) {
            assertEquals("subsequent elements should follow in correct order", Integer.valueOf(i), mQueue.dequeue());
        }
    
        assertTrue("queue should be empty after all elements are dequeued", mQueue.isEmpty());
    }
    


    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        // TODO: write your own unit test
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }


}
