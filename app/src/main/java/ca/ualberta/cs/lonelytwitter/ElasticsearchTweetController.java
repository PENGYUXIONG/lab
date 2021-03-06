package ca.ualberta.cs.lonelytwitter;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.params.SearchType;

/**
 * Created by romansky on 10/20/16.
 */
public class ElasticsearchTweetController {
    private static JestDroidClient client;

    // TODO we need a function which adds tweets to elastic search
    public static class AddTweetsTask extends AsyncTask<NormalTweet, Void, Void> {

        @Override
        protected Void doInBackground(NormalTweet... tweets) {
            verifySettings();

            for (NormalTweet tweet : tweets) {
                Index index = new Index.Builder(tweet).index("testing").type("tweet").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded())
                    {
                        tweet.setId(result.getId());
                    }
                    else
                    {
                        Log.i("Error","Some error =(");
                    }

                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the tweets");
                }

            }
            return null;
        }
    }

    // TODO we need a function which gets tweets from elastic search
    public static class GetTweetsTask extends AsyncTask<String, Void, ArrayList<NormalTweet>> {
        @Override
        protected ArrayList<NormalTweet> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<NormalTweet> tweets = new ArrayList<NormalTweet>();

            // TODO Build the query
            Search search = new Search.Builder(search_parameters[0]).setSearchType(SearchType.QUERY_THEN_FETCH).addIndex("testing").addType("tweet").build();
            try {
               // TODO get the results of the query
                Log.i("Test", search_parameters[0]);
                SearchResult result = client.execute(search);
                if(result.isSucceeded())
                {
                    List<NormalTweet> foundTweets = result.getSourceAsObjectList(NormalTweet.class);
                    tweets.addAll(foundTweets);
                }
                else
                {
                    Log.i("Error", "Search query failed to find any thing =/");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tweets;
        }
    }




    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}