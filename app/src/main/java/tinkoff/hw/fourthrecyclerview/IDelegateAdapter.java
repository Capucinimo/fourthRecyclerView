package tinkoff.hw.fourthrecyclerview;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface IDelegateAdapter<VH extends RecyclerView.ViewHolder, T> {

    @NonNull
    RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    void onBindViewHolder(@NonNull VH holder,
                          @NonNull List<T> items,
                          int position);

    void onRecycled(VH holder);

    boolean isForViewType(@NonNull List<?> items, int position);
}
