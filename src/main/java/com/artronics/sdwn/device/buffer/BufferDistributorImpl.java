package com.artronics.sdwn.device.buffer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class BufferDistributorImpl implements BufferDistributor
{
    private final static Logger log = Logger.getLogger(BufferDistributorImpl.class);

    private InputStream input;

    @Override
    public void bufferReceived()
    {

    }

    @Override
    public void setInput(InputStream input)
    {
        this.input = input;
    }
}
