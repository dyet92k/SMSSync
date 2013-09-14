/*******************************************************************************
 *  Copyright (c) 2010 - 2013 Ushahidi Inc
 *  All rights reserved
 *  Contact: team@ushahidi.com
 *  Website: http://www.ushahidi.com
 *  GNU Lesser General Public License Usage
 *  This file may be used under the terms of the GNU Lesser
 *  General Public License version 3 as published by the Free Software
 *  Foundation and appearing in the file LICENSE.LGPL included in the
 *  packaging of this file. Please review the following information to
 *  ensure the GNU Lesser General Public License version 3 requirements
 *  will be met: http://www.gnu.org/licenses/lgpl.html.
 *
 * If you have questions regarding the use of this file, please contact
 * Ushahidi developers at team@ushahidi.com.
 ******************************************************************************/

package org.addhen.smssync.fragments;

import com.google.analytics.tracking.android.EasyTracker;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import org.addhen.smssync.adapters.BaseListAdapter;
import org.addhen.smssync.models.Model;
import org.addhen.smssync.util.Logger;
import org.addhen.smssync.util.Objects;
import org.addhen.smssync.views.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author eyedol
 */
public abstract class BaseListFragment<V extends View, M extends Model, L extends BaseListAdapter<M>>
        extends SherlockListFragment {

    /**
     * Menu resource id
     */
    protected final int menu;

    /**
     * Layout resource id
     */
    protected final int layout;

    /**
     * View class
     */
    protected final Class<V> viewClass;

    /**
     * ListView resource id
     */
    private final int listViewId;

    /**
     * ListAdpater class
     */
    private final Class<L> adapterClass;

    /**
     * ListAdapter
     */
    protected L adapter;

    /**
     * ListView
     */
    protected ListView listView;

    /**
     * View
     */
    protected V view;


    /**
     * BaseListActivity
     *
     * @param view     View class type
     * @param adapter  List adapter class type
     * @param layout   layout resource id
     * @param menu     menu resource id
     * @param listView list view resource id
     */
    protected BaseListFragment(Class<V> view, Class<L> adapter, int layout,
            int menu, int listView) {
        this.adapterClass = adapter;
        this.listViewId = listView;
        this.viewClass = view;
        this.menu = menu;
        this.layout = layout;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        if (listViewId != 0) {
            listView = getListView();

            view = Objects.createInstance(viewClass, Activity.class,
                    getSherlockActivity());
            adapter = Objects.createInstance(adapterClass, Context.class,
                    getSherlockActivity());

            listView.setFocusable(true);
            listView.setFocusableInTouchMode(true);
            listView.setAdapter(adapter);
        }
        EasyTracker.getInstance().setContext(getActivity());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (this.menu != 0) {
            inflater.inflate(this.menu, menu);
        }

    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        android.view.View root = null;
        if (layout != 0) {
            root = inflater.inflate(layout, container, false);
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        log("onStart");
        EasyTracker.getInstance().activityStart(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy");
        EasyTracker.getInstance().activityStop(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressWarnings("unchecked")
    protected M getSelectedItem() {
        return (M) listView.getSelectedItem();
    }

    public void onItemSelected(AdapterView<?> adapterView,
            android.view.View view, int position, long id) {
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    protected void log(String message) {
        Logger.log(getClass().getName(), message);
    }

    protected void log(String format, Object... args) {

        Logger.log(getClass().getName(), String.format(format, args));
    }

    protected void log(String message, Exception ex) {

        Logger.log(getClass().getName(), message, ex);
    }

    protected void toastLong(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    protected void toastLong(int message) {
        Toast.makeText(getActivity(), getText(message), Toast.LENGTH_LONG)
                .show();
    }

    protected void toastShort(int message) {
        Toast.makeText(getActivity(), getText(message), Toast.LENGTH_SHORT)
                .show();
    }

    protected void toastShort(CharSequence message) {
        Toast.makeText(getActivity(), message.toString(), Toast.LENGTH_SHORT)
                .show();
    }

}