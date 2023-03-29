package com.example.application.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.application.Databases.AppDatabase;
import com.example.application.Databases.Student;
import com.example.application.Databases.StudentsDao;
import com.example.application.R;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder>{

    private List<Student> students = new ArrayList<>();
    AppDatabase db;
    StudentsDao studentsDao;
    private final Context context;
    private static final int EDIT_STUDENT_REQUEST_CODE = 1;
    public StudentAdapter(Context context) {
        this.context = context;
        studentsDao = AppDatabase.getInstance(context).studentsDao();
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);

        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {

        Student currentStudent = students.get(position);

        holder.textName1.setText(String.valueOf(currentStudent.getID()));
        holder.textName2.setText(currentStudent.getFirstName());
        holder.textName3.setText(currentStudent.getPhoneNumber());
        holder.textName4.setText(currentStudent.getEmail());

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setStudents(List<Student> student){
        this.students = student;
        notifyDataSetChanged();
    }



    class StudentHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        TextView textName1,textName2,textName3,textName4;
        ImageButton btn_more ;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);

            this.textName1 = itemView.findViewById(R.id.text1);
            this.textName2 = itemView.findViewById(R.id.text2);
            this.textName3 = itemView.findViewById(R.id.text3);
            this.textName4 = itemView.findViewById(R.id.text4);
            this.btn_more = itemView.findViewById(R.id.btn_more);
            btn_more.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            showPopupMenu(view);

        }

        public void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            db = Room.databaseBuilder(view.getContext(), AppDatabase.class, "Students-info").allowMainThreadQueries().build();
            studentsDao = db.studentsDao();
            popupMenu.show();
        }

        @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int getAdapterposition = getLayoutPosition();
            Student student = students.get(getAdapterposition);


            switch (menuItem.getItemId()){

                case R.id.btn_popup_edit:
                    Intent intent = new Intent(context, EditStudentActivity.class);
                    intent.putExtra("student", student.getID());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);



                    return true;

                case R.id.btn_popup_delete:

                    students.remove(getAdapterPosition());
                    studentsDao.deleteRecord(student);

                    notifyDataSetChanged();
                    return true;

                default:
                    return false;
            }

        }
    }
}
