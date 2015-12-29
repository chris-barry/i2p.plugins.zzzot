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
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.i2p.I2PAppContext;;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ I2PAppContext.class })
public class ZzzOTControllerTest {
    private ZzzOTController zzzOTController;

    @Mock // lazygravy is having trouble mocking this :(
    private I2PAppContext ctx;

    // @Mock
    private String[] args;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
//	I2PAppContext a = new I2PAppContext();
//	ctx = I2PAppContext.getGlobalContext();
	args = new String[2];
	zzzOTController = new ZzzOTController(ctx, ctx.clientAppManager(), args);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowUnsupportedOperationExceptionWhenMainCalled() throws Exception {
	ZzzOTController.main(args);
    }

    @Test
    public void shouldReturnNullWithNoManagerTorrents() throws Exception {
	assertNull(ZzzOTController.getTorrents());
    }
    
    @Test
    @Ignore("not working right now")
    public void shouldReturnNullWithManagerNoClientAppTorrents() throws Exception {
	assertNull(ZzzOTController.getTorrents());
    }
    
    @Test
    @Ignore("not working right now")
    public void shouldGetTorrents() throws Exception {
	PowerMockito.mockStatic(I2PAppContext.class);
	assertThat(ZzzOTController.getTorrents(), isA(Torrents.class));
    }
    
    @Test
    public void shouldReturnNullCache() throws Exception {
	assertNull(ZzzOTController.getDestCache());
    }

    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    @Test
    public void shouldStart() {
	zzzOTController.startup();
    }
    
    @Test
    public void shouldShutdown() {
	zzzOTController.shutdown(args);
    }
    
    @Test
    public void shouldGetName() throws Exception {
	assertThat(zzzOTController.getName(), equalTo(ZzzOTController.NAME));
    }

    @Test
    public void shouldGetDisplayName() throws Exception {
	assertThat(zzzOTController.getDisplayName(), equalTo(ZzzOTController.NAME));
    }

    @Test
    public void shouldReturnNullWithNoMClientApp() throws Exception {
	// when(mgr.getRegisteredApp(ZzzOTController.NAME))
	// zzzOTController = new ZzzOTController(ctx,mgr,args);
	// assertNull(ZzzOTController.getTorrents());
    }

    /*
     * @Test public void shouldBeGood() throws Exception {
     * zzzOTController.setClientAppManager(clientAppManager);
     * assertNotNull(ZzzOTController.getTorrents()); }
     */
}
