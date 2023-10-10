//[app](../../../index.md)/[br.com.autotrac.testnanoclient.handlers](../index.md)/[ParseResult](index.md)

# ParseResult

[androidJvm]\
sealed class [ParseResult](index.md)

Recebimento das requisições e distribuição para os cada Handler específico. Distribui por Endpoint.

#### Author

Melina Minaya

## Types

| Name | Summary |
|---|---|
| [Error](-error/index.md) | [androidJvm]<br>data class [Error](-error/index.md)(errorMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [ParseResult](index.md) |
| [Ok](-ok/index.md) | [androidJvm]<br>object [Ok](-ok/index.md) : [ParseResult](index.md) |

## Inheritors

| Name |
|---|
| [Ok](-ok/index.md) |
| [Error](-error/index.md) |
