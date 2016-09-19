package com.example.newuser.vangmaterialdesign.file_downloader;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.newuser.vangmaterialdesign.L;
import com.example.newuser.vangmaterialdesign.R;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by new user on 8/26/2016.
 */
public  class TasksManagerDBController {


        public final static String TABLE_NAME = "tasksmanger";
        private final SQLiteDatabase db;

        public TasksManagerDBController() {
            DlModel.TasksManagerDBOpenHelper openHelper = new DlModel.TasksManagerDBOpenHelper(MyApplication.CONTEXT);
            L.m("TasksManger DB controller");
            db = openHelper.getWritableDatabase();
        }

        public ArrayList<DlModel.TasksManagerModel> getAllTasks() {
            final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
L.m("getAllTasks");
            final ArrayList<DlModel.TasksManagerModel> list = new ArrayList<>();
            try {
                if (!c.moveToLast()) {
                    return list;
                }

                do {
                    DlModel.TasksManagerModel model = new DlModel.TasksManagerModel();
                    model.setId(c.getInt(c.getColumnIndex(DlModel.TasksManagerModel.ID)));
                    model.setName(c.getString(c.getColumnIndex(DlModel.TasksManagerModel.NAME)));
                    model.setUrl(c.getString(c.getColumnIndex(DlModel.TasksManagerModel.URL)));
                    model.setPath(c.getString(c.getColumnIndex(DlModel.TasksManagerModel.PATH)));
                    list.add(model);
                } while (c.moveToPrevious());
            } finally {
                if (c != null) {
                    c.close();
                }
            }

            return list;
        }

        public DlModel.TasksManagerModel addTask(final String url, final String path) {
            if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
                L.m("aaaaaaaaaaaaaddddddd");
                return null;
            }

            // have to use FileDownloadUtils.generateId to associate TasksManagerModel with FileDownloader
            final int id = FileDownloadUtils.generateId(url, path);

            DlModel.TasksManagerModel model = new DlModel.TasksManagerModel();
            model.setId(id);
            model.setName(MyApplication.CONTEXT.getString(R.string.tasks_manager_demo_name, id));
            model.setUrl(url);
            model.setPath(path);

            final boolean succeed = db.insert(TABLE_NAME, null, model.toContentValues()) != -1;
            return succeed ? model : null;
        }

    //---deletes a particular title---
    public boolean deleteTask(long id)
    {
        return db.delete(TABLE_NAME, DlModel.TasksManagerModel.ID + "=" + id, null) > 0;
    }


}
