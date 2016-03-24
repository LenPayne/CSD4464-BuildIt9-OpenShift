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
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonArray;
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
@ServerEndpoint("/chat")
public class ChatServer {

    // Injects the @ApplicationScoped controller into all server instances
    @Inject
    private ChatController chatCtrl;

    @OnMessage
    public void onMessage(String str, Session session) throws IOException {
        // Adds new sessions to the controller/chat-room
        if (!chatCtrl.containsSession(session)) {
            chatCtrl.addSession(session);
        }

        // Parses the incoming JSON and adds it to the controller/chat-room
        JsonObject json = Json.createReader(new StringReader(str)).readObject();
        chatCtrl.addMessage(json);

        // Connects to all sessions and sends them the latest message
        for (Session s : chatCtrl.getSessions()) {
            RemoteEndpoint.Basic basic = s.getBasicRemote();
            String output = Json.createArrayBuilder().add(json).build().toString();
            basic.sendText(output);
        }
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        // Adds new sessions to the controller/chat-room
        if (!chatCtrl.containsSession(session)) {
            chatCtrl.addSession(session);
        }

        // Builds a JSON Array containing all registered messages
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (JsonObject json : chatCtrl.getMessages()) {
            arr.add(json);
        }
        JsonArray output = arr.build();

        // Sends the JSON Array out as a message history
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        System.out.println("Connected to " + session.getId() + " and sending: " + output.toString());
        basic.sendText(output.toString());
    }

}
