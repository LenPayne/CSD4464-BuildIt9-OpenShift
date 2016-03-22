/*
 * Copyright 2016 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package beans;

import java.io.IOException;
import java.util.Date;
import javax.websocket.OnMessage;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@ServerEndpoint("/sample")
public class SampleServer {

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println(new Date().toString() + ": " + message);
        Basic basic = session.getBasicRemote();
        basic.sendText(new Date() + ": " + message);
    }

}
