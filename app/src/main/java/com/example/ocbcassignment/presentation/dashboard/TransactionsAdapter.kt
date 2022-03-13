package com.example.ocbcassignment.presentation.dashboard

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ocbcassignment.R
import com.example.ocbcassignment.databinding.ItemDateLayoutBinding
import com.example.ocbcassignment.databinding.ItemLayoutBinding
import com.example.ocbcassignment.domain.model.RecyclerViewDataModel
import com.example.ocbcassignment.domain.model.Transaction
import java.text.SimpleDateFormat

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.ItemViewHolder>() {

    private val adapterData = mutableListOf<RecyclerViewDataModel>()
    private val listData = mutableListOf<Transaction>()

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: RecyclerViewDataModel) {
            when (data) {
                is RecyclerViewDataModel.Header -> bindHeader(data)
                is RecyclerViewDataModel.Item -> bindItem(data)
            }
        }

        private fun bindHeader(data: RecyclerViewDataModel.Header) {
            val dateTxt = itemView.findViewById<TextView>(R.id.dateTv)
            dateTxt.text = data.date
        }

        @SuppressLint("SetTextI18n")
        private fun bindItem(data: RecyclerViewDataModel.Item) {
            val nameTxt = itemView.findViewById<TextView>(R.id.nameTv)
            val accountNoTxt = itemView.findViewById<TextView>(R.id.accountNoTv)
            val amountTxt = itemView.findViewById<TextView>(R.id.amountTv)

            nameTxt.text = data.item.receipient?.accountHolder.toString()
            accountNoTxt.text = data.item.receipient?.accountNo.toString()

            if (data.item.transactionType == "transfer") {
                amountTxt.setTextColor(Color.GRAY)
                amountTxt.text = "- ${data.item.amount.toString()}"
            } else {
                amountTxt.setTextColor(Color.GREEN)
                amountTxt.text = data.item.amount.toString()
            }

        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(adapterData[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layout = when (viewType) {
            TYPE_HEADER -> ItemDateLayoutBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
            TYPE_ITEM -> ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
            else -> throw IllegalArgumentException("Invalid type")
        }
        return ItemViewHolder(layout.root)
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is RecyclerViewDataModel.Header -> TYPE_HEADER
            is RecyclerViewDataModel.Item -> TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return adapterData.size
    }

    fun setData(data: List<Transaction>) {
        listData.apply {
            clear()
            addAll(data)
        }

        val parser = SimpleDateFormat("yyyy-DD-MM'T'HH:MM:SS.SSS")
        val format = SimpleDateFormat("dd MMM yyyy")

        val groupedData = listData.groupBy { item ->
            val date = parser.parse(item.transactionDate)

            format.format(date)
        }

        adapterData.clear()

        groupedData.keys.forEach { s ->
            adapterData.add(RecyclerViewDataModel.Header(s))
            groupedData[s]?.forEach {
                adapterData.add(RecyclerViewDataModel.Item(it))
            }
        }

        Log.d("<>AdaptDataCount", adapterData.size.toString())

        notifyDataSetChanged()
    }
}