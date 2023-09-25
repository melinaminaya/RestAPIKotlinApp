package com.example.nanoclientkotlin.security

import android.content.Context
import com.example.nanoclientkotlin.R
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object SSLSetup{
    var certificateTrusted:X509Certificate? = null
    val trustAllCertificatesTrustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
            // No-op: Trust all client certificates
        }

        override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
            // No-op: Trust all server certificates
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
            return emptyArray()
        }
    }

    // Create an SSLContext with the custom TrustManager
    val customSSLContext: SSLContext = SSLContext.getInstance("TLS")
    fun loadTrustedCertificate(context:Context): X509Certificate {
        // Implement code to load the trusted certificate here
        // You may read it from a file or a resource
        // Example:
         val certificateInputStream = context.resources.openRawResource(R.raw.autotracapicertificate)
         val certificateFactory = CertificateFactory.getInstance("X.509")
        certificateTrusted = certificateFactory.generateCertificate(certificateInputStream) as X509Certificate
         return certificateTrusted as X509Certificate
    }
    // Create a custom trust manager that trusts a specific certificate
    fun createCustomTrustManager(trustedCertificate: X509Certificate): X509TrustManager {
        return object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                // Implement client certificate validation if needed
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                // Validate the server's certificate
                chain?.forEach { cert ->
                    try {
                        cert.verify(trustedCertificate.publicKey)
                    } catch (e: Exception) {
                        throw CertificateException("Certificate validation failed")
                    }
                }
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }
}