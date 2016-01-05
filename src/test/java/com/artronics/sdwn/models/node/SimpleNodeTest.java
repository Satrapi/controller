package com.artronics.sdwn.models.node;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class SimpleNodeTest
{
    SimpleNode aNode;
    SimpleNode anotherNode;

    @Before
    public void setUp() throws Exception
    {
        aNode = new SimpleNode(1L);
        anotherNode = new SimpleNode(1L);
    }

    @Test
    public void test_equals(){
        assertTrue(aNode.equals(anotherNode));
        assertTrue(anotherNode.equals(aNode));

        aNode.setId(2L);

        assertFalse(aNode.equals(anotherNode));
        assertFalse(anotherNode.equals(aNode));
    }
    
    @Test
    public void test_hashCode(){
        assertThat(aNode.hashCode(),equalTo(anotherNode.hashCode()));
        assertThat(anotherNode.hashCode(),equalTo(aNode.hashCode()));
    }
}