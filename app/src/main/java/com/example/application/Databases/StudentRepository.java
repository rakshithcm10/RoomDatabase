package com.example.application.Databases;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentRepository {

    private StudentsDao studentsDao;

     private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application) {

        AppDatabase database = AppDatabase.getInstance(application);
        studentsDao = database.studentsDao();
        allStudents = studentsDao.getAllStudents();

    }

    public void insertRecord(Student student){

        new InsertStudentAsyncTask(studentsDao).execute(student);

    }

    public Student getStudentById(int id) {
        return studentsDao.getStudentById(id);
    }



    public void deleteRecord(Student student){
        new deleteStudentAsyncTask(studentsDao).execute(student);
    }

    public void updateRecord(Student student){
        new UpdateStudentAsyncTask(studentsDao).execute(student);
    }

    public LiveData<List<Student>> getAllStudents(){
        return allStudents;
    }






    private static class InsertStudentAsyncTask extends AsyncTask<Student,Void,Void>{
        private StudentsDao studentsDao;

        public InsertStudentAsyncTask(StudentsDao studentsDao) {
            this.studentsDao = studentsDao;
        }

        @Override
        protected Void doInBackground(Student... students) {

            studentsDao.insertRecord(students[0]);

            return null;
        }
    }



    private static class deleteStudentAsyncTask extends AsyncTask<Student,Void,Void>{
        private StudentsDao studentsDao;

        public deleteStudentAsyncTask(StudentsDao studentsDao) {
            this.studentsDao = studentsDao;
        }

        @Override
        protected Void doInBackground(Student... students) {

            studentsDao.deleteRecord(students[0]);

            return null;
        }
    }


    private static class UpdateStudentAsyncTask extends AsyncTask<Student,Void,Void>{
        private StudentsDao studentsDao;

        public UpdateStudentAsyncTask(StudentsDao studentsDao) {
            this.studentsDao = studentsDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentsDao.UpdateRecord(students[0]);
            return null;
        }
    }



}
