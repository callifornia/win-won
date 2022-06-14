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
  *  - node -> Defined by a hostname:port:uid tuple
  *  - When a new node is started it sends a message to all configured seed-nodes
  *  - then sends a join command to the one that answers first.
  *     If none of the seed nodes replies (might not be started yet)
  *      it retries this procedure until successful or shutdown.
  *
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
  * */



  def main(args: Array[String]): Unit = {

  }
}
