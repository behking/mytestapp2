package com.example.newuser.vangmaterialdesign.file_downloader;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.newuser.vangmaterialdesign.AdapterMovies;
import com.example.newuser.vangmaterialdesign.FragC;
import com.example.newuser.vangmaterialdesign.ItemTouchHelperAdapter;
import com.example.newuser.vangmaterialdesign.L;
import com.example.newuser.vangmaterialdesign.R;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

/**
 * Created by new user on 8/26/2016.
 */
public class DlAdapter {
    public static class TaskItemViewHolder extends RecyclerView.ViewHolder {
        public TaskItemViewHolder(View itemView) {
            super(itemView);
            assignViews();
        }

        private View findViewById(final int id) {
            return itemView.findViewById(id);
        }

        /**
         * viewHolder position
         */
        private int position;
        /**
         * download id
         */
        private int id;

        public void update(final int id, final int position) {
            this.id = id;
            this.position = position;
        }


        public void updateDownloaded() {
            taskPb.setMax(1);
            taskPb.setProgress(1);

            taskStatusTv.setText(R.string.tasks_manager_demo_status_completed);
            taskActionBtn.setText(R.string.delete);
            Drawable icon=ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_delete_black_24dp);
               taskActionBtn.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                taskActionBtn.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_action_delete));
//            }else {
//                taskActionBtn.setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_action_delete));
//
//            }
        }

        public void updateNotDownloaded(final int status, final long sofar, final long total) {
            if (sofar > 0 && total > 0) {
                final float percent = sofar
                        / (float) total;
                taskPb.setMax(100);
                taskPb.setProgress((int) (percent * 100));
            } else {
                taskPb.setMax(1);
                taskPb.setProgress(0);
            }

            switch (status) {
                case FileDownloadStatus.error:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_error);
                    break;
                case FileDownloadStatus.paused:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_paused);
                    break;
                default:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_not_downloaded);
                    break;
            }
            taskActionBtn.setText(R.string.start);
            Drawable icon=ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_cloud_download);
            taskActionBtn.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                taskActionBtn.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_action_download));
//            }else {
//                taskActionBtn.setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_action_download));
//
//            }
        }

        public void updateDownloading(final int status, final long sofar, final long total) {
            final float percent = sofar
                    / (float) total;
            taskPb.setMax(100);
            taskPb.setProgress((int) (percent * 100));

            switch (status) {
                case FileDownloadStatus.pending:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_pending);
                    break;
                case FileDownloadStatus.started:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_started);
                    break;
                case FileDownloadStatus.connected:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_connected);
                    break;
                case FileDownloadStatus.progress:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_progress);
                    break;
                default:
                    taskStatusTv.setText(MyApplication.CONTEXT.getString(
                            R.string.tasks_manager_demo_status_downloading, status));
                    break;
            }

            taskActionBtn.setText(R.string.pause);
            Drawable icon=ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_pause_black_24dp);
            taskActionBtn.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                taskActionBtn.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_action_pause));
