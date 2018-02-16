package ca.ualberta.cs.lonelytwitter;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;



public class TweetListTest extends ActivityInstrumentationTestCase2{
    public TweetListTest() {
        super(LonelyTwitterActivity.class);
    }

    public void testAddTweet() {
        TweetList tweets = new TweetList();
        Tweet tweet = new NormalTweet("Hello");

        try{
            tweets.add(tweet);
            tweets.add(tweet);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    public void testHasTweet() {
        TweetList tweets = new TweetList();
        Tweet tweet = new NormalTweet("Hello");
        assertFalse(tweets.hasTweet(tweet));
        tweets.add(tweet);
        assertTrue(tweets.hasTweet(tweet));
    }

    public void testGetTweet() {
        //using index
        TweetList tweets = new TweetList();
        Tweet tweet = new NormalTweet("Hello");
        tweets.add(tweet);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 1 seconds
            }
        }, 1000);

        TweetList tweets2 = new TweetList();
        Tweet tweet2 = new NormalTweet("Hello again");
        tweets.add(tweet2);

        Tweet returnedTweet = tweets.getTweet(0);
        Tweet returnedTweet2 = tweets.getTweet(1);

        assertEquals(returnedTweet.getMessage(), tweet.getMessage());
        assertEquals(returnedTweet2.getMessage(), tweet2.getMessage());
        assertTrue(returnedTweet2.getDate().after(returnedTweet.getDate()));
    }

    public void testDeleteTest() {
        TweetList tweets = new TweetList();
        Tweet tweet = new NormalTweet("hello");

        tweets.add(tweet);
        tweets.deleteTweet(tweet);
        assertFalse(tweets.hasTweet(tweet));
    }

    public void testGetCount(){
        TweetList tweets = new TweetList();
        Tweet tweet = new NormalTweet("Hello");
        tweets.add(tweet);

        TweetList tweets2 = new TweetList();
        Tweet tweet2 = new NormalTweet("Hello again");
        tweets.add(tweet2);

        TweetList tweets3 = new TweetList();
        Tweet tweet3 = new NormalTweet("Hello again and again");
        tweets.add(tweet3);

        assertEquals( tweets.getCount(),3);
    }

}



