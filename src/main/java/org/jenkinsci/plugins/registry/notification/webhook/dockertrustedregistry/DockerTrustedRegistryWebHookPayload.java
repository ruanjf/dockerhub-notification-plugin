/**
 * The MIT License
 *
 * Copyright (c) 2015, HolidayCheck AG.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jenkinsci.plugins.registry.notification.webhook.dockertrustedregistry;

import net.sf.json.JSONObject;
import org.jenkinsci.plugins.registry.notification.webhook.WebHookPayload;

import javax.annotation.Nonnull;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DockerTrustedRegistryWebHookPayload extends WebHookPayload {

    private static final Logger logger = Logger.getLogger(DockerTrustedRegistryPushNotification.class.getName());

    /**
     * Creates the object from the json payload
     *
     * @param data the json payload
     * @throws net.sf.json.JSONException if the key {@code repository.  repo_name} doesn't exist.
     */
    public DockerTrustedRegistryWebHookPayload(@Nonnull JSONObject data) {
        super();
        setData(data);
        setJson(data.toString());
        JSONObject contents = data.getJSONObject("contents");

        if (contents != null) {
            String repository = contents.getString("imageName").split(":")[0];
            String eventType = data.getString("type");
            DockerTrustedRegistryPushNotification pn = new DockerTrustedRegistryPushNotification(this, repository, eventType, contents.getString("imageName").split("/")[0]);
            pn.setImageTag(contents.getString("tag"));
            pushNotifications.add(pn);
        } else {
            logger.log(Level.FINER, "Skipping notification" + data.getString("location"));
        }

    }
}