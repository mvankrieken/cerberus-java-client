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

import static org.junit.Assert.assertEquals;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.junit.Test;

import com.nike.cerberus.client.CerberusClient;
import com.nike.cerberus.client.ClientVersion;

/**
 * Tests the DefaultCerberusClientFactory class
 */
public class DefaultCerberusClientFactoryTest {

    @Test
    public void test_that_getClient_adds_client_version_as_a_default_header() {
        String region = "us-west-2";
        String url = "url";
        CerberusClient result = DefaultCerberusClientFactory.getClient(url, region);
        assertEquals(
                ClientVersion.getClientHeaderValue(),
                result.getDefaultHeaders().get(ClientVersion.CERBERUS_CLIENT_HEADER));
    }
    
    @Test
    public void test_that_getClient_adds_client_version_as_a_default_header_with_factory() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String region = "us-west-2";
        String url = "url";
        SSLContext sslcontext = SSLContext.getInstance("TLSv1");
        sslcontext.init(null,null,null);
        
        final TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init((KeyStore) null);
        final X509TrustManager x509Manager = (X509TrustManager) factory.getTrustManagers()[0];
        
        CerberusClient result = DefaultCerberusClientFactory.getClient(url, region,sslcontext.getSocketFactory(),x509Manager);
        assertEquals(
                ClientVersion.getClientHeaderValue(),
                result.getDefaultHeaders().get(ClientVersion.CERBERUS_CLIENT_HEADER));
    }
    
    @Test
    public void test_that_getV2Client_adds_client_version_as_a_default_header() {
        String region = "us-west-2";
        String url = "url";
        CerberusClient result = DefaultCerberusClientFactory.getV2Client(url, region);
        assertEquals(
                ClientVersion.getClientHeaderValue(),
                result.getDefaultHeaders().get(ClientVersion.CERBERUS_CLIENT_HEADER));
    }
    
    @Test
    public void test_that_getV2Client_adds_client_version_as_a_default_header_with_factory() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String region = "us-west-2";
        String url = "url";
        SSLContext sslcontext = SSLContext.getInstance("TLSv1");
        sslcontext.init(null,null,null);
        
        final TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init((KeyStore) null);
        final X509TrustManager x509Manager = (X509TrustManager) factory.getTrustManagers()[0];
        
        CerberusClient result = DefaultCerberusClientFactory.getV2Client(url, region,sslcontext.getSocketFactory(),x509Manager);
        assertEquals(
                ClientVersion.getClientHeaderValue(),
                result.getDefaultHeaders().get(ClientVersion.CERBERUS_CLIENT_HEADER));
    }

}