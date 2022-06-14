package akka_cluster

object Main {


  /*
  *
  * - Akka Cluster provides a fault-tolerant decentralized peer-to-peer based Cluster Membership Service
  *   with no single point of failure or single point of bottleneck.
  *   It does this using gossip protocols and an automatic failure detector.
  *   Akka Cluster allows for building distributed applications, where one application or service spans multiple nodes
  *   (in practice multiple ActorSystems)
  *
  *
  *  - node -> Defined by a "hostname:port:uid tuple"
  *  - When a new node is started it sends a message to all configured seed-nodes
  *  - then sends a join command to the one that answers first.
  *     If none of the seed nodes replies (might not be started yet)
  *      it retries this procedure until successful or shutdown.
  *
  *
  * LEADER
  * - There is no leader election process,
  * - the leader can always be recognised deterministically by any node whenever there is gossip convergence.
  * - The leader is only a role, any node can be the leader and it can change between convergence rounds.
  * - The leader is the first node in sorted order that is able to take the leadership role

  * - The role of the leader:
      - is to shift members in and out of the cluster
      - changing joining members to the up state or exiting members to the removed state.
  * - Currently leader actions are only triggered by receiving a new cluster state with gossip convergence.
  *
  *
  * SEAD NODES:
  * - The seed nodes are contact points for new nodes joining the cluster.
  * - When a new node is started it sends a message to all seed nodes and then sends a join command to the seed node that answers first.
  * - The seed nodes configuration value does not have any influence on the running cluster itself,
  *   it helps them to find contact points to send the join command to;
  *   a new member can send this command to any current member of the cluster, not only to the seed nodes.
  *
  *   The actor system on a node that exited or was downed cannot join the cluster again.
  *   In particular, a node that was downed while being unreachable and then regains connectivity cannot rejoin the cluster.
  *   Instead, the process has to be restarted on the node, creating a new actor system that can go through the joining process again.
  *
  *  - The seed nodes can be started in any order.
  *  - node configured as the first element in the seed-nodes list must be started when initially starting a cluster.
  *    If it is not, the other seed-nodes will not become initialized, and no other node can join the cluster.
  *    The reason for the special first seed node is to avoid forming separated islands when starting from an empty cluster.
  *  - As soon as more than two seed nodes have been started, it is no problem to shut down the first seed node.
  *  - If the first seed node is restarted, it will first try to join the other seed nodes in the existing cluster.
  *  - Note that if you stop all seed nodes at the same time and restart them with the same seed-nodes configuration they will join themselves and form a new cluster, instead of joining remaining nodes of the existing cluster.
  *  - That is likely not desired and can be avoided by listing several nodes as seed nodes for redundancy, and don’t stop all of them at the same time.
  *
  *  - The Configuration Compatibility Check feature ensures that all nodes in a cluster have a compatible configuration.
  *  - Whenever a new node is joining an existing cluster, a subset of its configuration settings (only needed) is sent to the nodes in the
       cluster for verification.
  *  - Once the configuration is checked on the cluster side, the cluster sends back its own set of required configuration settings.
  *  - The joining node will then verify if it’s compliant with the cluster configuration.
  *  - The joining node will only proceed if all checks pass, on both sides.
  *  - Akka Cluster can be used across multiple data centers, availability zones or regions,
  *    so that one Cluster can span multiple data centers and still be tolerant to network partitions.
  *
  *
  * Singleton manager:
  *  - singleton actor instance among all cluster nodes or a group of nodes tagged with a specific role.
  *  - started on the oldest node by creating a child actor from supplied Behavior.
  *  - it makes sure that at most one singleton instance is running at any point in time.
  *  - always running on the oldest member with specified role.
  *  - when the oldest node is Leaving the cluster there is an exchange from the oldest and the new oldest before a new singleton is started up.
  *  - the cluster failure detector will notice when oldest node becomes unreachable due to things like JVM crash, hard shut down, or network failure.
  *    After Downing and removing that node the a new oldest node will take over and a new singleton actor is created.
  *
  * - To communicate with a given named singleton in the cluster you can access it though a proxy ActorRef.
  * - ClusterSingleton.init for a given singletonName  ActorRef is returned.
  * - if there already is a singleton manager running - is returned.

  * - The proxy will route all messages to the current instance of the singleton, and keep track of the oldest node in
  *   the cluster and discover the singleton’s ActorRef.
  * - singleton is unavailable:
  *   - the proxy will buffer the messages sent to the singleton
  *   - deliver them when the singleton is finally available
  *   - if the buffer is full the proxy will drop old messages when new messages are sent via the proxy.
  *   - the size of the buffer is configurable and it can be disabled by using a buffer size of 0.
  *
  * Example: https://doc.akka.io/docs/akka/current/typed/cluster-singleton.html#example
  *
  * Lease
  *   - a lease can be used as an additional safety measure to ensure that two singletons don’t run at the same time.
  *   - reasons for how this can happen:
  *   - mistakes in the deployment process leading to two separate Akka Clusters
  *   - timing issues between removing members from the Cluster on one side of a network partition and shutting them down on the other side
  * */



  def main(args: Array[String]): Unit = {

  }
}
