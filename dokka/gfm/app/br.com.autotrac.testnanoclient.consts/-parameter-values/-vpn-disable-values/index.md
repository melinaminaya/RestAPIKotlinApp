//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ParameterValues](../index.md)/[VpnDisableValues](index.md)

# VpnDisableValues

object [VpnDisableValues](index.md)

Indica se o uso da VPN pelo serviço de comunicação é permitido ou não.

#### See also

| |
|---|
| [ApiEndpoints.Companion.GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION](../../-api-endpoints/-companion/-g-e-t_-p-a-r-a-m_-l-o-c-a-l_-d-i-s-a-b-l-e_-v-p-n_-c-o-m-m-u-n-i-c-a-t-i-o-n.md) |
| [ApiEndpoints.Companion.SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION](../../-api-endpoints/-companion/-s-e-t_-p-a-r-a-m_-l-o-c-a-l_-d-i-s-a-b-l-e_-v-p-n_-c-o-m-m-u-n-i-c-a-t-i-o-n.md) | O valor 1 desabilita o uso da VPN pelo serviço de comunicação e o valor 0 habilita. Esta opção deve ser utilizada quando operações envolvendo o uso simultâneo do HotSpot WiFi e da conexão celular. Logo que possível o uso da VPN deve ser habilitado (configurando o valor deste parâmetro como 0), caso seja desejável o seu uso. |

## Properties

| Name | Summary |
|---|---|
| [DISABLE_VPN](-d-i-s-a-b-l-e_-v-p-n.md) | [androidJvm]<br>const val [DISABLE_VPN](-d-i-s-a-b-l-e_-v-p-n.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1 |
| [ENABLE_VPN](-e-n-a-b-l-e_-v-p-n.md) | [androidJvm]<br>const val [ENABLE_VPN](-e-n-a-b-l-e_-v-p-n.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
