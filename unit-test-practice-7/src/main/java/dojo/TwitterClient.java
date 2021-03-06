package dojo;

import java.io.IOException;
import java.util.Properties;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterClient {

	// az alkalmazásunk azonosítója a twitter rendszerében
	private final String consumerKey;
	 
    // az alkalmazásunk jelszava a twitter rendszerében
	private final String consumerSecret;

    // Access Token
	private final String accessToken;

    // Access Token Secret
	private final String accessTokenSecret;

	private final TwitterFactory twitterFactory;
	
	public TwitterClient() throws IOException {
		twitterFactory = new TwitterFactory();
		Properties authProps = new Properties();
		authProps.load(getClass().getResourceAsStream("twitter-auth.properties"));
		consumerKey = authProps.getProperty("consumerKey");
		consumerSecret = authProps.getProperty("consumerSecret");
		accessToken = authProps.getProperty("accessToken");
		accessTokenSecret = authProps.getProperty("accessTokenSecret");
	}
	
	/**
	 * Ezzel a metódussal ki szeretnénk tweet-elni az aznap megtett lépéseink számát.
	 * A lépésszámot egy független eszközzel mérjük, a metódus paraméterként kapja.
	 * Követelmények:
	 * <ul>
	 * <li>Negatív lépésszámot nem írunk ki, hanem {@link IllegalArgumentException}-t dobunk.</li>
	 * <li>Ha a lépésszám 0, akkor írjuk ki, hogy "Ma nem léptem egyet sem."</li>
	 * <li>Ha a lépésszám pozitív, akkor írjuk ki: "A mai lépéseim száma: ", és a lépésszámot fűzzük utána.</li>
	 * <li>Minden üzenet végére tegyük oda az aktuális dátumot YYYY-MM-dd formában.</li>
	 * <li>Mielőtt közzétennénk az új státuszüzenetet, ellenőrizzük, hogy egyedi-e! Ha már van olyan üzenet, akkor dobjunk {@link IllegalArgumentException}-t.</li>
	 * <li>Mielőtt közzétennénk az új státuszüzenetet, töröljük ki azokat a korábbiakat, amik ugyanerre a napra szólnak!</li>
	 * </ul>
	 * A tweet-eléshez használjuk a {@link Twitter#updateStatus(String)} metódust.
	 * Az üzenetek lekérdezéséhez használjuk a {@link Twitter#getHomeTimeline()} metódust.
	 * @param todaySteps az adott napon megtett lépések száma
	 * @throws TwitterException 
	 */
	public void tweetSteps(int todaySteps) throws TwitterException {
		// TODO implementálni ezt a metódust
		Twitter twitter = newTwitter();
		twitter.updateStatus("Test: " + todaySteps);
		ResponseList<Status> tweets = twitter.getHomeTimeline();
		for (Status tweet : tweets) {
			System.out.println(tweet.getText());
		}
	}
	
	private Twitter newTwitter() {
		Twitter twitter = twitterFactory.getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
        return twitter;
	}
	
}
