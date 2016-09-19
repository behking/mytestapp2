package com.example.newuser.vangmaterialdesign.file_downloader;

import android.media.MediaScannerConnection;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.newuser.vangmaterialdesign.FragC;
import com.example.newuser.vangmaterialdesign.L;
import com.example.newuser.vangmaterialdesign.MainActivity;
import com.example.newuser.vangmaterialdesign.PagerAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DownloadManager {

  DlAdapter.TaskItemAdapter adapter = new DlAdapter.TaskItemAdapter();
 PagerAdapter pagerAdapter ;
    FragC fragC;
    ArrayList<String> dlURL= new ArrayList<>();





    private final static class HolderClass{

        private final static DownloadManager INSTANCE = new DownloadManager();
    }

    public static DownloadManager getImpl(){
        L.m("getImplllllll");
        DlConstance dlConstance = new DlConstance();

        return HolderClass.INSTANCE;
    }




    private TasksManagerDBController dbController;
        private ArrayList<DlModel.TasksManagerModel> modelList;



        public DownloadManager() {
L.m("TasDDDDDDDDDDDDDDDDBControler");

            dbController = new TasksManagerDBController();

            modelList = dbController.getAllTasks();
             L.m("modellist ="+modelList);

                initDemo();




        }

    private void initDemo() {

      DlConstance dlConstance = new DlConstance();
        L.m("inidemo");


            if (modelList.size() <= 0) {

                    final int demoSize = dlConstance.dlurl.size();
                    L.m("demo size " + demoSize);
                    for (int i = 0; i < demoSize; i++) {
                        final String url = dlConstance.dlurl.get(i);
                        L.m("demo url " + url);
                        addTask(url);
                    }

            }
    }



        private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();


        public void addTaskForViewHolder(final BaseDownloadTask task) {
            taskSparseArray.put(task.getId(), task);
            L.m("sparsetask = "+taskSparseArray);
        }
    public void Dlupdate(int position) {
       modelList.remove(position);


    }
        public void removeTaskForViewHolder(final int id) {
            taskSparseArray.remove(id);
        }

        public void updateViewHolder(final int id, final DlAdapter.TaskItemViewHolder holder) {
            final BaseDownloadTask task = taskSparseArray.get(id);
            if (task == null) {
                return;
            }

            task.setTag(holder);
        }

        public void releaseTask() {
            taskSparseArray.clear();
        }

        private FileDownloadConnectListener listener;


        private void registerServiceConnectionListener(final WeakReference<FragC> activityWeakReference) {
            if (listener != null) {
                FileDownloader.getImpl().removeServiceConnectListener(listener);
            }

            listener = new FileDownloadConnectListener() {

                @Override
                public void connected() {
                    if (activityWeakReference == null
                            || activityWeakReference.get() == null) {
                        return;
                    }

                    activityWeakReference.get().postNotifyDataChanged();
                }

                @Override
                public void disconnected() {
                    if (activityWeakReference == null
                            || activityWeakReference.get() == null) {
                        return;
                    }

                    activityWeakReference.get().postNotifyDataChanged();
                    MediaScannerConnection.scanFile(
                            FileDownloadHelper.getAppContext(),
                            new String[]{String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC))},
                            null,
                            null);
                }
            };

            FileDownloader.getImpl().addServiceConnectListener(listener);
        }

        private void unregisterServiceConnectionListener() {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
            listener = null;
        }

        public void onCreate(final WeakReference<FragC> activityWeakReference) {
            L.m("oncreat Dlll");
            if (!FileDownloader.getImpl().isServiceConnected()) {
                FileDownloader.getImpl().bindService();
                registerServiceConnectionListener(activityWeakReference);
            }
        }

        public void onDestroy() {
            unregisterServiceConnectionListener();
            releaseTask();
        }

        public boolean isReady() {
            return FileDownloader.getImpl().isServiceConnected();
        }

        public DlModel.TasksManagerModel get(final int position) {
            return modelList.get(position);
        }

        public DlModel.TasksManagerModel getById(final int id) {
            for (DlModel.TasksManagerModel model : modelList) {
                if (model.getId() == id) {
                    return model;
                }
            }

            return null;
        }

        /**
         * @param status Download Status
         * @return has already downloaded
         * @see FileDownloadStatus
         */
        public boolean isDownloaded(final int status) {

            return status == FileDownloadStatus.completed;
        }

        public int getStatus(final int id, String path) {
            return FileDownloader.getImpl().getStatus(id, path);
        }

        public long getTotal(final int id) {
            return FileDownloader.getImpl().getTotal(id);
        }

        public long getSoFar(final int id) {
            return FileDownloader.getImpl().getSoFar(id);
        }

    public int getTaskCounts() {
            return modelList.size();
        }
    //===========================
    public void removeTask(long id) {
         TasksManagerDBController ts = new TasksManagerDBController();
        ts.deleteTask(id);
        L.m("remove");
    }
    //================================
        public DlModel.TasksManagerModel addTask(final String url) {
            return addTask(url, createPath(url));
        }

        public DlModel.TasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }

            final int id = FileDownloadUtils.generateId(url, path);
            DlModel.TasksManagerModel model = getById(id);
            if (model != null) {
                return model;
            }
            final DlModel.TasksManagerModel newModel = dbController.addTask(url, path);
            if (newModel != null) {
                modelList.add(newModel);
            }

            return newModel;
        }


        public String createPath(final String url) {
            if (TextUtils.isEmpty(url)) {
                return null;
            }

            return FileDownloadUtils.getDefaultSaveFilePath(url);
        }




    }
