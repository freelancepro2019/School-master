package com.softray_solutions.newschoolproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.fragments.paymentReset.PaymentResetFragmentPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentResetAdapter extends RecyclerView.Adapter<PaymentResetAdapter.MyViewHolder> {
    private PaymentResetFragmentPresenter presenter;

    public PaymentResetAdapter(PaymentResetFragmentPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.payment_receipt_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        presenter.setReceiptData(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getSize();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.receiptID_textView)
        public TextView receiptID;
        @BindView(R.id.payment_method_textView)
        public TextView paymentMethod;
        @BindView(R.id.number_textView)
        public TextView number;
        @BindView(R.id.amount_textView)
        public TextView amount;
        @BindView(R.id.year_textView)
        public TextView year;
        @BindView(R.id.date)
        public TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
