package com.example.orgsocial.databinding;
import com.example.orgsocial.R;
import com.example.orgsocial.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityGroupProfileBindingImpl extends ActivityGroupProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.profileImage, 1);
        sViewsWithIds.put(R.id.editGroupSubject, 2);
        sViewsWithIds.put(R.id.username, 3);
        sViewsWithIds.put(R.id.created, 4);
        sViewsWithIds.put(R.id.progress, 5);
        sViewsWithIds.put(R.id.descriptionLayout, 6);
        sViewsWithIds.put(R.id.viewDescriptionLayout, 7);
        sViewsWithIds.put(R.id.description, 8);
        sViewsWithIds.put(R.id.addDescriptionLayout, 9);
        sViewsWithIds.put(R.id.editDescription, 10);
        sViewsWithIds.put(R.id.groupSettingsLayout, 11);
        sViewsWithIds.put(R.id.participantsLayout, 12);
        sViewsWithIds.put(R.id.participantsCount, 13);
        sViewsWithIds.put(R.id.addParticipants, 14);
        sViewsWithIds.put(R.id.groupIcon, 15);
        sViewsWithIds.put(R.id.participantsRecycler, 16);
        sViewsWithIds.put(R.id.exitGroupLayout, 17);
        sViewsWithIds.put(R.id.toolbar, 18);
        sViewsWithIds.put(R.id.back, 19);
        sViewsWithIds.put(R.id.addParticipantsIcon, 20);
        sViewsWithIds.put(R.id.progressBar, 21);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityGroupProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }
    private ActivityGroupProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.ImageButton) bindings[20]
            , (android.widget.ImageButton) bindings[19]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[8]
            , (androidx.cardview.widget.CardView) bindings[6]
            , (android.widget.ImageButton) bindings[10]
            , (android.widget.ImageButton) bindings[2]
            , (androidx.cardview.widget.CardView) bindings[17]
            , (android.widget.ImageView) bindings[15]
            , (androidx.cardview.widget.CardView) bindings[11]
            , (android.widget.TextView) bindings[13]
            , (androidx.cardview.widget.CardView) bindings[12]
            , (androidx.recyclerview.widget.RecyclerView) bindings[16]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.ProgressBar) bindings[5]
            , (android.widget.ProgressBar) bindings[21]
            , (com.google.android.material.appbar.AppBarLayout) bindings[18]
            , (android.widget.TextView) bindings[3]
            , (android.widget.LinearLayout) bindings[7]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}