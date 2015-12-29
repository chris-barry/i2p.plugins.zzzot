package net.i2p.zzzot;
/*
 *  Copyright 2010 zzz (zzz@mail.i2p)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

public class TorrentsTest {

    private Torrents torrents;
    
    private static final int INTERVAL = 5;
    private static final int NUM_PEERS = 100;
    private static final String BAD_DATA = "aaa";
    private static final String GOOD_DATA = "aaaaaaaaaaaaaaaaaaaa";
    
    @Mock
    private InfoHash infoHash;
    
    @Mock
    private Peers peers;
    
    @Mock
    private Peer peer;
    
    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	torrents = new Torrents(INTERVAL);
    }

    @Test
    public void shouldGetInterval() throws Exception {
	assertThat("Interval should be " + INTERVAL, torrents.getInterval(), equalTo(INTERVAL));
    }
    
    @Test
    public void shouldHaveZeroPeers() throws Exception {
	assertThat(torrents.countPeers(), equalTo(0));
    }
    
    @Test
    public void shouldHaveOnePeer1111() throws Exception {
	torrents.put(infoHash, peers);
	assertThat(torrents.size(), equalTo(1));
    }
    
    @Test
    public void shouldClear() throws Exception {
	torrents.put(infoHash, peers);
	torrents.clear();
	assertThat("There should be no peers", torrents.countPeers(), equalTo(0));
    }
    
    @Test
    public void shouldHaveOnePeer() throws Exception {
	when(peers.size()).thenReturn(NUM_PEERS);
	torrents.put(infoHash, peers);
	assertThat("There should be no peers", torrents.countPeers(), equalTo(NUM_PEERS));
    }
    

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenMakingNewInfoHash() throws Exception {
	torrents.createInfoHash(BAD_DATA);
    }
    
    @Test
    public void shouldFetchNewInfoHash() throws Exception {
	assertNotNull("InfoHash should not be null", torrents.createInfoHash(GOOD_DATA));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenMakingNewPID() throws Exception {
	torrents.createPID(BAD_DATA);
    }
    
    @Test
    public void shouldFetchNewPID() throws Exception {
	assertNotNull("PID should not be null", torrents.createPID(GOOD_DATA));
    }
    
}
