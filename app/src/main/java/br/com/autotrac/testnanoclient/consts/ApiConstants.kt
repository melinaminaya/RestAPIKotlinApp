package br.com.autotrac.testnanoclient.consts

class ApiConstants {
    companion object {
        /**
         * Intent utilizado para inicializar o serviço.
         * Consiste em criar arquivos cruciais ao funcionamento do serviço.
         */
        // private static final String INTENT_SVC_INIT = "br.com.autotrac.jATMobileCommSvc.Init";
        const val INTENT_SVC_INITIALIZE:String = "br.com.autotrac.jATMobileCommSvc.Initialize"

        /** Intent utilizado para iniciar o funcionamento do serviço.  */
        const val INTENT_SVC_START:String = "br.com.autotrac.jATMobileCommSvc.Start"

        /** Intent utilizado para parar o funcionamento do serviço.  */
        const val INTENT_SVC_STOP:String = "br.com.autotrac.jATMobileCommSvc.Stop"

        /** Intent utilizado para finalizar o serviço.  */
        const val INTENT_SVC_FINALIZE:String = "br.com.autotrac.jATMobileCommSvc.Finalize"

        /** Package do serviço.  */
        const val INTENT_SVC_PACKAGE_NAME:String = "br.com.autotrac.jatmobilecommsvc"

        /** Classe do serviço.  */
        const val SERVICE_CLASSNAME:String = "br.com.autotrac.service.ATMobileCommSvc"

        /** Intent utilizado para o knox.  */
        const val INTENT_ACTION_NEED_KNOX:String = "need_knox"

        /** URL da API */
        const val BASE_URL:String = "https://localhost:8081"

        /** Endpoint de Autenticação */
        const val AUTH_ENDPOINT:String = "auth"
    }
}