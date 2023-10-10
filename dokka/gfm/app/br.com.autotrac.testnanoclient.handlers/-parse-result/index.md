//[app](../../../index.md)/[br.com.autotrac.testnanoclient.handlers](../index.md)/[ParseResult](index.md)

# ParseResult

sealed class [ParseResult](index.md)

Recebimento das requisições e distribuição para os cada Handler específico. Distribui por Endpoint.

#### Author

Melina Minaya

#### Inheritors

| |
|---|
| [Ok](-ok/index.md) |
| [Error](-error/index.md) |

## Types

| Name | Summary |
|---|---|
| [Error](-error/index.md) | [androidJvm]<br>data class [Error](-error/index.md)(val errorMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [ParseResult](index.md) |
| [Ok](-ok/index.md) | [androidJvm]<br>object [Ok](-ok/index.md) : [ParseResult](index.md) |
