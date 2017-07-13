package com.ekocbiyik.javafx.utils;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;

/**
 * Erdal
 * Date: 10.10.2013
 * Time: 11:12
 */
public class SpecialHttpsClient extends DefaultHttpClient {
    protected static Logger logger = LoggerFactory.getLogger(SpecialHttpsClient.class);
    private static int DEFAULT_HTTPS_PORT=443;
    private static SSLSocketFactory sslSocketFactory;
    int port;

    public SpecialHttpsClient(URI uri) {
        super();
        port = uri.getPort();
        if ( port <=0 )
            port=DEFAULT_HTTPS_PORT;
    }

    public SpecialHttpsClient(URI uri, HttpParams params) {
        super(params);
        port = uri.getPort();
        if ( port <=0 )
            port=DEFAULT_HTTPS_PORT;
    }

    @Override
    protected ClientConnectionManager createClientConnectionManager() {

        if(sslSocketFactory ==null) {
            sslSocketFactory = newSslSocketFactory();
        }

        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", sslSocketFactory, port));
        return new SingleClientConnManager(getParams(), registry);
    }

    private SSLSocketFactory newSslSocketFactory() {
        InputStream in =null;

        try {
            KeyStore trusted = KeyStore.getInstance("JKS");
            in = this.getClass().getResourceAsStream("/keystoreFile");
            trusted.load(in, "password11".toCharArray());

            SSLSocketFactory sf = new SSLSocketFactory(trusted);
            sf.setHostnameVerifier(sslSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            return sf;
        } catch (Exception e) {
            logger.error("An error was occurred while creating SSLSocketFactory!***************", e);
            return null;
        } finally {
            if(in!=null )
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("An error was occurred while creating SSLSocketFactory!***************", e);
                }
        }
    }
}
