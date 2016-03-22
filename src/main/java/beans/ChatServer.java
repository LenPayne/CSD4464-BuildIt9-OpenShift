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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@ApplicationScoped
@ServerEndpoint("/chat")
public class ChatServer {

    List<Session> people = new ArrayList<>();
    List<JsonObject> messages = new ArrayList<>();

    @OnMessage
    public void onMessage(String str, Session session) throws IOException {
        if (!people.contains(session)) {
            people.add(session);
        }
        JsonObject json = Json.createReader(new StringReader(str)).readObject();
        messages.add(json);
        for (Session s : people) {
            RemoteEndpoint.Basic basic = s.getBasicRemote();
            String output = Json.createArrayBuilder().add(json).build().toString();
            basic.sendText(output);
        }
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        if (!people.contains(session)) {
            people.add(session);
        }
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (JsonObject json : messages) {
            arr.add(json);
        }
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        basic.sendText(arr.build().toString());
    }

}
