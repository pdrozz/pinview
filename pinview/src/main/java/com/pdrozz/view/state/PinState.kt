package com.pdrozz.view.state

sealed class PinState {
    class TextUnitChanged(val index: Int, val text: String?) : PinState()
    class SetText(val text: String?) : PinState()
    class RequestFocus(val index: Int) : PinState()
    object Clear : PinState()
}