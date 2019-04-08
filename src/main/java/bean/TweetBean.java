/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author teren
 */
@Named(value = "tweetBean")
@Dependent
public class TweetBean {

    /**
     * Creates a new instance of TweetBean
     */
    public TweetBean() {
    }
    
}
