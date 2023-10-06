package br.com.autotrac.testnanoclient.security

import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.Date
import javax.net.ssl.X509TrustManager

/**
 * Gerenciamento de certificados SSL.
 * Por enquanto faz o bypass de autorizar todos os certificados.
 * @author Melina Minaya
 */
object SSLSetup{
    val trustAllCertificates: X509TrustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            if (chain != null && chain.isNotEmpty()) {
                val serverCert = chain[0] // The server's certificate

                // Check the certificate's expiration date
                val currentDate = Date()
                if (currentDate.after(serverCert.notAfter)) {
                    throw CertificateException("Server certificate has expired.")
                }

                // Perform additional validation checks as needed
                // ...

                // If all checks pass, the certificate is trusted
            } else {
                throw CertificateException("Empty certificate chain.")
            }
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return emptyArray() // Return an empty array to trust all issuers
        }
    }
}