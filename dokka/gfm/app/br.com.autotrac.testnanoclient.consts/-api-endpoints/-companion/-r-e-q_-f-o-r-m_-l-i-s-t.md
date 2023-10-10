//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ApiEndpoints](../index.md)/[Companion](index.md)/[REQ_FORM_LIST](-r-e-q_-f-o-r-m_-l-i-s-t.md)

# REQ_FORM_LIST

[androidJvm]\
const val [REQ_FORM_LIST](-r-e-q_-f-o-r-m_-l-i-s-t.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Lista os formulários ou grupos de formulários de acordo com o filtro especificado (Este método só terá aplicabilidade para produtos que possuem comunicação celular).

#### Parameters

androidJvm

| | |
|---|---|
| param1 | (groupOnly) - se for true, indica que a consulta deve ser realizada nos grupos de formulário. Neste caso, apenas o parâmetro formGroupCode é considerado. Se for false, a consulta será realizada nos formulários. |
| param2 | (formGroupCode) - indica qual grupo de formulário deve ser filtrado. Somente os formulários pertencentes a este grupo serão retornados. Se este parâmetro for igual a 0, o filtro será desabilitado. |
| param3 | (formType) - é o tipo de formulário a ser filtrado. Somente os formulários do tipo especificado serão retornados. |

#### See also

| |
|---|
| [ActionValues.FormTypeValues](../../-action-values/-form-type-values/index.md) |
