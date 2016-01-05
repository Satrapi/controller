package com.artronics.sdwn.models.node;


/**
 * All Nodes in GSDWN must implement this interface. This force all nodes to pass
 * a unique id across all nodes in network.
 * Remember for using Node implementation with Graph consider overriding hashCode and equals.
 */
public interface Node
{
    Long getId();
}
