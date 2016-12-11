package com.sleekcreative.gossip.protocol;

/**
 * Created by kpatch on 12/10/16.
 */

/**
 * A "member" is a process or a node within the network.
 */
public interface Member {
    /**
     * Everytime we receive a membership list, it will contain a heartbeat
     *
     * @param hearbeat
     */
    void setHeartBeat(int hearbeat);
    int getHeartBeat();

    /**
     * The IP address of the member
     * @param address
     */
    void setIPAddress(String address);
    String getIPAddress();

    /**
     * The port number on which the member is listening for Gossips from
     * @param potNumber
     */
    void setPortNumber(int potNumber);
    void getPortNumber();
}
