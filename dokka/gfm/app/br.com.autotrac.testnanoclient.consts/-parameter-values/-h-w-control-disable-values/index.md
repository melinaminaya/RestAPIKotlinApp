//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ParameterValues](../index.md)/[HWControlDisableValues](index.md)

# HWControlDisableValues

[androidJvm]\
object [HWControlDisableValues](index.md)

Possíveis valores para [ApiEndpoints.GET_PARAM_HW_CONTROL_DISABLE](../../-api-endpoints/-g-e-t_-p-a-r-a-m_-h-w_-c-o-n-t-r-o-l_-d-i-s-a-b-l-e.md). Mapa de bits. Pode ser uma combinação de um dos valores. Um bit setado indica que o gerenciamento do dispositivo em questão não deve ser executado.

## Properties

| Name | Summary |
|---|---|
| [AIR_PLANE](-a-i-r_-p-l-a-n-e.md) | [androidJvm]<br>const val [AIR_PLANE](-a-i-r_-p-l-a-n-e.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 8<br>Desabilita o controle do modo avião. |
| [CELLULAR](-c-e-l-l-u-l-a-r.md) | [androidJvm]<br>const val [CELLULAR](-c-e-l-l-u-l-a-r.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1<br>Desabilita o gerenciamento do Hardware Celular/APNs/Dados/Dados em roaming/Modo avi�o. |
| [GNSS](-g-n-s-s.md) | [androidJvm]<br>const val [GNSS](-g-n-s-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 4<br>Desabilita o gerenciamento do Hardware GPS/GNSS. |
| [NONE](-n-o-n-e.md) | [androidJvm]<br>const val [NONE](-n-o-n-e.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0<br>Todos os gerenciamentos estão ativos (nenhum gerenciamento desativado). |
| [WIFI](-w-i-f-i.md) | [androidJvm]<br>const val [WIFI](-w-i-f-i.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 2<br>Desabilita o gerenciamento do Hardware WiFi/Hotspot. |
