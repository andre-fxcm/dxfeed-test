/*
 * Copyright(c) 2021 FXCM, LLC.
 * 520 Madison, 18th Floor
 * New York, New York 10022

 * THIS SOFTWARE IS THE CONFIDENTIAL AND PROPRIETARY INFORMATION OF
 * FXCM, LLC. ("CONFIDENTIAL INFORMATION"). YOU SHALL NOT DISCLOSE
 * SUCH CONFIDENTIAL INFORMATION AND SHALL USE IT ONLY IN ACCORDANCE
 * WITH THE TERMS OF THE LICENSE AGREEMENT YOU ENTERED INTO WITH
 * FXCM.
 *
 * History:
 *
 */

import com.devexperts.mdd.auth.util.SignedToken;
import com.dxfeed.api.DXFeed;
import com.dxfeed.api.DXFeedEventListener;
import com.dxfeed.api.DXFeedSubscription;
import com.dxfeed.event.market.Quote;

import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String s = SignedToken.newBuilder()
                              .setIssuer("issuer")
                              .setSubject("")
                              .setMessage("user")
                              .setIssuedNow()
                              .setExpirationFromNow(Duration.ofDays(1))
                              .toToken()
                              .signToken("secret");
        System.out.println("token = " + s);
        //AutherLoginHandlerFactory.setAppToken(token);
        // create subscription for a specific event type on default feed
        DXFeedSubscription<Quote> sub = DXFeed.getInstance().createSubscription(Quote.class);

        // define listener for events
        sub.addEventListener(new DXFeedEventListener<Quote>() {
            public void eventsReceived(List<Quote> events) {
                for (Quote quote : events) {
                    System.out.println(quote);
                }
            }
        });

        // add symbols to start receiving events
        sub.addSymbols("IBM", "GOOG", "AAPL", "SPY");
    }
}
