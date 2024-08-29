package com.example.adminapp;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {

    private Context context;
    private List<EbookData> list;
    private DownloadManager downloadManager;
    private DatabaseReference reference;

    public EbookAdapter(Context context, List<EbookData> list) {
        this.context = context;
        this.list = list;
        this.reference = FirebaseDatabase.getInstance().getReference().child("uploadPDF");
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.ebook_item_layout,parent,false);
        return new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, @SuppressLint("RecyclerView") int position) {

        EbookData data = list.get(position);

        holder.ebookName.setText(list.get(position).getName());

        holder.ebookDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pdfUrl = list.get(position).getUrl();

                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
                    context.startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });

        holder.ebookDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNotice(list.get(position).getId(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EbookViewHolder extends RecyclerView.ViewHolder {

//        public View ebookDelete;
        private TextView ebookName;
        private ImageView ebookDownload;
        private ImageView ebookDelete;

        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);

            ebookName = itemView.findViewById(R.id.ebookName);
            ebookDownload = itemView.findViewById(R.id.ebookDownload);
            ebookDelete = itemView.findViewById(R.id.ebookDelete);
        }
    }

    private void deleteNotice(String id, int position) {
        reference.child(id).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "File deleted", Toast.LENGTH_SHORT).show();
                list.remove(position);
                notifyItemRemoved(position);
            } else {
                Toast.makeText(context, "Failed to delete file", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
