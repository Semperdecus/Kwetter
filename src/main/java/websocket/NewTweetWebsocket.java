/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import models.Account;
import service.AccountService;
import service.TweetService;

/**
 *
 * @author teren
 */
@ApplicationScoped
@ServerEndpoint("/ws/newTweet/{username}")
public class NewTweetWebsocket {

    @Inject
    private TweetService tweetService;

    @Inject
    private AccountService accountService;

    private static final Map<String, Session> clients
            = Collections.synchronizedMap(new HashMap<String, Session>());

    private static final Set<Session> activeClients
            = Collections.synchronizedSet(new HashSet<Session>());

    private Account sessionUser;

    private static final Logger logger = Logger.getLogger(NewTweetWebsocket.class.getName());

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        logger.log(Level.INFO, "Opening Session {0}", session.getId());
        clients.put(username, session);
        activeClients.add(session);
        this.sessionUser = accountService.findByUsername(username);
    }

    @OnClose
    public void onClose(Session session) {
        logger.log(Level.INFO, "Closing Session {0}", session.getId());
        activeClients.remove(session);
        clients.remove(this.sessionUser.getUsername());
    }

    @OnError
    public void onError(Throwable t) {
        logger.log(Level.INFO, "onError", t);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.log(Level.INFO, "Received Message on Session {0}", session.getId());

        try {
            synchronized (clients) {
                for (Account a : this.sessionUser.getFollowing()) {
                    Session s = clients.get(a.getUsername());

                    if (s != null) {
                        if (!s.equals(session)) {
                            System.out.println("sending message to " + a.getUsername());
                            clients.get(a.getUsername()).getBasicRemote().sendText(message);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }
}
