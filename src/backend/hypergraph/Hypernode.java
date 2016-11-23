package backend.hypergraph;

import java.util.ArrayList;

import backend.utilities.exception.ExceptionHandler;

//import pebbler.PebblerHypernode;

public class Hypernode<T, A>
{
    public T data;
    public int id;

    //edges in which this node is a source
    public ArrayList<Hyperedge<A>> outEdges;
    
    //edges in which this node is the target
    public ArrayList<Hyperedge<A>> inEdges;
    
    public Hypernode(T theData, int theId)
    {
        data = theData;
        id = theId;
        outEdges = new ArrayList<Hyperedge<A>>();
        inEdges = new ArrayList<Hyperedge<A>>();
    }
    
    public boolean addInEdge(Hyperedge<A> edge)
    {
        if(edge.targetNode != this.id) ExceptionHandler.throwException(new Exception("Hyperedge<A>: " + edge.toString() + "has a different target node than expected " + this.id));
        //for performance, comment out the next line
        if(inEdges.contains(edge)) return false;
        return inEdges.add(edge);
    }
    
    public boolean addOutEdge(Hyperedge<A> edge)
    {
        if(!edge.sourceNodes.contains(this.id)) ExceptionHandler.throwException(new Exception("Hyperedge<A>: " + edge.toString() + "is not incident to " + this.id));
        //for performance, comment out the next line
        if(outEdges.contains(edge)) return false;
        return outEdges.add(edge);
    }
    
//    public PebblerHypernode<A> createPebbledNode() 
//    {
//        return new PebblerHypernode<A>(id);
//    }
}
