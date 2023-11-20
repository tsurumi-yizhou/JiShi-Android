@file:OptIn(ExperimentalComposeUiApi::class)

package me.yihtseu.jishi.ui.framework


import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree

fun Modifier.autofill(autofillTypes: List<AutofillType>, onFill: (String) -> Unit) = composed {
    composed {
        val autofillNode = AutofillNode(autofillTypes, onFill = onFill)
        LocalAutofillTree.current += autofillNode

        val autofill = LocalAutofill.current

        this
            .onGloballyPositioned {
                // Inform autofill framework of where our composable is so it can show the popup in the right place
                autofillNode.boundingBox = it.boundsInWindow()
            }
            .onFocusChanged {
                autofill?.run {
                    if (it.isFocused) {
                        requestAutofillForNode(autofillNode)
                    } else {
                        cancelAutofillForNode(autofillNode)
                    }
                }
            }
    }
}