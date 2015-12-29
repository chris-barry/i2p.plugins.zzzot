package net.i2p.zzzot;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.i2p.I2PAppContext;
import net.i2p.util.Clock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Clock.class })
public class ZzzOTTest {

    private ZzzOT zzzot;

    private final static String BAD_INTERVAL = "itoopie";
    private final static long CURRENT_TIME = 100l;

    @Mock
    I2PAppContext ctx;

    @Mock
    Properties properties;

    @Mock
    InfoHash infoHash;

    @Mock
    Peers peers;

    @Mock
    Peer peer;

    @Mock
    Clock clock;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	zzzot = new ZzzOT(ctx, properties);
    }

    @Test(expected = AssertionError.class)
    @Ignore("it passes, but says \"java.lang.AssertionError: Expected exception: java.lang.AssertionError\"")
    public void shouldThrowNumberFormatException() throws Exception {
	when(properties.getProperty(ZzzOT.PROP_INTERVAL)).thenReturn(BAD_INTERVAL);
	zzzot = new ZzzOT(ctx, properties);
	assertNotNull(zzzot);
    }

    @Test
    public void shouldWorkWithNullInterval() throws Exception {
	when(properties.getProperty(ZzzOT.PROP_INTERVAL)).thenReturn(null);
	zzzot = new ZzzOT(ctx, properties);
	assertNotNull(zzzot);
    }

    @Test
    public void shouldGoToMinInterval() throws Exception {
	when(properties.getProperty(ZzzOT.PROP_INTERVAL)).thenReturn(Integer.toString(ZzzOT.MIN_INTERVAL - 1));
	// assertThat(zzzot.EXPIRE_TIME,
	// equalTo(ZzzOT.MIN_INTERVAL*ZzzOT.EXPIRE_MULT));
	zzzot = new ZzzOT(ctx, properties);
	assertNotNull(zzzot);
    }

    @Test
    public void shouldGoToMaxInterval() throws Exception {
	when(properties.getProperty(ZzzOT.PROP_INTERVAL)).thenReturn(Integer.toString(ZzzOT.MAX_INTERVAL + 1));
	// assertThat(zzzot.EXPIRE_TIME,
	// equalTo(ZzzOT.MAX_INTERVAL*ZzzOT.EXPIRE_MULT));
	zzzot = new ZzzOT(ctx, properties);
	assertNotNull(zzzot);
    }

    @Test
    public void shouldProperlySetInterval() throws Exception {
	when(properties.getProperty(ZzzOT.PROP_INTERVAL)).thenReturn(Integer.toString(ZzzOT.DEFAULT_INTERVAL));
	zzzot = new ZzzOT(ctx, properties);
	// assertThat(zzzot.EXPIRE_TIME,
	// equalTo(ZzzOT.DEFAULT_INTERVAL*ZzzOT.EXPIRE_MULT));
	assertNotNull(zzzot);
    }

    @Test
    public void shouldReturnTorrents() throws Exception {
	assertThat(zzzot.getTorrents(), isA(Torrents.class));
    }

    @Test
    public void shouldCacheDest() throws Exception {
	assertThat(zzzot.getDestCache(), isA(ConcurrentHashMap.class));
    }

    @Test
    @Ignore("Not sure I know how to test this properly.")
    public void shouldStart() throws Exception {
	zzzot = new ZzzOT(ctx, properties);
	zzzot.start();
    }

    @Test
    public void shouldStopAndClear() throws Exception {
	zzzot.stop();
	assertThat(zzzot.getDestCache().size(), equalTo(0));
	assertThat(zzzot.getTorrents().size(), equalTo(0));
    }

/*
    // NOTE: Schedule is returning a null pointer issue for some reason.
    // NOTE: I am having a hard time 
    @Test
    public void shouldForceClean() throws Exception {
	long time = CURRENT_TIME - zzzot.getExpireTime();

	// Mock the clock.
	PowerMockito.mockStatic(Clock.class);
	PowerMockito.when(clock.now()).thenReturn(CURRENT_TIME);
	PowerMockito.when(Clock.getInstance()).thenReturn(clock);

	// Make a fake set of peers.
	LinkedList<Peers> pp = new LinkedList<Peers>();
	pp.add(peers);

	LinkedList<Peer> pi = new LinkedList<Peer>();
	pi.add(peer);
	pi.add(peer);

	// Mock the peers.
	when(peers.values()).thenReturn(pi);
	when(peer.lastSeen()).thenReturn(time - 1,time + 2);
//	when(zzzot.getTorrents()).thenReturn(value)
	
	zzzot = new ZzzOT(ctx, properties);

	zzzot.getTorrents().put(infoHash, peers);
	
	assertThat("We should have one torrent", zzzot.getTorrents().size(), equalTo(1));
	
	zzzot.timeReached();
	
//	assertThat("We should have one torrent", zzzot.getTorrents().countPeers(), equalTo(1));
    }

    @Test
    public void shouldForceCleanAndRemoveTorrent() throws Exception {
	long time = CURRENT_TIME - zzzot.getExpireTime();

	// Mock the clock.
	PowerMockito.mockStatic(Clock.class);
	PowerMockito.when(clock.now()).thenReturn(CURRENT_TIME);
	PowerMockito.when(Clock.getInstance()).thenReturn(clock);

	// Make a fake set of peers.
	LinkedList<Peers> pp = new LinkedList<Peers>();
	pp.add(peers);

	LinkedList<Peer> pi = new LinkedList<Peer>();
	pi.add(peer);
	pi.add(peer);

	// Mock the peers.
	when(peers.values()).thenReturn(pi);
	when(peer.lastSeen()).thenReturn(time - 1, time - 1);
	
	zzzot = new ZzzOT(ctx, properties);

	zzzot.getTorrents().put(infoHash, peers);
	
	assertThat("We should have one torrent", zzzot.getTorrents().size(), equalTo(1));
	
	zzzot.timeReached();

//	assertThat(zzzot.getTorrents().countPeers(), equalTo(1));
    }
*/

}
