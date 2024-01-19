//[app](../../../index.md)/[br.com.autotrac.testnanoclient.models](../index.md)/[IntegrationMessage](index.md)

# IntegrationMessage

data class [IntegrationMessage](index.md)(val code: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, val isForward: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val msgStatusNum: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val msgSubtype: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val formCode: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val body: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val bodyBytes: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)?, var createdTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?, val sendReceivedTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?, val deliveryTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?, val gmn: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, val isOutOfBandMsg: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val outOfBandFilename: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val outOfBandMsgStatusNum: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, val sourceAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val destAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val lastStatusTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?, val msgPriority: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val replyGmn: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, val positionLatitude: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, val positionLongitude: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, val positionTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?, val positionSpeed: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, val positionHeading: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)?, val positionAging: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, val transmissionChannel: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val transmittedChannel: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, val transmissionType: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))

Objeto de envio e de retorno das requisições que envolvem mensagens.

#### See also

| |
|---|
| [ApiEndpoints.REQ_MESSAGE_LIST](../../br.com.autotrac.testnanoclient.consts/-api-endpoints/-r-e-q_-m-e-s-s-a-g-e_-l-i-s-t.md) |
| [ApiEndpoints.REQ_MESSAGE_DELETE](../../br.com.autotrac.testnanoclient.consts/-api-endpoints/-r-e-q_-m-e-s-s-a-g-e_-d-e-l-e-t-e.md) |
| [ApiEndpoints.SEND_MESSAGE](../../br.com.autotrac.testnanoclient.consts/-api-endpoints/-s-e-n-d_-m-e-s-s-a-g-e.md) |
| [ApiEndpoints.SEND_FILE_MESSAGE](../../br.com.autotrac.testnanoclient.consts/-api-endpoints/-s-e-n-d_-f-i-l-e_-m-e-s-s-a-g-e.md) |

## Constructors

| | |
|---|---|
| [IntegrationMessage](-integration-message.md) | [androidJvm]<br>constructor(code: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, isForward: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), msgStatusNum: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), msgSubtype: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), formCode: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, body: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, bodyBytes: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)?, createdTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?, sendReceivedTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?, deliveryTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?, gmn: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, isOutOfBandMsg: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), outOfBandFilename: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, outOfBandMsgStatusNum: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, sourceAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, destAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, lastStatusTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?, msgPriority: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), replyGmn: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, positionLatitude: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, positionLongitude: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, positionTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?, positionSpeed: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, positionHeading: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)?, positionAging: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, transmissionChannel: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), transmittedChannel: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, transmissionType: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [body](body.md) | [androidJvm]<br>val [body](body.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [bodyBytes](body-bytes.md) | [androidJvm]<br>val [bodyBytes](body-bytes.md): [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)? |
| [code](code.md) | [androidJvm]<br>val [code](code.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? |
| [createdTime](created-time.md) | [androidJvm]<br>var [createdTime](created-time.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? |
| [deliveryTime](delivery-time.md) | [androidJvm]<br>val [deliveryTime](delivery-time.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? |
| [destAddress](dest-address.md) | [androidJvm]<br>val [destAddress](dest-address.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [formCode](form-code.md) | [androidJvm]<br>val [formCode](form-code.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [gmn](gmn.md) | [androidJvm]<br>val [gmn](gmn.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? |
| [isForward](is-forward.md) | [androidJvm]<br>val [isForward](is-forward.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isOutOfBandMsg](is-out-of-band-msg.md) | [androidJvm]<br>val [isOutOfBandMsg](is-out-of-band-msg.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [lastStatusTime](last-status-time.md) | [androidJvm]<br>val [lastStatusTime](last-status-time.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? |
| [msgPriority](msg-priority.md) | [androidJvm]<br>val [msgPriority](msg-priority.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [msgStatusNum](msg-status-num.md) | [androidJvm]<br>val [msgStatusNum](msg-status-num.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [msgSubtype](msg-subtype.md) | [androidJvm]<br>val [msgSubtype](msg-subtype.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [outOfBandFilename](out-of-band-filename.md) | [androidJvm]<br>val [outOfBandFilename](out-of-band-filename.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [outOfBandMsgStatusNum](out-of-band-msg-status-num.md) | [androidJvm]<br>val [outOfBandMsgStatusNum](out-of-band-msg-status-num.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? |
| [positionAging](position-aging.md) | [androidJvm]<br>val [positionAging](position-aging.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? |
| [positionHeading](position-heading.md) | [androidJvm]<br>val [positionHeading](position-heading.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)? |
| [positionLatitude](position-latitude.md) | [androidJvm]<br>val [positionLatitude](position-latitude.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? |
| [positionLongitude](position-longitude.md) | [androidJvm]<br>val [positionLongitude](position-longitude.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? |
| [positionSpeed](position-speed.md) | [androidJvm]<br>val [positionSpeed](position-speed.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? |
| [positionTime](position-time.md) | [androidJvm]<br>val [positionTime](position-time.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? |
| [replyGmn](reply-gmn.md) | [androidJvm]<br>val [replyGmn](reply-gmn.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? |
| [sendReceivedTime](send-received-time.md) | [androidJvm]<br>val [sendReceivedTime](send-received-time.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? |
| [sourceAddress](source-address.md) | [androidJvm]<br>val [sourceAddress](source-address.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [subject](subject.md) | [androidJvm]<br>val [subject](subject.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [transmissionChannel](transmission-channel.md) | [androidJvm]<br>val [transmissionChannel](transmission-channel.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [transmissionType](transmission-type.md) | [androidJvm]<br>val [transmissionType](transmission-type.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [transmittedChannel](transmitted-channel.md) | [androidJvm]<br>val [transmittedChannel](transmitted-channel.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? |

## Functions

| Name | Summary |
|---|---|
| [equals](equals.md) | [androidJvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [androidJvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
