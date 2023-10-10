//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ActionValues](../index.md)/[ValuesBaptismStatusParam1](index.md)

# ValuesBaptismStatusParam1

[androidJvm]\
object [ValuesBaptismStatusParam1](index.md)

Status retornados pelo Param1 de ação [ActionValues.BAPTISM_STATUS](../-b-a-p-t-i-s-m_-s-t-a-t-u-s.md). Utilizado também no retorno da [ApiConstEndpoints.GET_PARAM_IS_BAPTIZED](../../-api-const-endpoints/-companion/-g-e-t_-p-a-r-a-m_-i-s_-b-a-p-t-i-z-e-d.md) .

## Properties

| Name | Summary |
|---|---|
| [BAPTISM_TIMED_OUT](-b-a-p-t-i-s-m_-t-i-m-e-d_-o-u-t.md) | [androidJvm]<br>const val [BAPTISM_TIMED_OUT](-b-a-p-t-i-s-m_-t-i-m-e-d_-o-u-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 6<br>Houve timeout no processo de batismo. |
| [BAPTIZED](-b-a-p-t-i-z-e-d.md) | [androidJvm]<br>const val [BAPTIZED](-b-a-p-t-i-z-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1<br>A Uc está batizada. |
| [IN_BAPTISM_PROCESS](-i-n_-b-a-p-t-i-s-m_-p-r-o-c-e-s-s.md) | [androidJvm]<br>const val [IN_BAPTISM_PROCESS](-i-n_-b-a-p-t-i-s-m_-p-r-o-c-e-s-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 2<br>O processo de batismo esta em execução. |
| [MCT_ADDR_RESPONSE](-m-c-t_-a-d-d-r_-r-e-s-p-o-n-s-e.md) | [androidJvm]<br>const val [MCT_ADDR_RESPONSE](-m-c-t_-a-d-d-r_-r-e-s-p-o-n-s-e.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 4<br>Resposta da consulta do número do Mct. |
| [MCT_NOT_AUTHORIZED](-m-c-t_-n-o-t_-a-u-t-h-o-r-i-z-e-d.md) | [androidJvm]<br>const val [MCT_NOT_AUTHORIZED](-m-c-t_-n-o-t_-a-u-t-h-o-r-i-z-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 5<br>O Mct conectado não está autorizado a se comunicar. A Uc está batizada com outro Mct. |
| [NOT_BAPTIZED](-n-o-t_-b-a-p-t-i-z-e-d.md) | [androidJvm]<br>const val [NOT_BAPTIZED](-n-o-t_-b-a-p-t-i-z-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0<br>A Uc não está batizada. |
| [WAITING_CONFIRMATION](-w-a-i-t-i-n-g_-c-o-n-f-i-r-m-a-t-i-o-n.md) | [androidJvm]<br>const val [WAITING_CONFIRMATION](-w-a-i-t-i-n-g_-c-o-n-f-i-r-m-a-t-i-o-n.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 3<br>Aguardando pela confirmação do batismo pelo servidor. |
