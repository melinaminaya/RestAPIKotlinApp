//[app](../../index.md)/[br.com.autotrac.testnanoclient.common](index.md)

# Package br.com.autotrac.testnanoclient.common

## Functions

| Name | Summary |
|---|---|
| [Alert](-alert.md) | [androidJvm]<br>@Composable<br>fun [Alert](-alert.md)(onDismissRequest: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onClick: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), content: @Composable() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [BadgeText](-badge-text.md) | [androidJvm]<br>@Composable<br>fun [BadgeText](-badge-text.md)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), isServiceOn: MutableState&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;) |
| [ButtonTicker](-button-ticker.md) | [androidJvm]<br>@Composable<br>fun [ButtonTicker](-button-ticker.md)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), icon: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), modifier: Modifier, onClick: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [CustomAlert](-custom-alert.md) | [androidJvm]<br>@Composable<br>fun [CustomAlert](-custom-alert.md)(onDismissRequest: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, content: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), confirmBt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onClick: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [CustomTextFieldWithButton](-custom-text-field-with-button.md) | [androidJvm]<br>@Composable<br>fun [CustomTextFieldWithButton](-custom-text-field-with-button.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, onTextChange: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onSaveClick: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [DefaultButton](-default-button.md) | [androidJvm]<br>@Composable<br>fun [DefaultButton](-default-button.md)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), icon: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), modifier: Modifier, color: Color?, onClick: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [DropdownCard](-dropdown-card.md) | [androidJvm]<br>@Composable<br>fun [DropdownCard](-dropdown-card.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), content: @Composable() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [DropDownToSet](-drop-down-to-set.md) | [androidJvm]<br>@Composable<br>fun [DropDownToSet](-drop-down-to-set.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), previousText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, onTextChange: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), textStatus: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, dropdownItems: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt;) |
| [IconToggleButtonSample](-icon-toggle-button-sample.md) | [androidJvm]<br>@Composable<br>fun [IconToggleButtonSample](-icon-toggle-button-sample.md)() |
| [LoadingIcon](-loading-icon.md) | [androidJvm]<br>@Composable<br>fun [LoadingIcon](-loading-icon.md)(size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [MessageListComposable](-message-list-composable.md) | [androidJvm]<br>@Composable<br>fun [MessageListComposable](-message-list-composable.md)(messages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IntegrationMessage](../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md)&gt;, onMessageDelete: ([IntegrationMessage](../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onMessageClick: ([IntegrationMessage](../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onDialogDismiss: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onRefresh: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [ModelRow](-model-row.md) | [androidJvm]<br>@Composable<br>fun [ModelRow](-model-row.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), status: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [RedBackground](-red-background.md) | [androidJvm]<br>@Composable<br>fun [RedBackground](-red-background.md)(degrees: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), onMessageDelete: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [ShowSnackbar](-show-snackbar.md) | [androidJvm]<br>@Composable<br>fun [ShowSnackbar](-show-snackbar.md)(snackbarHostState: SnackbarHostState, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), duration: SnackbarDuration, coroutineScope: CoroutineScope) |
| [SwipeItem](-swipe-item.md) | [androidJvm]<br>@Composable<br>fun [SwipeItem](-swipe-item.md)(message: [IntegrationMessage](../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md), onMessageDelete: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onMessageClick: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [SwitchParameter](-switch-parameter.md) | [androidJvm]<br>@Composable<br>fun [SwitchParameter](-switch-parameter.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), paramText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, isChecked: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), onTextChange: ([Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), textStatus: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [ToggleCard](-toggle-card.md) | [androidJvm]<br>@Composable<br>fun [ToggleCard](-toggle-card.md)(modifier: Modifier, color: Color?, isChecked: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), onCheckedChange: ([Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), icon: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?) |
| [ToggleSwitch](-toggle-switch.md) | [androidJvm]<br>@Composable<br>fun [ToggleSwitch](-toggle-switch.md)(isChecked: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), onCheckedChange: ([Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), icon: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, modifier: Modifier = Modifier) |