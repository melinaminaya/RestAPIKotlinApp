//[app](../../../index.md)/[br.com.autotrac.testnanoclient.vm](../index.md)/[CustomDateDeserializer](index.md)

# CustomDateDeserializer

[androidJvm]\
class [CustomDateDeserializer](index.md) : StdDeserializer&lt;[Date](https://developer.android.com/reference/kotlin/java/util/Date.html)&gt;

## Constructors

| | |
|---|---|
| [CustomDateDeserializer](-custom-date-deserializer.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [deserialize](deserialize.md) | [androidJvm]<br>open override fun [deserialize](deserialize.md)(parser: JsonParser, context: DeserializationContext): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? |

## Inherited functions

| Name | Summary |
|---|---|
| [deserialize](index.md#-1366966549%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [deserialize](index.md#-1366966549%2FFunctions%2F-912451524)(p0: JsonParser, p1: DeserializationContext, p2: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) |
| [deserializeWithType](index.md#-1748245774%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [deserializeWithType](index.md#-1748245774%2FFunctions%2F-912451524)(p0: JsonParser, p1: DeserializationContext, p2: TypeDeserializer, p3: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)<br>open override fun [deserializeWithType](index.md#-839578978%2FFunctions%2F-912451524)(p0: JsonParser, p1: DeserializationContext, p2: TypeDeserializer): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html) |
| [findBackReference](index.md#1438700766%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [findBackReference](index.md#1438700766%2FFunctions%2F-912451524)(p0: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): SettableBeanProperty |
| [getAbsentValue](index.md#-390729380%2FFunctions%2F-912451524) | [androidJvm]<br>open override fun [getAbsentValue](index.md#-390729380%2FFunctions%2F-912451524)(p0: DeserializationContext): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html) |
| [getDelegatee](index.md#-1050556161%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getDelegatee](index.md#-1050556161%2FFunctions%2F-912451524)(): JsonDeserializer&lt;*&gt; |
| [getEmptyAccessPattern](index.md#2004370652%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getEmptyAccessPattern](index.md#2004370652%2FFunctions%2F-912451524)(): AccessPattern |
| [getEmptyValue](index.md#2066120599%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [~~getEmptyValue~~](index.md#2066120599%2FFunctions%2F-912451524)(): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)<br>open fun [getEmptyValue](index.md#-1621668596%2FFunctions%2F-912451524)(p0: DeserializationContext): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html) |
| [getKnownPropertyNames](index.md#808020811%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getKnownPropertyNames](index.md#808020811%2FFunctions%2F-912451524)(): [MutableCollection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-collection/index.html)&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; |
| [getNullAccessPattern](index.md#-96796966%2FFunctions%2F-912451524) | [androidJvm]<br>open override fun [getNullAccessPattern](index.md#-96796966%2FFunctions%2F-912451524)(): AccessPattern |
| [getNullValue](index.md#-1752557675%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [~~getNullValue~~](index.md#-1752557675%2FFunctions%2F-912451524)(): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>open override fun [getNullValue](index.md#432781262%2FFunctions%2F-912451524)(p0: DeserializationContext): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) |
| [getObjectIdReader](index.md#911426750%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getObjectIdReader](index.md#911426750%2FFunctions%2F-912451524)(): ObjectIdReader |
| [getValueClass](index.md#255254003%2FFunctions%2F-912451524) | [androidJvm]<br>fun [~~getValueClass~~](index.md#255254003%2FFunctions%2F-912451524)(): [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;*&gt; |
| [getValueInstantiator](index.md#1591006481%2FFunctions%2F-912451524) | [androidJvm]<br>open override fun [getValueInstantiator](index.md#1591006481%2FFunctions%2F-912451524)(): ValueInstantiator |
| [getValueType](index.md#943959893%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getValueType](index.md#943959893%2FFunctions%2F-912451524)(): JavaType<br>open fun [getValueType](index.md#-956205042%2FFunctions%2F-912451524)(p0: DeserializationContext): JavaType |
| [handledType](index.md#2000742074%2FFunctions%2F-912451524) | [androidJvm]<br>open override fun [handledType](index.md#2000742074%2FFunctions%2F-912451524)(): [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;*&gt; |
| [isCachable](index.md#1654902530%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isCachable](index.md#1654902530%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [logicalType](index.md#1638353390%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [logicalType](index.md#1638353390%2FFunctions%2F-912451524)(): LogicalType |
| [replaceDelegatee](index.md#79105129%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [replaceDelegatee](index.md#79105129%2FFunctions%2F-912451524)(p0: JsonDeserializer&lt;*&gt;): JsonDeserializer&lt;*&gt; |
| [supportsUpdate](index.md#336340330%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [supportsUpdate](index.md#336340330%2FFunctions%2F-912451524)(p0: DeserializationConfig): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [unwrappingDeserializer](index.md#-1815728544%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [unwrappingDeserializer](index.md#-1815728544%2FFunctions%2F-912451524)(p0: NameTransformer): JsonDeserializer&lt;[Date](https://developer.android.com/reference/kotlin/java/util/Date.html)&gt; |
