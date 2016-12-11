# Gossiper
A simple implementation of a [Gossip protocol](https://en.wikipedia.org/wiki/Gossip_protocol)

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
    - [Gossip Implementation](#)
- [Build](#build)
- [Usage](#usage)

## About Gossip
Lorem Ipsum

### Problem Definition:
The problem that Gossip is trying to solve is the Multicast problem.

We have different processes and nodes spread across the internet and want to be able to spread information across theses
members

In comparison to Broadcast were we want to spread information across all members of the networks, Mulcast is restrictive
to a set of members of the network.

Multicast is a protocol the 'often' sits on the application layer, but there are protocols that talk to the lower level
layers, such as IP Multicast which is implemented in the routers themselves. A caveat of IP Multicast is that it might
not be enabled in the router.

### Background
#### Requirements for Multicast:

1. Fault Tolerance
    Nodes may crash, packets may be dropped and we want our protocol to be reselient to this
2. Scalability
    The network may scale to 1000's of nodes, we don't want the overhead per node to scale out linearly or in worst-case
    exponentially

#### Approach

- We have a sender
- A list of recepient
- Go through the list of recepients and send them a TCP or UDP packet

#### Caveats
It is not fault-tolerant. The sender could fail half way through iterating through the list.
Overhead can be high: Imagine the use-case were a sender has a list containing 1000's of recipients. This would increase
the latency of sending the messages - it could be linear (order N)

#### Algorithm

Use a Spanning Tree

### Gossip Implementation

### Single Multicast Message - Push Gossip Protocol

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
