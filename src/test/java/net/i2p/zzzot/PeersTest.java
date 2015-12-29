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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PeersTest {

    private Peers peers;

    @Mock
    private Peer seedingPeer;

    @Mock
    private Peer leechingPeer;

    @Mock
    private PID seed_pid;

    @Mock
    private PID leech_pid;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	peers = new Peers();

	when(seedingPeer.isSeed()).thenReturn(true);
	when(leechingPeer.isSeed()).thenReturn(false);

	peers.put(seed_pid, seedingPeer);
	peers.put(leech_pid, leechingPeer);
    }

    @Test
    public void shouldCountSeeds() {
	assertThat("There should be 1 seeder", peers.countSeeds(), equalTo(1));
    }

    @Test
    public void shouldCountLeeches() {
	assertThat("There should be 1 leecher", peers.countLeeches(), equalTo(1));
    }
}
