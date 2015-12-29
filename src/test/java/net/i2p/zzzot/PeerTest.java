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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.concurrent.ConcurrentMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.i2p.data.Base64;
import net.i2p.data.Destination;
import net.i2p.util.Clock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Clock.class })
public class PeerTest {
    
    private static final long SEED_DATA_SIZE = 0l;
    private static final long DOWNLOAD_DATA_SIZE = 1l;
    private static final long LAST_SEEN = 111l;
    private static final byte[] GOOD_ID = "aaaaaaaaaaaaaaaaaaaa".getBytes();
    private static final byte[] BAD_ID = "a".getBytes();
    private static final byte[] GOOD_HASH = new byte[] { -62, -81, 0, 15, -61, -108, 101, 0, -61, -88, -61, -119, -61,
	    -80, 110, -61, -78, 115, 28, 77, 93, 107, 8, 69, 112, 9, 10, -62, -67, 51, -62, -89, 30, -62, -86, -61, -93,
	    114, 50, -62, -84, -62, -70, 120 };

    private Peer peer;

    @Mock
    private Destination dest;

    @Mock
    private ConcurrentMap<String, String> destCache;

    @Mock
    private Clock clock;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	peer = new Peer(GOOD_ID, dest, destCache);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() throws Exception {
	peer = new Peer(BAD_ID, dest, destCache);
    }

    @Test
    public void shouldMakeNewPeer() throws Exception {
	when(destCache.putIfAbsent("null.i2p", "null.i2p")).thenReturn("not-null");
	peer = new Peer(GOOD_ID, dest, destCache);
    }

    @Test
    public void shouldBeSeed() throws Exception {
	peer.setLeft(SEED_DATA_SIZE);
	assertTrue("Peer should be a seed", peer.isSeed());
    }

    @Test
    public void shouldNotBeSeed() throws Exception {
	peer.setLeft(DOWNLOAD_DATA_SIZE);
	assertFalse("Peer should not be a seed", peer.isSeed());
    }

    @Test
    public void shouldGetLastSeen() throws Exception {
	PowerMockito.mockStatic(Clock.class);
	Mockito.when(clock.now()).thenReturn(LAST_SEEN);
	PowerMockito.when(Clock.getInstance()).thenReturn(clock);
	
	peer.setLeft(DOWNLOAD_DATA_SIZE);
	assertThat("Last seen time is incorrect", peer.lastSeen(), equalTo(LAST_SEEN));
    }

    @Test
    public void shouldGetHash() throws Exception {
	when(peer.get("ip")).thenReturn(Base64.encode("foo.i2p"));
	byte[] s = peer.getHash().getBytes();
	// s.getBytes("ISO-8859-1").toString()
	assertThat("Hash not equal", s, equalTo(GOOD_HASH));
    }

    // @Test(expected = UnsupportedEncodingException.class)
    // public void shouldThrowUnsupportedEncodingException() throws Exception {
    // assertThat("Hash not equal", peer.getHash().getBytes(), equalTo(new
    // byte[]{1,2,3}));
    // }
}