//            }else {
//                taskActionBtn.setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_action_pause));
//
//            }
        }
        //=================================================================
        private TextView taskNameTv;
        private TextView taskStatusTv;
        private ProgressBar taskPb;
        private Button taskActionBtn;
        private Button closeButton;
        private void assignViews() {
            taskNameTv = (TextView) findViewById(R.id.task_name_tv);
            taskStatusTv = (TextView) findViewById(R.id.task_status_tv);
            taskPb = (ProgressBar) findViewById(R.id.task_pb);
            taskActionBtn = (Button) findViewById(R.id.task_action_btn);
            closeButton = (Button) findViewById(R.id.closButton);
        }

    }
    //=================================================================================================
    public static class TaskItemAdapter extends RecyclerView.Adapter<TaskItemViewHolder> implements ItemTouchHelperAdapter {
        TaskItemViewHolder holder;

        private FileDownloadListener taskDownloadListener = new FileDownloadSampleListener() {

            private TaskItemViewHolder checkCurrentHolder(final BaseDownloadTask task) {
                final TaskItemViewHolder tag = (TaskItemViewHolder) task.getTag();
                if (tag.id != task.getId()) {
                    return null;
                }

                return tag;
            }

            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.pending(task, soFarBytes, totalBytes);
                final TaskItemViewHolder tag = checkCurrentHolder(task);
                if (tag == null) {
                    return;
                }

                tag.updateDownloading(FileDownloadStatus.pending, soFarBytes
                        , totalBytes);
                tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_pending);
            }

            @Override
            protected void started(BaseDownloadTask task) {
                super.started(task);
                final TaskItemViewHolder tag = checkCurrentHolder(task);
                if (tag == null) {
                    return;
                }

                tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_started);
            }

            @Override
            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                final TaskItemViewHolder tag = checkCurrentHolder(task);
                if (tag == null) {
                    return;
                }

                tag.updateDownloading(FileDownloadStatus.connected, soFarBytes
                        , totalBytes);
                tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_connected);
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.progress(task, soFarBytes, totalBytes);
                final TaskItemViewHolder tag = checkCurrentHolder(task);
                if (tag == null) {
                    return;
                }

                tag.updateDownloading(FileDownloadStatus.progress, soFarBytes
                        , totalBytes);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                super.error(task, e);
                final TaskItemViewHolder tag = checkCurrentHolder(task);
                if (tag == null) {
                    return;
                }

                tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
                        , task.getLargeFileTotalBytes());
                DownloadManager.getImpl().removeTaskForViewHolder(task.getId());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.paused(task, soFarBytes, totalBytes);
                final TaskItemViewHolder tag = checkCurrentHolder(task);
                if (tag == null) {
                    return;
                }

                tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
                tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_paused);
                DownloadManager.getImpl().removeTaskForViewHolder(task.getId());
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                super.completed(task);
                final TaskItemViewHolder tag = checkCurrentHolder(task);
                if (tag == null) {
                    return;
                }

                tag.updateDownloaded();

                DownloadManager.getImpl().removeTaskForViewHolder(task.getId());
            }
        };
        private View.OnClickListener taskActionOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() == null) {
                    return;
                }

                TaskItemViewHolder holder = (TaskItemViewHolder) v.getTag();


                CharSequence action = ((TextView) v).getText();
                if (action.equals(v.getResources().getString(R.string.pause))) {
                    // to pause
                    FileDownloader.getImpl().pause(holder.id);
                } else if (action.equals(v.getResources().getString(R.string.start))) {
                    // to start
                    // to start
                    final DlModel.TasksManagerModel model = DownloadManager.getImpl().get(holder.position);
                    final BaseDownloadTask task = FileDownloader.getImpl().create(model.getUrl())
                            .setPath(model.getPath())
                            .setCallbackProgressTimes(100)
                            .setListener(taskDownloadListener);

                    DownloadManager.getImpl()
                            .addTaskForViewHolder(task);

                    DownloadManager.getImpl()
                            .updateViewHolder(holder.id, holder);

                    task.start();
                } else if (action.equals(v.getResources().getString(R.string.delete))) {
                    // to delete

                    new File(DownloadManager.getImpl().get(holder.position).getPath()).delete();
                    holder.taskActionBtn.setEnabled(true);
                    holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
                }
            }
        };



        @Override
        public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            holder = new TaskItemViewHolder(
                    LayoutInflater.from(
                            parent.getContext())
                            .inflate(R.layout.item_tasks_manager, parent, false));

            holder.taskActionBtn.setOnClickListener(taskActionOnClickListener);

            holder.closeButton.setOnClickListener(taskActionOnClickListener);
            return holder;
        }

        @Override
        public void onBindViewHolder(final TaskItemViewHolder holder, final int position) {
            final DlModel.TasksManagerModel model = DownloadManager.getImpl().get(position);

            holder.update(model.getId(), position);
            holder.taskActionBtn.setTag(holder);

            String url = model.getUrl();
            String fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
            holder.taskNameTv.setText(fileName);
            holder.closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//
                       DownloadManager.getImpl().Dlupdate(position);
                    DownloadManager.getImpl().removeTask(holder.id);
//                    DownloadManager.getImpl().removeTaskForViewHolder(holder.id);
                    AdapterMovies adapterMovies = new AdapterMovies(v.getContext());

                    CardView cv = new CardView(v.getContext());
//                    adapterMovies.onBindViewHolder(cv.setCardBackgroundColor(v.getResources().getColor(R.color.fbutton_color_carrot)));
//                    cv.setBackgroundColor(v.getResources().getColor(R.color.fbutton_color_carrot));

                    notifyItemRemoved(position);
                }
            });
            DownloadManager.getImpl()
                    .updateViewHolder(holder.id, holder);



            holder.taskActionBtn.setEnabled(true);


            if (DownloadManager.getImpl().isReady()) {
                final int status = DownloadManager.getImpl().getStatus(model.getId(), model.getPath());
                if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                        status == FileDownloadStatus.connected) {
                    // start task, but file not created yet
                    holder.updateDownloading(status, DownloadManager.getImpl().getSoFar(model.getId())
                            , DownloadManager.getImpl().getTotal(model.getId()));
                } else if (!new File(model.getPath()).exists() &&
                        !new File(FileDownloadUtils.getTempPath(model.getPath())).exists()) {
                    // not exist file
                    holder.updateNotDownloaded(status, 0, 0);
                } else if (DownloadManager.getImpl().isDownloaded(status)) {
                    // already downloaded and exist
                    holder.updateDownloaded();
                } else if (status == FileDownloadStatus.progress) {
                    // downloading
                    holder.updateDownloading(status, DownloadManager.getImpl().getSoFar(model.getId())
                            , DownloadManager.getImpl().getTotal(model.getId()));
                } else {
                    // not start
                    holder.updateNotDownloaded(status, DownloadManager.getImpl().getSoFar(model.getId())
                            , DownloadManager.getImpl().getTotal(model.getId()));
                }
            } else {
                holder.taskStatusTv.setText(R.string.tasks_manager_demo_status_loading);
                holder.taskActionBtn.setEnabled(false);
            }
        }

        @Override
        public int getItemCount() {
            return DownloadManager.getImpl().getTaskCounts();
        }

        @Override
        public void swipeToDelete(int position) {
            DownloadManager.getImpl().Dlupdate(position);
            DownloadManager.getImpl().removeTask(holder.id);
//                    DownloadManager.getImpl().removeTaskForViewHolder(holder.id);
            notifyItemRemoved(position);
//            notifyAll();
//            notifyDataSetChanged();

        }
    }


}

