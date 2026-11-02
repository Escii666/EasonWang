package gh2;

import deque.Deque61B;
import deque.ArrayDeque61B;

//Note: This file will not compile until you complete the Deque61B implementations
public class GuitarString {
    /**
     * Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday.
     */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque61B<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayDeque61B<>();
        // 每个周期需要保存的采样数量，先四舍五入再转为整数。
        int sampleCount = (int) Math.round(SR / frequency);
        // 初始时琴弦还没有被拨动，所有采样都为 0.0。
        for (int i = 0; i < sampleCount; i++) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {

        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        int n = buffer.size();
        for (int i = 0; i < n; i++) {
            buffer.removeFirst();
            buffer.addLast(Math.random() - 0.5);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {

        double oldFirst = buffer.get(0);
        buffer.removeFirst();
        double newFirst = buffer.get(0);
        double sample = (oldFirst + newFirst) / 2.0 * DECAY;
        buffer.addLast(sample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}

