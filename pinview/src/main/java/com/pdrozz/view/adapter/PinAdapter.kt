package com.pdrozz.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pdrozz.view.model.PinModel
import com.pdrozz.view.model.PinParams
import com.pdrozz.view.state.PinState
import com.pdrozz.view.databinding.PinItemBinding
import kotlinx.coroutines.flow.MutableSharedFlow

class PinAdapter(
    private val pinParams: PinParams,
    private val pinEventEmitter: MutableSharedFlow<PinState>
) : ListAdapter<PinModel, PinViewHolder>(PIN_DIFF_UTIL) {

    private val UPDATE_BACKGROUND_PAYLOAD = "UPDATE_BACKGROUND_PAYLOAD"
    private val UPDATE_IS_ENABLED_PAYLOAD = "UPDATE_IS_ENABLED_PAYLOAD"

    init {
        submitList(List(pinParams.pinCount) { PinModel(index = it) })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinViewHolder {
        return PinViewHolder(
            PinItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), pinEventEmitter
        )
    }

    override fun onBindViewHolder(
        holder: PinViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            payloads.forEach {
                if (it == UPDATE_BACKGROUND_PAYLOAD) holder.setBackground(pinParams.pinBackground)
                else if (it == UPDATE_IS_ENABLED_PAYLOAD) holder.setIsEnabled(pinParams.isEnabled)
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: PinViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.setBackground(pinParams.pinBackground)
        holder.setIsPassword(pinParams.isPassword)
        holder.setTextStyle(pinParams.fontFamilyID, pinParams.textStyle)
        holder.setTextSize(pinParams.textSize)
        holder.setTextColor(pinParams.textColor)
    }

    fun setPinBackground(pinBackground: Int) {
        pinParams.pinBackground = pinBackground
        notifyItemRangeChanged(0, currentList.size, UPDATE_BACKGROUND_PAYLOAD)
    }

    fun setIsEnabled(isEnabled: Boolean) {
        pinParams.isEnabled = isEnabled
        notifyItemRangeChanged(0, currentList.size, UPDATE_IS_ENABLED_PAYLOAD)
    }

    fun getText(): String {
        val stringBuilder = StringBuilder()
        currentList.map {
            stringBuilder.append(it.text ?: "")
        }
        return stringBuilder.toString()
    }

    companion object {
        private val PIN_DIFF_UTIL = object : DiffUtil.ItemCallback<PinModel>() {
            override fun areItemsTheSame(oldItem: PinModel, newItem: PinModel): Boolean {
                return oldItem.index == newItem.index
            }

            override fun areContentsTheSame(oldItem: PinModel, newItem: PinModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}