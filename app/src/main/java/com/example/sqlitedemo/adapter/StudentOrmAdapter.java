package com.example.sqlitedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.entity.StudentOrm;

import java.util.List;

public class StudentOrmAdapter extends BaseAdapter {

    private  int selectItem=-1;
    private List<StudentOrm> students;
    private Context context;


    public void setSelectItem(int selectItem){
        this.selectItem = selectItem;

    }

    public StudentOrmAdapter(List <StudentOrm> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;


        if (convertView==null){

            //1、加载item_student.xml
            convertView = (View) LayoutInflater.from( context ).inflate( R.layout.item_student ,null);

            //2、获取每个控件
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById( R.id.tv_name );
            holder.tvGrade = convertView.findViewById( R.id.tv_grade );
            holder.tvAge = convertView.findViewById( R.id.tv_age );

            //3、将holder对象存储到view中
            convertView.setTag( holder );


        }else {

            //恢复
            holder= (ViewHolder) convertView.getTag();

        }

        //4、加载数据
        StudentOrm student = students.get(position);
        holder.tvName.setText( student.getName());
        holder.tvGrade.setText( student.getGrade() );
        holder.tvAge.setText(String.valueOf( student.getAge() ));
//        holder.tvCost.setText( String.valueOf( room.getCost() ) );


        return convertView;

    }

    static class ViewHolder {

        TextView tvName;
        TextView tvGrade;
        TextView tvAge;

    }
}
