//[app](../../../index.md)/[br.com.autotrac.testnanoclient.consts](../index.md)/[ApiEndpoints](index.md)/[SET_PARAM_WIFI_SSID](-s-e-t_-p-a-r-a-m_-w-i-f-i_-s-s-i-d.md)

# SET_PARAM_WIFI_SSID

[androidJvm]\
const val [SET_PARAM_WIFI_SSID](-s-e-t_-p-a-r-a-m_-w-i-f-i_-s-s-i-d.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Endpoint específico para realizar o batismo com o endereço IP da rede Wi-Fi do dispositivo móvel (Android).

Para sistemas que utilizam puramente o dispositivo de comunicação alternativa, se for configurado um SSID, a associação com o dispositivo ocorre automaticamente em seguida. Ao configurar um SSID, o sistema verifica se a UC está habilitada para ser batizada, caso não esteja retorna [ApiResponses.UC_NOT_ENABLE](../-api-responses/-u-c_-n-o-t_-e-n-a-b-l-e.md) ou [ApiResponses.BAD_REQUEST](../-api-responses/-b-a-d_-r-e-q-u-e-s-t.md).

Caso o SSID fornecido seja vazio (&quot;&quot;) e o sistema esteja associado a um dispositivo de comunicação alternativa, a associação será desfeita.
