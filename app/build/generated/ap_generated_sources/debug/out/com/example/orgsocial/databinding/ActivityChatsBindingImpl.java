package com.example.orgsocial.databinding;
import com.example.orgsocial.R;
import com.example.orgsocial.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityChatsBindingImpl extends ActivityChatsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.chatScreenBack, 2);
        sViewsWithIds.put(R.id.userProfile, 3);
        sViewsWithIds.put(R.id.profileProgress, 4);
        sViewsWithIds.put(R.id.userName, 5);
        sViewsWithIds.put(R.id.lastSeen, 6);
        sViewsWithIds.put(R.id.chatRecycler, 7);
        sViewsWithIds.put(R.id.messageInputArea, 8);
        sViewsWithIds.put(R.id.emojis, 9);
        sViewsWithIds.put(R.id.messsageField, 10);
        sViewsWithIds.put(R.id.attachFile, 11);
        sViewsWithIds.put(R.id.cameraPhoto, 12);
        sViewsWithIds.put(R.id.chatScreenFab, 13);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityChatsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private ActivityChatsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageButton) bindings[11]
            , (android.widget.ImageButton) bindings[12]
            , (androidx.recyclerview.widget.RecyclerView) bindings[7]
            , (android.widget.ImageButton) bindings[2]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[13]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.TextView) bindings[6]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.EditText) bindings[10]
            , (android.widget.ProgressBar) bindings[4]
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            , (android.widget.TextView) bindings[5]
            , (com.mikhaellopez.circularimageview.CircularImageView) bindings[3]
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