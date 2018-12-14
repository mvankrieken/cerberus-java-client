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

package com.nike.cerberus.client.auth;
import com.nike.cerberus.client.auth.aws.StsCerberusCredentialsProvider;
import okhttp3.OkHttpClient;

/**
 * Default credentials provider chain that will attempt to retrieve a token in the following order:
 * <p>
 * <ul>
 * <li>Environment Variable - <code>CERBERUS_TOKEN</code></li>
 * <li>Java System Property - <code>cerberus.token</code></li>
 * <li>STS authentication using current IAM role</li>
 * </ul>
 *
 * @see EnvironmentCerberusCredentialsProvider
 * @see SystemPropertyCerberusCredentialsProvider
 * @see StsCerberusCredentialsProvider
 */
public class DefaultCerberusCredentialsProviderChain extends CerberusCredentialsProviderChain {

    /**
     * Constructor that sets up a provider chain using the specified implementation of UrlResolver.
     *
     * @param url Cerberus URL
     * @param region AWS region
     */
    public DefaultCerberusCredentialsProviderChain(String url, String region) {
        super(
                new EnvironmentCerberusCredentialsProvider(),
                new SystemPropertyCerberusCredentialsProvider(),
                new StsCerberusCredentialsProvider(url, region)
        );
    }

    /**
     * Constructor that sets up a provider chain using the specified implementation of UrlResolver.
     *
     * @param url Cerberus URL
     * @param region AWS region
     * @param xCerberusClientOverride - Overrides the default header value for the 'X-Cerberus-Client' header
     */
    public DefaultCerberusCredentialsProviderChain(String url, String region, String xCerberusClientOverride) {
        super(
                new EnvironmentCerberusCredentialsProvider(),
                new SystemPropertyCerberusCredentialsProvider(),
                new StsCerberusCredentialsProvider(url, region, xCerberusClientOverride)
        );
    }

    /**
     * Constructor that sets up a provider chain using the specified implementation of UrlResolver
     * and OkHttpClient
     *
     * @param url         Cerberus URL
     * @param httpClient  the client to use
     */
    public DefaultCerberusCredentialsProviderChain(String url, String region, OkHttpClient httpClient) {
        super(new EnvironmentCerberusCredentialsProvider(),
                new SystemPropertyCerberusCredentialsProvider(),
                new StsCerberusCredentialsProvider(url, region, httpClient));
    }
}
