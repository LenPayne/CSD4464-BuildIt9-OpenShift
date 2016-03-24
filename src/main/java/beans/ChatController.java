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

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.websocket.Session;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@ApplicationScoped
public class ChatController {

    // Holders for all active connections and all past messages
    List<Session> sessions = new ArrayList<>();
    List<JsonObject> messages = new ArrayList<>();

    /**
     * Add a session to the list of active WebSocket sessions
     *
     * @param s the Session
     */
    public void addSession(Session s) {
        sessions.add(s);
    }

    /**
     * Retrieve the whole list of active WebSocket sessions
     *
     * @return the list of active WebSocket sessions
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /**
     * Check if the given WebSocket session is already connected
     *
     * @param s the Session
     * @return true/false for "Is this WebSocket Session in the list?"
     */
    public boolean containsSession(Session s) {
        return sessions.contains(s);
    }

    /**
     * Add a message to the list of messages
     *
     * @param j the Message
     */
    public void addMessage(JsonObject j) {
        messages.add(j);
    }

    /**
     * Retrieve the whole list of messages
     *
     * @return the whole list of messages
     */
    public List<JsonObject> getMessages() {
        return messages;
    }
}
