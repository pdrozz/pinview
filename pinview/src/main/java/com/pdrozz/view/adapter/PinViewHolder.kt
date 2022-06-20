package com.pdrozz.view.adapter

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.KeyEvent
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.pdrozz.view.model.PinModel
import com.pdrozz.view.state.PinState
import com.pdrozz.view.databinding.PinItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class PinViewHolder(
    private val itemBinding: PinItemBinding,
    private val pinStateFlow: MutableSharedFlow<PinState>
) : RecyclerView.ViewHolder(itemBinding.root) {

    private var pinModel: PinModel? = null
    private var emitOnTextChanged = AtomicBoolean(true)

    fun bind(pinModel: PinModel) {
        this.pinModel = pinModel

        CoroutineScope(Dispatchers.Main).launch {
            pinStateFlow.collectLatest {
                when (it) {
                    is PinState.RequestFocus -> onFocusRequested(it.index)
                    is PinState.Clear -> clearText()
                    is PinState.SetText -> setText(it.text)
                    else -> {}
                }
            }
        }

        itemBinding.editPin.setText(pinModel.text)

        setupDelKeyListener()
        setupOnTextChanged()
    }

    private fun setText(text: String?) {
        emitOnTextChanged.set(false)
        itemBinding.editPin.setText(
            text?.getOrNull(adapterPosition)
                ?.toString() ?: ""
        )
        emitOnTextChanged.set(true)
    }

    fun clearText() {
        emitOnTextChanged.set(false)
        itemBinding.editPin.text.clear()
        emitOnTextChanged.set(true)
    }

    private fun setupOnTextChanged() {
        itemBinding.editPin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                pinModel?.text = s.toString()

                if (emitOnTextChanged.get())
                    pinStateFlow.tryEmit(
                        PinState.TextUnitChanged(
                            index = adapterPosition,
                            text = pinModel?.text
                        )
                    )
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupDelKeyListener() {
        itemBinding.editPin.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN)
                pinStateFlow.tryEmit(PinState.RequestFocus(index = adapterPosition - 1))
            return@setOnKeyListener false
        }
    }

    private fun onFocusRequested(indexToFocus: Int) {
        if (indexToFocus == adapterPosition) itemBinding.editPin.requestFocus()
    }

    fun setIsPassword(password: Boolean) {
        if (password) {
            itemBinding.editPin.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        } else {
            itemBinding.editPin.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }

    fun setTextStyle(fontFamilyId: Int?, textStyle: Int?) {
        runCatching {
            val font = if (fontFamilyId != null && fontFamilyId != 0) ResourcesCompat.getFont(
                itemBinding.root.context,
                fontFamilyId
            )
            else null

            itemBinding.editPin.setTypeface(
                font, textStyle ?: 0
            )
        }
    }

    fun setTextSize(textSize: Int?) {
        if (textSize != null && textSize > 0)
            itemBinding.editPin.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
    }

    fun setTextColor(textColor: Int?) {
        if (textColor != null && textColor != 0)
            itemBinding.editPin.setTextColor(textColor)
    }

    fun setBackground(pinBackground: Int?) {
        if (pinBackground != null)
            itemBinding.editPin.setBackgroundResource(pinBackground)
    }

    fun setIsEnabled(isEnabled: Boolean) {
        itemBinding.editPin.isEnabled = isEnabled
    }

}