package com.softray_solutions.newschoolproject.adapters;

import android.view.ViewGroup;

public abstract class DrawerItem<T extends DrawerAdapter.MyOwnViewHolder> {

    protected boolean isChecked;
    private boolean isSelectable;

    public abstract T createViewHolder(ViewGroup parent);

    public abstract void bindViewHolder(T holder);

    public boolean isChecked() {
        return isChecked;
    }

    public DrawerItem setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        return this;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public void setSelectable(boolean selectable) {
        isSelectable = selectable;
    }
}
