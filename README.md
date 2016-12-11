# Gossiper
A simple implementation of [Gossip-Style](https://en.wikipedia.org/wiki/Gossip_protocol) Membership, that implements 
failure detection.

## The Story
You have just recently joined a secret invite-only club. Everyday the club meets in a large and dark room. 
The club is big, and the only way that others get to know about new members (or even hear about new members) is through 
side chat or gossips spread across nearby members - who then spread these gossips to their neighbors as well. 

Your friend Alice has just brought you into the club.

> "Hey, did you hear Alice brought Bob into the club?"... Carl whispers to his fellow neighboring members. 

And the gossips begins...

## Table of Contents

- [About Gossip](#about-gossip)
    - [Background](#background)
        - [Multicast](#multicast)
    - [Gossip-Style Membership Implementation](#)
- [Build](#build)
- [Usage](#usage)

## About Gossip
Lorem Ipsum

### Problem Definition:
The Gossip Protocol tries to solve the [Multicast](https://en.wikipedia.org/wiki/Multicast) Problem: We have different 
processes and nodes spread across the a network (i.e. the internet) and we want to be able to spread information across 
theses members reliably as possible

*In comparison to [Broadcast](https://en.wikipedia.org/wiki/Broadcasting) were we want to spread information across all 
members of the networks, Mulcast is restrictive to a set of members of the network.*

*Multicast is a protocol the 'often' sits on the application layer, but there are protocols that talk to the lower level
layers, such as [IP Multicast](https://en.wikipedia.org/wiki/IP_multicast) which is implemented in the routers themselves. A caveat of IP Multicast is that it might
not be enabled in the router.*

#### Requirements for Multicast:

1. **Fault Tolerance:** Nodes may crash, packets may be dropped and we want our protocol to be resilient to this
2. **Scalability:** The network may scale to 1000's of nodes, we don't want the overhead per node to scale out linearly 
or ,in worst-case, exponentially

#### A Simple Approach

- We have a sender
- The sender has a list of recipients
- Go through the list of recipients and send them a TCP or UDP packet

##### Caveats
- This approach is simple, but It is not fault-tolerant. 
- The sender could fail half way through iterating through the list.
- Overhead can become high: Imagine the use-case were a sender has a list containing 1000's of recipients. This would increase
the latency of sending the messages - it could be linear (order N)

### Gossip-Style Membership

We start off with the same notion as other membership protocols: every process (node) wants to send it's heartbeat to other 
processes. Hearbeats are indicative of a process's liveliness. 

These hearbeats are sequenced numbers that are incremented locally. 

Processes timeout waiting for other processes' heartbeats and mark the corresponding processes as failed, 
i.e. *t_timeout* can be 10 seconds - meaning that after not hearing a process's heart for 10 seconds it is marked as failed

Every process maintains a table containing entries of neighboring processes and their heartbeats - this table is known as
a process's **membership list**. 

Periodically each process sends its membership list to **N** of its neighbors, which are selected at random. 
This is known as a **gossip**, essentially each process gossips its membership list. The receiver of the membership list 
then merges and updates it's own membership to contain the latest heartbeat, as shown in the diagram below.

When a process times out, and is declared failed/dead, it is not immediately deleted. It is rather *marked* as dead or 
placed in a queue of dead processes. These dead processes are then cleaned after *t_cleanup* seconds. 
The latter helps avoid the case where a dead process might never go away because another process might share the dead
process again and would be consider a new process if we don't already have it in our membership list.
 
 **Further explanation:**
Consider the following case where we immediately remove failed(dead) processes...

Say we have two processes, *p1* an *p2*. 

- Process *p2* considers the third entry in it's membership table to be failed, say process *p3*, and removes it right away. 
- As soon as a this occurs it just so happens that *p1* gossips its membership list to *p2*. And within this list is process *p3*.
- Following the protocol, *p2* compares it's own membership list and notices that it does not contain *p3*. Hence,
 by rules of the protocol, *p2* adds *p3* to its membership list and updates *p3*'s  heartbeat to the latest heartbeat.
- *p3* times out again and the whole process can occur all over again, hence never removing *p3* from the membership list.

#### Single Multicast Message - Push Gossip Protocol

- There's a group of nodes, let this be the multicast group
- You have a single multicast sender
- There is a single multicast message that we want to send
- We want to get the multicast message across all nodes

#### Algorithm
- Periodically (i.e. 5 seconds or 10 seconds) send the message to *b* random target nodes
    - *b* is known as the Gossip Fanout 
    - It is possible that the same random target(s) will be chosen and that nodes will receive multiple copies of the same message
- Send the message over [UDP](https://en.wikipedia.org/wiki/User_Datagram_Protocol). This message is be known as the **gossip message**. 
- Once a receiver receives a gossip, it becomes infected and does the same - periodically it sends
the message to a randomly selected group of nodes

#### NOTE:
- The gossip protocol is not synchronized across all the nodes. Different nodes run at different periods.
- There are other version, such as a Pull Gossip and a hybrid variant: Push-Pull

### Resources

- [Gossip Algorithms - MIT](http://web.mit.edu/devavrat/www/GossipBook.pdf)

#BUILD

Gossiper is built using Gradle. To build Gossiper and its example programs run:

```Bash
$ gradle build
```

#USAGE

```Bash
$ ./gossiper <options>
```

Options:

* `-p` or `--port`: Port number to listen on. Defaults to 8545.
* `-t` or `--timeout`: Lorem ipsum.
* `--debug`: Output debug messages
