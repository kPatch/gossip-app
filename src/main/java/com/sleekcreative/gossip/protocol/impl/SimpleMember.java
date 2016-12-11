package com.sleekcreative.gossip.protocol.impl;

import com.sleekcreative.gossip.protocol.Member;

import java.io.Serializable;

/**
 * Created by kpatch on 12/11/16.
 */
public class SimpleMember implements Member, Serializable {
    public void setHeartBeat(int hearbeat) {

    }

    public int getHeartBeat() {
        return 0;
    }

    public void setIPAddress(String address) {

    }

    public String getIPAddress() {
        return null;
    }

    public void setPortNumber(int potNumber) {

    }

    public void getPortNumber() {

    }

    /**
     * Hash code based on the member's IP address
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        String address = getIPAddress();
        result = prime * result
                + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return null;
    }
}
