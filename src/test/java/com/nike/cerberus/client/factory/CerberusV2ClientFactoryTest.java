/*
 * Copyright (c) 2018 Nike, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nike.cerberus.client.factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.nike.cerberus.client.CerberusClient;
import com.nike.cerberus.client.auth.CerberusCredentials;
import com.nike.cerberus.client.auth.CerberusCredentialsProvider;
import com.nike.cerberus.client.auth.TokenCerberusCredentials;

/**
 * Tests the CerberusClientFactoryTest class
 */
public class CerberusV2ClientFactoryTest {

    private final String url = "https://localhost/";

    private final String TOKEN = "TOKEN";

    private final CerberusCredentialsProvider credentialsProvider = new CerberusCredentialsProvider() {

        @Override
        public CerberusCredentials getCredentials() {
            return new TokenCerberusCredentials(TOKEN);
        }
        
        public boolean shouldRun() {
            return true;
        }
    };

    @Test
    public void test_get_v2client_uses_url_and_creds_provider() {
        final CerberusClient client = CerberusV2ClientFactory.getClient(url, credentialsProvider);
        assertThat(client).isNotNull();
        assertThat(client.getCerberusUrl().url().toString()).isEqualTo(url);
        assertThat(client.getCredentialsProvider()).isNotNull();
        assertThat(client.getCredentialsProvider().getCredentials().getToken()).isEqualTo(TOKEN);
    }

    @Test
    public void test_get_v2client_uses_default_headers() {
        final String headerKey = "HeaderKey";
        final String headerValue = "header value";
        final Map<String, String> defaultHeaders = new HashMap<>();
        defaultHeaders.put(headerKey, headerValue);
        final CerberusClient client = CerberusV2ClientFactory.getClient(url, credentialsProvider, defaultHeaders);
        assertThat(client).isNotNull();
        assertThat(client.getCerberusUrl().url().toString()).isEqualTo(url);
        assertThat(client.getCredentialsProvider()).isNotNull();
        assertThat(client.getCredentialsProvider().getCredentials().getToken()).isEqualTo(TOKEN);
        assertThat(client.getDefaultHeaders().size()).isEqualTo(1);
        assertThat(client.getDefaultHeaders().get(headerKey)).isEqualTo(headerValue);
    }

    @Test
    public void test_get_admin_v2client_uses_all_parameters() {
        final String headerKey = "HeaderKey";
        final String headerValue = "header value";
        final Map<String, String> defaultHeaders = new HashMap<>();
        defaultHeaders.put(headerKey, headerValue);
        final CerberusClient client = CerberusV2ClientFactory.getClient(url, credentialsProvider, 100, defaultHeaders);
        assertThat(client).isNotNull();
        assertThat(client.getCerberusUrl().url().toString()).isEqualTo(url);
        assertThat(client.getCredentialsProvider()).isNotNull();
        assertThat(client.getCredentialsProvider().getCredentials().getToken()).isEqualTo(TOKEN);
        assertThat(client.getDefaultHeaders().size()).isEqualTo(1);
        assertThat(client.getDefaultHeaders().get(headerKey)).isEqualTo(headerValue);
    }

}