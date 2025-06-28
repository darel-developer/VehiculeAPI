package com.propelize.vehicleapi.config;

import org.springframework.context.annotation.Configuration;
import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Configuration
public class SslConfig {

    public static void configureSsl() throws Exception {
        // Chemin vers le certificat CA
        String certPath = "src/main/resources/cert/ca.pem";
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream(new File(certPath));
        X509Certificate caCert = (X509Certificate) cf.generateCertificate(fis);

        // Créer un keystore
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca-cert", caCert);

        // Configurer le trust manager
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        // Appliquer au SSL context
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        SSLContext.setDefault(sslContext);
    }
}