package com.artronics.sdwn.models.node;

import org.apache.log4j.Logger;

public class SimpleNode implements Node
{
    private final static Logger log = Logger.getLogger(SimpleNode.class);
    private Long id;

    public SimpleNode()
    {
    }

    public SimpleNode(Long id)
    {
        this.id = id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public int hashCode()
    {
        //use getters for getting fields(for ORM) see this SO answer:
        //http://stackoverflow.com/questions/27581/what-issues-should-be-considered-when-overriding-equals-and-hashcode-in-java
        int result =17;
        Long id_l = getId();

        int id = (int) (id_l ^ (id_l >>>32));

        result+=id;

        return 31*result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof SimpleNode))
            return false;
        if (obj == this)
            return true;

        SimpleNode rhs = (SimpleNode) obj;
        return this.getId().equals(rhs.getId());
    }

    @Override
    public String toString()
    {
        return id.toString();
    }
}
