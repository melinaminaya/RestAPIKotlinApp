package com.example.nanoclientkotlin.consts

class ConstsCommSvc {



    companion object {
        /**
         * Intent utilizado para inicializar o serviço. A inicialização consiste em criar os
         * arquivos necessários ao funcionamento do serviço.
         */
        // private static final String INTENT_SVC_INIT = "br.com.autotrac.jATMobileCommSvc.Init";
        const val INTENT_SVC_INITIALIZE = "br.com.autotrac.jATMobileCommSvc.Initialize"
        /** Intent utilizado para iniciar a execução serviço.  */
        const val INTENT_SVC_START = "br.com.autotrac.jATMobileCommSvc.Start"

        /** Intent utilizado para parar a execução serviço.  */
        const val INTENT_SVC_STOP = "br.com.autotrac.jATMobileCommSvc.Stop"

        /** Intent utilizado para finalizar o serviço.  */
        const val INTENT_SVC_FINALIZE = "br.com.autotrac.jATMobileCommSvc.Finalize"

        /** Package do serviço.  */
        const val INTENT_SVC_PACKAGE_NAME = "br.com.autotrac.jatmobilecommsvc"

        /** Ação se precisa de knox.  */
        const val INTENT_ACTION_NEED_KNOX = "need_knox"

        const val BASE_URL = "http://127.0.0.1:8081"
        const val AUTH_ENDPOINT = "auth"

        const val REQ_MESSAGE_LIST = "messageList"
        const val REQ_MESSAGE_LIST_INBOX = "messageListInbox"
        const val REQ_MESSAGE_LIST_OUTBOX = "messageListOutbox"
        const val REQ_MESSAGE_COUNT = "messageCount"
        const val REQ_MESSAGE_DELETE = "messageDelete"
        const val REQ_MESSAGE_SET_AS_READ = "messageSetAsRead"
        const val REQ_CONFIG_SERVICE_LOG = "configServiceLog"
        const val REQ_FILE_OPERATION = "fileOperation"
        const val REQ_FORM_LIST = "formList"
        const val REQ_GET_CHECKLIST = "getCheckList"
        const val REQ_GET_CURRENT_DATE = "getCurrentDate"
        const val REQ_GET_MCT_PARAMETERS = "getMctParameters"
        const val REQ_GET_POSITION_LAST = "getPositionLast"
        const val REQ_POSITION_HISTORY_COUNT = "positionHistoryCount"
        const val REQ_POSITION_HISTORY_LIST = "positionHistoryList"
        const val REQ_RESET_DATABASE = "resetDatabase"
    }
}